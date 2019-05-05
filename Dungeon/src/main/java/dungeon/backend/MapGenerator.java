/*
 * @author olli m
 */
package dungeon.backend;

import java.util.Random;
import squidpony.squidgrid.mapping.IDungeonGenerator;
import squidpony.squidgrid.mapping.OrganicMapGenerator;
import squidpony.squidmath.RNG;

/**
 * A map generator that uses SquidLib
 *
 * @author londes
 */
public class MapGenerator {

    private char[][] map;
    private IDungeonGenerator dungeonGenerator;

    /**
     * The constructor for this class takes a random number number generator as
     * an argument for seeding.
     *
     * @param random
     * @param width
     * @param height
     */
    public MapGenerator(Random random, int width, int height) {
        this(new RNG(random.nextInt()), width, height);
    }

    public MapGenerator(RNG rng, int width, int height) {
        this.map = new char[width + 2][height + 2];
        this.dungeonGenerator = new OrganicMapGenerator(width, height, rng);
    }

    /**
     * Calls Squidlib to generate a map, stores the result into the map
     * variable.
     */
    public void generateMap() {
        char[][] tempMap = dungeonGenerator.generate();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (x < tempMap.length && y < tempMap[0].length
                        && tempMap[x][y] == '.') {
                    map[y][x] = ' ';
                } else {
                    map[y][x] = '#';
                }
            }
        }
    }

    public char[][] getMap() {
        return map;
    }

}
