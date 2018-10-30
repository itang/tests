package demo.primary;

import io.micronaut.context.annotation.Primary;

import javax.inject.Singleton;

@Singleton
@Primary
public class Blue implements ColorPicker {
    @Override
    public String color() {
        return "blue";
    }

    @Override
    public String toString() {
        return "Blue{}";
    }
}
