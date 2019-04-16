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
    public void setUp() throws IllegalArgumentException {
        game = new Game();
        game.initializeMapObjects(40, 40);
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

    @Test
    public void gameDrawsPlayer() {
        game.createPlayer();
        char[][] map = game.getPlotter().populateMap(game.getPlayer());
        boolean playerIsVisible = false;
        outer:
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x] == '@') {
                    playerIsVisible = true;
                    break outer;
                }
            }
        }
        assertTrue(playerIsVisible);
    }
}
