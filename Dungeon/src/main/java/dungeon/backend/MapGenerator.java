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

    /*
     * this class generates a map with as of yet undecided parameters
     * for now it just returns a predetermined map
     */
    private char[][] map;
    private IDungeonGenerator dungeonGenerator;
    private Random random;

    /* the map, stored in a printable format
     * (Java lacks raw console i/o, which makes text-based games unpleasant to play)
     */
    public MapGenerator(Random random, int width, int height) {
        this.map = new char[width + 2][height + 2];
        this.random = random;
        this.dungeonGenerator = new FlowingCaveGenerator(width, height,
                TilesetType.DEFAULT_DUNGEON, new RNG(random.nextInt()));
    }

    public void generateBetaMap() {
        fillMap();
        String[] placeholderMap = setPlaceHolderMap();
        int offsetX = map[0].length / 2 - placeholderMap[0].length() / 2;
        int offsetY = map.length / 2 - placeholderMap.length / 2;
        insertPlaceHolderMap(placeholderMap, offsetX, offsetY);
    }

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
