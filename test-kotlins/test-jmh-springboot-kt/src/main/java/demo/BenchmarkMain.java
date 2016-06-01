package demo;


import demo.service.User;
import demo.service.UserService;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.TimeUnit;

public class BenchmarkMain {

    public static class JMHSample_02_BenchmarkModes {

        @Benchmark
        @BenchmarkMode(Mode.All)
        @OutputTimeUnit(TimeUnit.SECONDS)
        public void measureAll(UserServiceHolder userServiceHolder) throws InterruptedException {
            User it = userServiceHolder.userService.findById(1);
        }

        @State(Scope.Thread)
        public static class UserServiceHolder {
            ApplicationContext ctx = SpringApplication.run(BootApplication.class, new String[]{});
            UserService userService = ctx.getBean(UserService.class);
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JMHSample_02_BenchmarkModes.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
