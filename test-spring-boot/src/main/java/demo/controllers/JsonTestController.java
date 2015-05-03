package demo.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

import demo.ext.BaseController;

@Controller
@RequestMapping("/test/json")
public class JsonTestController extends BaseController {

    @RequestMapping("")
    public String index(ModelMap model) {
        User user = new User("itang");
        model.put("user", user);

        return "/test/json/index";
    }

    @RequestMapping("/data")
    @ResponseBody
    public List<User> data() {
        return Lists.newArrayList(new User("Itang"), new User("Tqibm"));
    }

    public static class User {
        private final String name;

        public User(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
