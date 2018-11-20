package demo;


import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.AbstractHttpService;
import com.linecorp.armeria.server.ServerBuilder;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.docs.DocService;

public class Main {

    public static void main(String[] args) {
        ServerBuilder sb = new ServerBuilder();

        sb.http(3000);

        sb.service("/test1", (req, resp) -> HttpResponse.of("Hello, World"));
        sb.service("/test2", new AbstractHttpService() {
            @Override
            public HttpResponse doGet(ServiceRequestContext ctx, HttpRequest req) throws Exception {
                return HttpResponse.of("Hello, World");
            }
        });
        sb.annotatedService(new Object() {
            @Get("/test3")
            public HttpResponse test() {
                return HttpResponse.of("Hello, World");
            }
        });

        sb.annotatedService(new Object() {
            @Get("/test4")
            public String test() {
                return "Hello, World";
            }
        });

        sb.serviceUnder("/docs", new DocService());

        sb.build().start().join();
    }
}

