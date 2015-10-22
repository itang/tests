package hello;

import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

interface Loggable {
    default public Logger logger() {
        return LoggerFactory.getLogger(this.getClass());
    }

    // @see
    // https://javax0.wordpress.com/2014/04/02/how-not-to-use-java-8-default-methods/
    default public Logger logger_v2() {
        Logger logger = Extensions.map.get(this);
        if (logger == null) {
            logger = LoggerFactory.getLogger(this.getClass());
            Extensions.map.put(this, logger);
        }
        return logger;
    }

    class Extensions {
        private static final WeakHashMap<Loggable, Logger> map = new WeakHashMap<>();
    }
}

public class Hello implements Loggable {
    public static final Logger _logger = LoggerFactory.getLogger(Hello.class);
    public static final Hello INSTANCE = new Hello();
}
