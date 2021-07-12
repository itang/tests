package hello;

import hello.internal.GreeterImpl;
import org.joda.time.LocalTime;

public class HelloWorld {
    public static void main(String[] args) {
        LocalTime currentTime = new LocalTime();
        System.out.println("The current local time is: " + currentTime);
        Greeter greeter = new GreeterImpl();
        System.out.println(greeter.sayHello());
    }
}
