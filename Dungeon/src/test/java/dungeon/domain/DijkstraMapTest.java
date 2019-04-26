/*
 * @author londes
 */
package dungeon.domain;

import java.util.Random;
import javafx.scene.paint.Color;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author londes
 */
public class DijkstraMapTest {

    private DijkstraMap dijkstraMap;
    private int max;
    private int[][] values;
    int centerX;
    int centerY;

    public DijkstraMapTest() {
    }

    @Before
    public void setUp() {
        int inf = Integer.MAX_VALUE;
        Random random = new Random(1337);
        values = new int[][]{
            {inf, inf, inf, inf, inf, inf, inf},
            {inf, 0, 0, 0, 0, 0, inf},
            {inf, 0, 0, 0, 0, 0, inf},
            {inf, 0, 0, 0, 0, 0, inf},
            {inf, 0, 0, 0, 0, 0, inf},
            {inf, 0, 0, 0, 0, 0, inf},
            {inf, inf, inf, inf, inf, inf, inf}};
        max = 0;
        for (int y = 1; y < values.length - 1; y++) {
            for (int x = 1; x < values[0].length - 1; x++) {
                if (values[y][x] != inf) {
                    int dx = x - centerX;
                    int dy = y - centerY;
                    values[y][x] = random.nextInt(1000) + 1;
                    if (values[y][x] > max) {
                        max = values[y][x];
                        centerX = x;
                        centerY = y;
                    }
                }
            }
        }
        values[4][2] = 0;
        dijkstraMap = new DijkstraMap(values);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void invertInverts() {
        dijkstraMap.invert();
        if (values[centerY][centerX] != 0) {
            fail("The largest value hasn't been inverted to zero.");
        }
    }

    @Test
    public void toStringWorks() {
        DijkstraMap emptyMap = new DijkstraMap(10, 10);
        if (!emptyMap.toString().equals("unformatted array")) {
            fail("toString is not checking for unformatted arrays");
        }
        String mapString = dijkstraMap.toString();
        if (mapString.charAt(0) != '#') {
            fail("toString does not print wall tiles (value Integer.MAX_VALUE) as '#'");
        }
        if (!mapString.contains(".")) {
            fail("toString does not mark 0 as '.'");
        }
        boolean numberFound = false;
        for (int i = 0; i < mapString.length(); i++) {
            char c = mapString.charAt(i);
            if (c >= '0' && c <= '9') {
                numberFound = true;
                break;
            }
        }
        if (!numberFound) {
            fail("toString does not print numbers");
        }
    }

    @Test
    public void getColorMapReturnsColorMap() {
        Color[][] colorMap = dijkstraMap.getColorMap(360, 1.0);
        for (int y = 0; y < colorMap.length; y++) {
            for (int x = 0; x < colorMap.length; x++) {
                if (colorMap[y][x] == null) {
                    fail("getColorMap() sets cells as null");
                }
            }
        }
    }
}
