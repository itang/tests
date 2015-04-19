package demo.ext;

import java.io.IOException;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import redis.embedded.RedisServer;

public class RedisBootstrapListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        System.out.println(event);
        System.out.println("start redis server...");
        RedisServer redisServer = null;
        try {
            redisServer = new RedisServer(6379);
            if (!redisServer.isActive()) {
                redisServer.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
