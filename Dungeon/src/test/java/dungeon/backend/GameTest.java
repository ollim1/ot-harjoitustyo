/*
 * @author olli m
 */
package dungeon.backend;

import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    private Game game;

    public GameTest() {
    }

    @Before
    public void setUp() {
        try {
            Game game = new Game(40, 40);
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void gameConstructorThrowsExceptionOnIllegalMapSize() {
        Game game1 = new Game(-1, -1);
    }
//
//    @Test
//    public void gameConstructorCorrectMapSize() {
//        Random rand = new Random();
//        int r1 = 100;
//        int r2 = 200;
//        Game testGame = new Game(r1, r2);
//        assertEquals(r2, testGame.drawMap().length);
//        assertEquals(r1, testGame.drawMap()[0].length);
//    }

    @Test
    public void outOfBoundsReturnsTrueIfOutOfBounds() {

    }
}
