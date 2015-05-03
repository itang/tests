package demo.controllers;

import java.util.stream.IntStream;

import javaslang.Function1;
import javaslang.Function2;
import javaslang.collection.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.ext.BaseController;

@Controller
@RequestMapping("/javaslang")
public class JavaslangTestController extends BaseController {

    @RequestMapping("")
    public String index(ModelMap model) {
        final Function2<Integer, Integer, Integer> sum = (a, b) -> a + b;
        // add2.apply(1) = 3
        final Function1<Integer, Integer> add2 = sum.curried().apply(2);

        model.put("sum", add2.apply(100));
        // / errors with springloaded
        model.put("sum2", List.of(1, 2, 3, 4).sum());
        model.put("sum3", IntStream.of(1, 2, 3).sum());

        return "/javaslang/index";
    }
}
