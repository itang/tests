package demo.anno;

import io.micronaut.context.annotation.DefaultScope;
import io.micronaut.context.annotation.Requires;

import javax.inject.Singleton;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Requires(classes = Car.class)
@DefaultScope(Singleton.class)
@Documented
@Retention(RUNTIME)
public @interface Driver2 {
}
