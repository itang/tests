package demo

import demo.service.UserService;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.options.OptionsBuilder
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.TimeUnit;

class BenchmarkMainKt {

    class JMHSample_02_BenchmarkModes {

        @Benchmark
        @BenchmarkMode(Mode.All)
        @OutputTimeUnit(TimeUnit.SECONDS)
        @Throws(InterruptedException::class)
        fun measureAll(userServiceHolder: UserServiceHolder) {
            val it = userServiceHolder.userService.findById(1)
        }

        @State(Scope.Thread)
        class UserServiceHolder {
            val ctx: ApplicationContext = SpringApplication.run(BootApplication::class.java, *emptyArray<String>())
            val userService: UserService = ctx.getBean(UserService::class.java)
        }
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val opt = OptionsBuilder()
                    .include(JMHSample_02_BenchmarkModes::class.java.getSimpleName())
                    .warmupIterations(5)
                    .measurementIterations(5)
                    .forks(1)
                    .build()
            Runner(opt).run()
        }
    }
}
