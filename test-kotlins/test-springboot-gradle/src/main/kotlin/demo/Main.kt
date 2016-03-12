package demo

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@EnableAutoConfiguration
class SampleController {
    @RequestMapping("/")
    @ResponseBody
    fun home(): String {
        return "Hello World!"
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(SampleController::class.java, *args)
}
