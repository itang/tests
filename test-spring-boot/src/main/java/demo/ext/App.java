package demo.ext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class App {
    enum RunMode {
        Dev, Prod, Test
    }

    private static Logger LOG = LoggerFactory.getLogger(App.class);

    private static RunMode runMode = null;

    public static final String RUN_MODE_NAME = "run.mode";
    public static final String RUN_MODE_DEV = "dev";
    public static final String RUN_MODE_PROD = "prod";
    public static final String RUN_MODE_TEST = "test";

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
