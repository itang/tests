package demo.primary;

import javax.inject.Singleton;

@Singleton
public class Green implements ColorPicker {
    @Override
    public String color() {
        return "green";
    }

    @Override
    public String toString() {
        return "Green{}";
    }
}
