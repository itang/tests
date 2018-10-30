package demo.named;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class MyVehicle {
    private final IEngine engine;
    private IEngine otherEngine;

    public MyVehicle(@Named("v6") IEngine engine) {
        this.engine = engine;
    }

    @Inject
    public void setEngine(@Named("v8") IEngine engine) {
        this.otherEngine = engine;
    }

    @Inject
    @V10
    private IEngine thirdEngine;

    public void start() {
        String ret = engine.start();
        System.out.println(ret);

        String ret2 = otherEngine.start();
        System.out.println(ret2);

        String ret3 = thirdEngine.start();
        System.out.println("third:" + ret3);
    }
}
