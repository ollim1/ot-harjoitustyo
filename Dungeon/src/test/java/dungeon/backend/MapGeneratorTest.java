/*
 * @author olli m
 */
package dungeon.backend;

import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MapGeneratorTest {

    private MapGenerator mapGenerator;

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
        mapGenerator = new MapGenerator(new Random(108), 50, 50);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void generateAndReturnMap() {
        mapGenerator.generateMap();
        assertNotNull(mapGenerator.getMap());
    }

    @Test
    public void mapIsFormatted() {
        mapGenerator.generateMap();
        char[][] map = mapGenerator.getMap();
        boolean invalidCharacter = false;
        char offender = '#';
        int posX = -1;
        int posY = -1;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x] != '#'
                        && map[y][x] != ' '
                        && map[y][x] != '.'
                        && map[y][x] != 'D'
                        && map[y][x] != '@') {
                    invalidCharacter = true;
                    offender = map[y][x];
                    posX = x;
                    posY = y;
                    break;
                }
            }
        }
        if (invalidCharacter) {
            fail("fail: character " + offender + " at (" + posX + ", " + posY + ")");
        }
    }

    @Test
    public void setMapSetsMap() {
        char[][] map = new char[10][10];
        mapGenerator.setMap(map);
        assertSame(map, mapGenerator.getMap());
    }
}
