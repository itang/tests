package demo;

import demo.util.IStringOps;

import java.io.PrintStream;

public class Client {
    private final IStringOps stringOps;
    private final PrintStream out;

    public Client(PrintStream out, IStringOps stringOps) {
        this.out = out;
        this.stringOps = stringOps;
    }

    public void sayHello(String name) {
        out.println("Hi " + name);
        if (stringOps.isStartsAndEndsWith(name, "w")) {
            out.println("BTW, I like your name");
        }
    }
}
