package demo.factories;

public class YourV8Engine implements Engineable {
    private final CrankShaft crankShaft;

    public YourV8Engine(CrankShaft crankShaft) {
        this.crankShaft = crankShaft;
    }

    @Override
    public String start() {
        return "Your V8 Engine start...";
    }
}
