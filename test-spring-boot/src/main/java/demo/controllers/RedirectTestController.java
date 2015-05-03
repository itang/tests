package demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.ext.BaseController;

@Controller
@RequestMapping("/test/redirect")
public class RedirectTestController extends BaseController {

    @RequestMapping("")
    public String index() {
        return "/test/redirect/index";
    }

    @RequestMapping("/to_inner")
    public String redirectToInner() {
        return redirect("/test/redirect/inner");
    }

    @RequestMapping("/to_outer")
    public String redirectToOuter() {
        return redirect("http://www.baidu.com");
    }

    @RequestMapping("/inner")
    @ResponseBody
    public String inner() {
        return "Inner";
    }
}
