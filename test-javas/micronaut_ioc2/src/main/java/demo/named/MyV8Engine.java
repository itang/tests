package demo.named;


import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Named("v8")
public class MyV8Engine implements IEngine {
    @Override
    public String start() {
        return "My V8 Engine start...";
    }
}
