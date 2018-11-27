package testjpa.web;


import com.linecorp.armeria.common.HttpHeaders;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.annotation.Get;
import io.netty.util.AsciiString;
import org.springframework.stereotype.Controller;

@Controller  //spring controller, mixed
public class IndexController {
    @Get("/")
    public HttpResponse index() {
        return HttpResponse.of(
                HttpHeaders.of(301).setAllIfAbsent(HttpHeaders.of(AsciiString.of("Location"), "http://localhost:8080/docs"))
        );
    }
}
