/*
 * @author olli m
 */
package dungeon.ui;

import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DungeonUiTest {

    public DungeonUiTest() {
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
     * Test of start method, of class DungeonUi.
     */
    @Test
    public void testStart() {
        System.out.println("start");
        Stage window = null;
        DungeonUi instance = new DungeonUi();
        instance.start(window);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class DungeonUi.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        DungeonUi.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
