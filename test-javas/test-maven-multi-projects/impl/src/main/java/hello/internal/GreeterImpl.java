package hello.internal;

import hello.Greeter;

public class GreeterImpl implements Greeter {
    @Override
    public String sayHello() {
        return "Hello world java!";
    }
}
