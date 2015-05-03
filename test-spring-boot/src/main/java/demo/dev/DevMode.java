package demo.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationListener;

public abstract class DevMode {

    public static void bootstrap(final SpringApplication application) {
        try {
            application.addListeners((ApplicationListener<?>) Class.forName(
                    "demo.dev.RedisBootstrapListener").newInstance());
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
