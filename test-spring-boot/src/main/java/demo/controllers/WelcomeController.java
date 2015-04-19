package demo.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController extends BaseController {

    @RequestMapping(value = "/")
    public String index(HttpServletRequest request, ModelMap model) {
        request.getSession().setAttribute("now", new Date().toString());

        model.put("now", new Date());
        model.put("lastAccessedTime", request.getSession().getLastAccessedTime());

        return "welcome";
    }
}
