package demo.ext;

import java.io.IOException;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import redis.embedded.RedisServer;

import demo.utils.Nets;

public class RedisBootstrapListener implements ApplicationListener<ApplicationStartedEvent> {

    private static final int DEFAULT_REDIS_PORT = 6379;
    private RedisServer redisServer = null;

    public RedisBootstrapListener() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            destroy();
        }));
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        System.out.println(event);
        if (!Nets.available(DEFAULT_REDIS_PORT)) {
            System.err.println("redis port unavailabled, check Redis service have started...");
            return;
        }

        System.out.println("start redis server...");
        try {
            redisServer = new RedisServer(DEFAULT_REDIS_PORT);
            if (!redisServer.isActive()) {
                redisServer.start();
                System.out.println("INFO: redis start");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        if (redisServer != null) {
            System.out.println("INFO: redis stop");
            redisServer.stop();
            redisServer = null;
        }
    }
}
