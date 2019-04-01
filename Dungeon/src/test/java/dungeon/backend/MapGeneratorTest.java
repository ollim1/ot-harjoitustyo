/*
 * @author olli m
 */
package dungeon.backend;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MapGeneratorTest {
    
    public MapGeneratorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of generateMap method, of class MapGenerator.
     */
    @Test
    public void testGenerateMap() {
        System.out.println("generateMap");
        MapGenerator instance = null;
        instance.generateMap();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMap method, of class MapGenerator.
     */
    @Test
    public void testSetMap() {
        System.out.println("setMap");
        char[][] map = null;
        MapGenerator instance = null;
        instance.setMap(map);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMap method, of class MapGenerator.
     */
    @Test
    public void testGetMap() {
        System.out.println("getMap");
        MapGenerator instance = null;
        char[][] expResult = null;
        char[][] result = instance.getMap();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
