package demo.controllers;

import java.util.Date;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloController extends BaseController {

    @RequestMapping(value = "/",produces = "text/html")
    public String index(HttpServletRequest request) {
        request.getSession().setAttribute("now", new Date().toString());
        return "version-1202: hello, " + new Date() + "<hr/>Last accessedtime:" + request.getSession().getLastAccessedTime();
    }
}
