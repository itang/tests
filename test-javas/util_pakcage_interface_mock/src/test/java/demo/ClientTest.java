package demo;

import demo.util.IStringOps;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientTest {

    @Test
    public void test() throws UnsupportedEncodingException {
        IStringOps stringOps = mock(IStringOps.class);
        when(stringOps.isStartsAndEndsWith("wow", "w")).thenReturn(true);
        when(stringOps.isStartsAndEndsWith("wo", "w")).thenReturn(false);

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos, true, "UTF-8");


        Client client = new Client(ps, stringOps);
        client.sayHello("wow");

        String data = new String(baos.toByteArray(), StandardCharsets.UTF_8);

        assertEquals("Hi wow\nBTW, I like your name\n", data);
    }
}
