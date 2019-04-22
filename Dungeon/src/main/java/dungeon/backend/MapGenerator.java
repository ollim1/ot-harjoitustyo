/*
 * @author olli m
 */
package dungeon.backend;

import java.util.Random;
import squidpony.squidgrid.mapping.FlowingCaveGenerator;
import squidpony.squidgrid.mapping.IDungeonGenerator;
import squidpony.squidgrid.mapping.styled.TilesetType;
import squidpony.squidmath.RNG;

public class MapGenerator {

    /**
     * A map generator that uses SquidLib
     */
    private char[][] map;
    private IDungeonGenerator dungeonGenerator;
    private RNG rng;

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
        this.rng = rng;
        this.dungeonGenerator = new FlowingCaveGenerator(width, height,
                TilesetType.DEFAULT_DUNGEON, rng);
    }

    /**
     * Generates the original test map and stores it in the map variable.
     */
    public void generateBetaMap() {
        fillMap();
        String[] placeholderMap = setPlaceHolderMap();
        int offsetX = map[0].length / 2 - placeholderMap[0].length() / 2;
        int offsetY = map.length / 2 - placeholderMap.length / 2;
        insertPlaceHolderMap(placeholderMap, offsetX, offsetY);
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

    private void insertPlaceHolderMap(String[] placeholderMap, int offsetX, int offsetY) {
        int maximumX = Math.min(map[0].length, offsetX + placeholderMap[0].length());
        int maximumY = Math.min(map.length, offsetY + placeholderMap.length);
        for (int x = offsetX; x < maximumX; x++) {
            for (int y = offsetY; y < maximumY; y++) {
                int originalX = x - offsetX;
                int originalY = y - offsetY;
                map[y][x] = placeholderMap[originalY].charAt(originalX);
            }
        }
    }

    private String[] setPlaceHolderMap() {
        String[] placeholderMap = new String[]{
            "######     ",
            "   ###     ",
            "   ###     ",
            "           ",
            "   ########",
            "   ########"
        };
        return placeholderMap;
    }

    private void fillMap() {
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map.length; y++) {
                map[y][x] = '#';
            }
        }
    }

    public void setMap(char[][] map) {
        this.map = map;
    }

    public char[][] getMap() {
        return map;
    }

}
