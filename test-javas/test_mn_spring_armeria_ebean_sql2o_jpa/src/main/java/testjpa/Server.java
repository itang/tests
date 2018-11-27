package testjpa;


import com.deftype.collector.LinkOuterClass;
import com.deftype.collector.LinkServiceGrpc;
import com.linecorp.armeria.common.grpc.GrpcSerializationFormats;
import com.linecorp.armeria.server.ServerBuilder;
import com.linecorp.armeria.server.docs.DocServiceBuilder;
import com.linecorp.armeria.server.grpc.GrpcServiceBuilder;
import io.grpc.BindableService;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import testjpa.web.IndexController;

import javax.inject.Singleton;
import java.util.List;

@Factory
public class Server {

    private final List<BindableService> services;
    private final IndexController indexController;

    public Server(List<BindableService> services, IndexController indexController) {
        this.services = services;
        this.indexController = indexController;
    }

    @Bean
    @Singleton
    public com.linecorp.armeria.server.Server createServer(@Value("${server.port}") String port) {
        ServerBuilder sb = new ServerBuilder();
        sb.http(Integer.valueOf(port));

        // Add a gRPC service which implements 'GrpcHelloService'.
        // Unlike Thrift, you must enable gRPC-Web and unframed requests explicitly.
        GrpcServiceBuilder grpcBuilder = new GrpcServiceBuilder();

        for (BindableService s : services) {
            grpcBuilder.addService(s);
        }
        grpcBuilder
                .supportedSerializationFormats(GrpcSerializationFormats.values())
                .enableUnframedRequests(true);

        sb.service(
                grpcBuilder.build()
        );

        // Add a DocService which scans all Thrift and gRPC services added to the server.
        sb.serviceUnder("/docs", new DocServiceBuilder()
                .exampleRequestForMethod(LinkServiceGrpc.SERVICE_NAME,
                        "AddLink", // Method name
                        LinkOuterClass.AddLinkRequest.newBuilder().setLink("http://www.baid.com").build())
                .build());

        sb.annotatedService(indexController);

        return sb.build();
    }
}
