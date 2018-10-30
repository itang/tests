package demo.named;

import javax.inject.Singleton;

@Singleton
@V10
public class MyV10Engine implements IEngine {
    @Override
    public String start() {
        return "My V10 Engine start...";
    }
}
