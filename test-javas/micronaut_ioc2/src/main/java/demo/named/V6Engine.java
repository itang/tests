package demo.named;

import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Named("v6")
public class V6Engine implements IEngine {
    @Override
    public String start() {
        return "My V6 Engine start";
    }
}
