package demo.factories;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;

import javax.inject.Singleton;

@Factory
public class EngineFactory {

    @Bean
    @Singleton
    public Engineable v8Engine(CrankShaft crankShaft) {
        return new YourV8Engine(crankShaft);
    }
}
