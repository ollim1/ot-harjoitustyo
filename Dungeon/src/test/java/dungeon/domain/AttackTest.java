/*
 * @author olli m
 */
package dungeon.domain;

import dungeon.backend.Game;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author londes
 */
public class AttackTest {

    Actor source;
    Actor target;

    public AttackTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        source = new Monster(0, 0);
        target = new Monster(0, 0);
    }

    @Test
    public void biteWorks() {
        Attack attack = new Bite();
        double originalHealth = source.getHealth();
        attack.apply(null, source, target);
        assertTrue(target.getHealth() < originalHealth);
    }

    @Test
    public void punchWorks() {
        Attack attack = new Punch();
        double originalHealth = source.getHealth();
        attack.apply(null, source, target);
        assertTrue(target.getHealth() < originalHealth);
    }
}
