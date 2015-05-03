package demo;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import demo.dev.DevMode;
import demo.ext.App;

@SpringBootApplication
// @Configurable
// @EnableAutoConfiguration
// @ComponentScan
public class DemoApplication {
    private static Logger LOG = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(final String[] args) {
        displayInfo(args);

        SpringApplication application = new SpringApplication(DemoApplication.class);
        if (App.isDevMode()) {
            DevMode.bootstrap(application);
        }

        application.run(args);
    }

    private static void displayInfo(String[] args) {
        LOG.info("#RunMode: {}", App.runMode());
        LOG.info("#Main args: {}", Arrays.toString(args));
        LOG.info("#spring.profiles.active: {}", System.getProperty("spring.profiles.active"));
    }
}
