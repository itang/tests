package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

interface Loggable {
    default public Logger logger() {
        return LoggerFactory.getLogger(Hello.class);
    }
}

public class Hello implements Loggable {
    public static final Logger _logger = LoggerFactory.getLogger(Hello.class);
    public static final Hello INSTANCE = new Hello();
}
