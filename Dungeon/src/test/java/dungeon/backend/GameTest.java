/*
 * @author olli m
 */
package dungeon.backend;

import dungeon.domain.Node;
import java.util.Arrays;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    private Game game;
    private static final char[][] testMap = new char[][]{
        "#######".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#     #".toCharArray(),
        "#######".toCharArray()
    };

    public GameTest() {
    }

    @Before
    public void setUp() throws IllegalArgumentException {
        game = new Game();
        game.initializeMapObjects(testMap);
    }

    @Test
    public void gameCreatesRandomMap() {
        game = new Game();
        game.initializeMapObjects(40, 40);
        if (game.getMap() == null) {
            fail("The game did not create a map");
        }
        for (int y = 0; y < testMap.length; y++) {
            if (testMap[y][0] != '#' || testMap[y][testMap[0].length - 1] != '#') {
                fail("The map has no vertical edges");
            }
        }
        for (int x = 0; x < testMap[0].length; x++) {
            if (testMap[x][0] != '#' || testMap[testMap.length - 1][x] != '#') {
                fail("The map has no horizontal edges");
            }
        }
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

    @Test
    public void gameCreatesMonster() {
        game.createPlayer();
        game.createMonster();
        if (game.getActors().isEmpty()) {
            fail("The game did not create a monster");
        }
        Node p = game.getActors().get(0).getPosition();
        if (p.getX() < 1 || p.getY() < 1 || p.getX() >= testMap[0].length - 1 || p.getY() >= testMap.length - 1) {
            fail("The monster was created in an invalid location: (" + p.getX() + ", " + p.getY() + ")");
        }
    }

    @Test
    public void gameDrawsMonster() {
        game.createPlayer();
        game.createMonster();

        char[][] map = game.getPlotter().populateMap(null);
        boolean monsterIsVisible = false;
        outer:
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x] != '#' && map[y][x] != ' ') {
                    monsterIsVisible = true;
                    break outer;
                }
            }
        }
        assertTrue(monsterIsVisible);
    }
}
