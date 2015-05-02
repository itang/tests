package demo;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;

@SpringBootApplication
// @Configurable
// @EnableAutoConfiguration
// @ComponentScan
public class DemoApplication {
    enum RunMode {
        Dev, Prod, Test
    }

    private static RunMode runMode = null;

    public static final String RUN_MODE_NAME = "run.mode";
    public static final String RUN_MODE_DEV = "dev";
    public static final String RUN_MODE_PROD = "prod";
    public static final String RUN_MODE_TEST = "test";

    public static void main(String[] args) {
        System.out.println("#RunMode: " + runMode());
        System.out.println("#Main args: " + Arrays.toString(args));
        System.out.println("#spring.profiles.active: "
                + System.getProperty("spring.profiles.active"));

        SpringApplication application = new SpringApplication(DemoApplication.class);
        if (isDevMode()) {
            try {
                application.addListeners((ApplicationListener<?>) Class.forName(
                        "demo.ext.RedisBootstrapListener").newInstance());
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        application.run(args);
    }

    public static boolean isDevMode() {
        return runMode() == RunMode.Dev;
    }

    public static boolean isProdMode() {
        return runMode() == RunMode.Prod;
    }

    public static boolean isProdTest() {
        return runMode() == RunMode.Test;
    }

    public static RunMode runMode() {
        if (runMode == null) {
            final String v = System.getProperty(RUN_MODE_NAME);
            System.out.println("run.mode raw value:" + v);
            if (v == null) {
                runMode = RunMode.Dev;
            }

            switch (v.toLowerCase()) {
            case RUN_MODE_DEV:
                runMode = RunMode.Dev;
                break;
            case RUN_MODE_PROD:
                runMode = RunMode.Prod;
                break;
            case RUN_MODE_TEST:
                runMode = RunMode.Test;
                break;
            default:
                throw new RuntimeException("Unknown Run Mode:" + v);
            }
        }

        return runMode;
    }
}
