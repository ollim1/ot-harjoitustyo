/*
 * @author olli m
 */
package dungeon.backend;

public class MapGenerator {

    /*
     * this class generates a map with as of yet undecided parameters
     * for now it just returns a predetermined map
     */
    private char[][] map;

    /* the map, stored in a printable format
     * (Java lacks raw console i/o, which makes text-based games unpleasant to play)
     */
    public MapGenerator(int width, int height) {
        this.map = new char[width + 2][height + 2];
    }

    public void generateMap() {
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map.length; y++) {
                map[y][x] = '#';
            }
        }
        String[] placeholderMap = new String[]{
            "######     ",
            "   ###     ",
            "   ###     ",
            "           ",
            "   ########",
            "   ########"
        };
        int offsetX = map[0].length / 2 - placeholderMap[0].length() / 2;
        int offsetY = map.length / 2 - placeholderMap.length / 2;
        for (int x = 0; x < placeholderMap[0].length(); x++) {
            for (int y = 0; y < placeholderMap.length; y++) {
                int placeX = offsetX + x;
                int placeY = offsetY + y;
                if (placeX < map[0].length - 1
                        && placeX > 0
                        && placeY < map.length - 1
                        && placeY > 0) {
                    map[placeY][placeX] = placeholderMap[y].charAt(x);
                }
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
