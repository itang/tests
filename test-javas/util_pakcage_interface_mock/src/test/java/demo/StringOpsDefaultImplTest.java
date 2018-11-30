package demo;

import demo.util.IStringOps;
import demo.util.impl.StringOpsDefaultImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringOpsDefaultImplTest {

    private IStringOps stringOps = new StringOpsDefaultImpl();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testIsStartAndEndWith() {
        assertTrue(stringOps.isStartsAndEndsWith("dad", "d"));
        assertFalse(stringOps.isStartsAndEndsWith("dac", "d"));
    }

    @Test
    public void testIsStartAndEndWith2() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("str can't bu null");
        assertTrue(stringOps.isStartsAndEndsWith(null, "d"));
    }

    @Test
    public void testIsStartAndEndWith3() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("it can't bu null");
        assertTrue(stringOps.isStartsAndEndsWith("a", null));
    }
}
