package demo;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger LOG = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        LOG.info("#RunMode: {}", runMode());
        LOG.info("#Main args: {}", Arrays.toString(args));
        LOG.info("#spring.profiles.active: {}", System.getProperty("spring.profiles.active"));

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
            LOG.debug("run.mode raw value: {}", v);

            if (v == null) {
                runMode = RunMode.Dev;
            } else {
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
        }

        return runMode;
    }
}
