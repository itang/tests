package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import demo.ext.RedisBootstrapListener;

@SpringBootApplication
// @Configurable
// @EnableAutoConfiguration
// @ComponentScan
public class DemoApplication {
    public static void main(String[] args) {
        System.out.println("spring.profiles.active: "
                + System.getProperty("spring.profiles.active"));

        SpringApplication application = new SpringApplication(DemoApplication.class);
        application.addListeners(new RedisBootstrapListener());

        application.run(args);
    }
}
