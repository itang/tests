package demo.ext;

import java.io.IOException;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import redis.embedded.RedisServer;

public class RedisBootstrapListener implements ApplicationListener<ApplicationStartedEvent> {

    private RedisServer redisServer = null;

    public RedisBootstrapListener() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            destroy();
        }));
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        System.out.println(event);
        System.out.println("start redis server...");
        try {
            redisServer = new RedisServer(6379);
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
