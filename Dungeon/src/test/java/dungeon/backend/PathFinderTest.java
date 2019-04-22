/*
 * @author olli m
 */
package dungeon.backend;

import dungeon.domain.Direction;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author londes
 */
public class PathFinderTest {

    private char[][] map;
    private PathFinder pathFinder;

    public PathFinderTest() {
    }

    @Before
    public void setUp() {
        map = new char[][]{
            "############".toCharArray(),
            "#  #   # # #".toCharArray(),
            "## #     # #".toCharArray(),
            "#  #   # # #".toCharArray(),
            "# #### #   #".toCharArray(),
            "#      #####".toCharArray(),
            "############".toCharArray()
        };
        pathFinder = new PathFinder();
    }

    @Test
    public void constructorWorks() {
        PathFinder p = new PathFinder();
    }

    @Test
    public void pathFinderReturnsPathWhenOneExists() {
        pathFinder.computePaths(map, 1, 1);
        int[][] distances = pathFinder.getDistances();
        assertNotNull(distances);
        boolean pathHasBeenGenerated = false;
        outer:
        for (int y = 0; y < distances.length; y++) {
            for (int x = 0; x < distances[0].length; x++) {
                if (distances[y][x] != Integer.MAX_VALUE && distances[y][x] != 0) {
                    pathHasBeenGenerated = true;
                    break outer;
                }
            }
        }
        assertTrue(pathHasBeenGenerated);
    }

    @Test
    public void pathFinderReturnsEmptyArrayWhenNoPathExists() {
        map = new char[][]{
            "############".toCharArray(),
            "############".toCharArray(),
            "############".toCharArray(),
            "############".toCharArray(),
            "############".toCharArray(),
            "############".toCharArray()
        };
        pathFinder.computePaths(map, 1, 1);
        int[][] distances = pathFinder.getDistances();
        assertNotNull(distances);
        boolean pathHasBeenGenerated = false;
        outer:
        for (int y = 0; y < distances.length; y++) {
            for (int x = 0; x < distances[0].length; x++) {
                if (distances[y][x] != Integer.MAX_VALUE && distances[y][x] != 0) {
                    pathHasBeenGenerated = true;
                    break outer;
                }
            }
        }
        assertFalse(pathHasBeenGenerated);
    }
}
