package demo.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.ext.BaseController;

@Controller
public class WelcomeController extends BaseController {

    @RequestMapping(value = "/")
    public String index(HttpServletRequest request, ModelMap model) {
        logger.debug("WelcomeController");

        request.getSession().setAttribute("now", new Date().toString());

        model.put("now", new Date());
        model.put("lastAccessedTime", request.getSession().getLastAccessedTime());
        model.put("sessionId", request.getSession().getId());
        model.put("creationTime", request.getSession().getCreationTime());
        model.put("session", request.getSession());
        return "welcome";
    }
}
