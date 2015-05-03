package demo.dev;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import redis.embedded.RedisServer;
import demo.utils.Utils;

public class RedisBootstrapListener implements ApplicationListener<ApplicationStartedEvent> {

    private static Logger LOG = LoggerFactory.getLogger(RedisBootstrapListener.class);
    private static final int DEFAULT_REDIS_PORT = 6379;

    private RedisServer redisServer = null;

    public RedisBootstrapListener() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            destroy();
        }));
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        LOG.debug("ApplicationStartedEvent: {}", event);
        if (!Utils.availablePort(DEFAULT_REDIS_PORT)) {
            LOG.warn("Redis port unavailabled, check Redis service have started...");
            return;
        }

        LOG.info("start redis server...");
        try {
            redisServer = new RedisServer(DEFAULT_REDIS_PORT);
            if (!redisServer.isActive()) {
                redisServer.start();
                LOG.info("Redis start");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        if (redisServer != null) {
            LOG.info("Redis stop");
            redisServer.stop();
            redisServer = null;
        }
    }
}
