package hello;

import hello.internal.GreeterImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GreeterImplTest {
    @Test
    public void test() {
        Greeter greeter = new GreeterImpl();
        assertEquals("Hello world java!", greeter.sayHello());
    }
}
