/*
 * @author olli m
 */
package dungeon.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author londes
 */
public class NodeTest {

    private Node a;
    private Node b;

    public NodeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        a = new Node(0, 0);
        b = new Node(0, 0);
    }

    @Test
    public void comparatorWorks() {
        a.setDistance(141);
        b.setDistance(100);
        int result = a.compareTo(b);
        if (result > 0) {
            return;
        }
        fail("expected " + 41 + ", got " + a.compareTo(b));
    }

    @Test
    public void compareToReturnsMaxValueIfNumberIsNegative() {
        if (a.compareTo(b) != Integer.MAX_VALUE) {
            fail("test failed when both values were -1");
        }
        a.setDistance(100);
        if (a.compareTo(b) != Integer.MAX_VALUE) {
            fail("test failed when foo in foo.compareTo(bar) had a distance of -1");
        }
        a.setDistance(-1);
        b.setDistance(100);
        if (a.compareTo(b) != Integer.MAX_VALUE) {
            fail("test failed when bar in foo.compareTo(bar) had a distance of -1");
        }
    }
    
    @Test
    public void getDistanceWorks() {
        a.setDistance(100);
        assertEquals(100, a.getDistance());
    }

    @Test
    public void equalsWorks() {
        assertFalse(a.equals(null));
        assertTrue(a.equals(b));
    }
    
    @Test
    public void toStringWorks() {
        Node node = new Node(4, 3);
        assertTrue(node.toString().equals("(4, 3)"));
    }
}
