package demo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody


@SpringBootApplication
@Controller
open class Main {
    @GetMapping(value = "/")
    @ResponseBody
    fun welcome(): String {
        return "hello, world"
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(Main::class.java, *args)
}
