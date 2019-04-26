/*
 * @author londes
 */
package dungeon.domain;

import dungeon.backend.Game;
import java.util.Random;
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
public class ActorTest {

    private Game game;
    private char[][] testMap;

    public ActorTest() {
    }

    @Before
    public void setUp() {
        game = new Game();
        testMap = new char[][]{
            "######".toCharArray(),
            "#    #".toCharArray(),
            "#    #".toCharArray(),
            "#    #".toCharArray(),
            "#    #".toCharArray(),
            "######".toCharArray()
        };
    }

    @After
    public void tearDown() {
    }

    @Test
    public void distanceToWorks() {
        game.initializeMapObjects(testMap);
        Random r = new Random(1337);
        int x1 = r.nextInt(4) + 1;
        int x2 = r.nextInt(4) + 1;
        int y1 = r.nextInt(4) + 1;
        int y2 = r.nextInt(4) + 1;
        int dy = y2 - y1;
        int dx = x2 - x1;
        Player player = (Player) game.createPlayer(x1, y1);
        Monster monster = (Monster) game.createMonster(x2, y2, ActorType.ORC);
        double actual1 = monster.distanceTo(player);
        double expected = Math.sqrt(dx * dx + dy * dy);
        if (!doubleEquals(expected, actual1, 0.01)) {
            fail("distanceTo(Actor that): expected " + expected + ", but got " + actual1);
        }

        double actual2 = monster.distanceTo(x1, y1);
        if (!doubleEquals(expected, actual2, 0.01)) {
            fail("distanceTo(int x, int y): expected " + expected + ", but got " + actual1);
        }
    }

    private boolean doubleEquals(double a, double b, double epsilon) {
        double difference = a - b;
        if (difference < 0) {
            difference = -difference;
        }
        return difference < epsilon;
    }

    @Test
    public void minHealthIsZero() {
        game.initializeMapObjects(testMap);
        Monster monster = (Monster) game.createMonster(3, 3, ActorType.ORC);
        monster.damage(monster.getMaxHealth() + 1);
        if (monster.getHealth() < 0.0) {
            fail("health can reach negative values");
        }
    }

    @Test
    public void compareToWorks() {
        game.initializeMapObjects(testMap);
        Random r = new Random(1337);
        Actor actor1 = game.createMonster(3, 3, ActorType.ORC);
        Actor actor2 = game.createMonster(3, 2, ActorType.ORC);

        actor2.setNextTurn(r.nextInt(1000));
        if (actor2.compareTo(actor1) != actor2.getNextTurn() - actor1.getNextTurn()) {
            fail("compareTo doesn't work");
        }

    }
}
