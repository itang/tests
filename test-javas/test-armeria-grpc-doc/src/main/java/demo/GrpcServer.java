package demo;

import com.example.grpc.hello.Hello;
import com.example.grpc.hello.HelloServiceGrpc;
import com.linecorp.armeria.client.Endpoint;
import com.linecorp.armeria.client.HttpClient;
import com.linecorp.armeria.client.endpoint.EndpointGroup;
import com.linecorp.armeria.client.endpoint.EndpointGroupRegistry;
import com.linecorp.armeria.client.endpoint.EndpointSelectionStrategy;
import com.linecorp.armeria.client.endpoint.StaticEndpointGroup;
import com.linecorp.armeria.common.AggregatedHttpMessage;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.grpc.GrpcSerializationFormats;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;
import com.linecorp.armeria.server.docs.DocServiceBuilder;
import com.linecorp.armeria.server.grpc.GrpcServiceBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GrpcServer {
    public static void main(String[] args) {
        ServerBuilder sb = new ServerBuilder();
        sb.http(8080);

        // Add a gRPC service which implements 'GrpcHelloService'.
        // Unlike Thrift, you must enable gRPC-Web and unframed requests explicitly.
        sb.service(
                new GrpcServiceBuilder()
                        .addService(new MyHelloServiceGrpc())
                        .supportedSerializationFormats(GrpcSerializationFormats.values())
                        .enableUnframedRequests(true)
                        .build()
        );

        // Add a DocService which scans all Thrift and gRPC services added to the server.
        sb.serviceUnder("/docs", new DocServiceBuilder()
                .exampleRequestForMethod(HelloServiceGrpc.SERVICE_NAME,
                        "Hello", // Method name
                        Hello.HelloRequest.newBuilder().setName("Armeria").build())
                .build());

        Server server = sb.build();
        server.start().join();
    }

    private static void test() {
        // Create a group of well-known search engine endpoints.
        EndpointGroup searchEngineGroup = new StaticEndpointGroup(
                Endpoint.of("www.google.com", 443),
                Endpoint.of("www.bing.com", 443),
                Endpoint.of("duckduckgo.com", 443).withWeight(1500));

        List<Endpoint> endpoints = searchEngineGroup.endpoints();
        assert endpoints.contains(Endpoint.of("www.google.com", 443));
        assert endpoints.contains(Endpoint.of("www.bing.com", 443));
        assert endpoints.contains(Endpoint.of("duckduckgo.com", 443));

        EndpointGroupRegistry.register("search_engines", searchEngineGroup,
                EndpointSelectionStrategy.WEIGHTED_ROUND_ROBIN);

        assert EndpointGroupRegistry.get("search_engines") == searchEngineGroup;

        HttpClient client = HttpClient.of("http://group:search_engines/");
        List<CompletableFuture<?>> futures = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            final HttpResponse res = client.get("/");
            final CompletableFuture<AggregatedHttpMessage> f = res.aggregate();
            futures.add(f.thenRun(() -> {
                System.out.println(Thread.currentThread() + " " + f.getNow(null));
            }));
        }

        System.out.println("here1");
        try {
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("here2");
    }
}
