package demo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class BootApplication {

}

fun main(args: Array<String>) {
    SpringApplication.run(BootApplication::class.java, *args)
}

