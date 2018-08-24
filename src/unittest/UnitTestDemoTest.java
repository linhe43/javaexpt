package unittest;

import org.junit.Test;

import static org.junit.Assert.*;

public class UnitTestDemoTest {

    @Test
    public void func() {
        assertEquals(0, new UnitTestDemo().func(-1));
        assertEquals(1, new UnitTestDemo().func(1));
        assertEquals(2, new UnitTestDemo().func(2));
        assertEquals(5, new UnitTestDemo().func(4));
        assertEquals(8, new UnitTestDemo().func(5));
    }
}