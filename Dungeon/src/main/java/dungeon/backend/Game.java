/*
 * @author olli m
 */
package dungeon.backend;

import dungeon.domain.Monster;
import dungeon.domain.Player;
import dungeon.domain.PlayerAction;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Random;

public class Game {

    private Random random;
    private char[][] map;
    private Player player;
    private ArrayList<Monster> monsters;

    public Game(int width, int height) throws IllegalArgumentException {
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException("invalid map size");
        }
        random = new Random();
        MapGenerator mapGenerator = new MapGenerator(random, width, height);
        mapGenerator.generateMap();
        map = mapGenerator.getMap();

        int playerX;
        int playerY;
        while (true) {
            playerX = random.nextInt(width);
            playerY = random.nextInt(height);
            if (map[playerY][playerX] == ' ') {
                break;
            }
        }
        player = new Player(playerX, playerY);
        this.monsters = new ArrayList<>();
    }

    public char[][] drawMap() {
        char[][] drawable = copyMap();
        drawMonsters(drawable);
        int playerX = player.getPositionX();
        int playerY = player.getPositionY();
        if (!outOfBounds(playerY, playerX)) {
            drawable[player.getPositionY()][player.getPositionX()] = '@';
        }

        return drawable;
    }

    private char[][] copyMap() {
        /*
        * returns the map with objects overlayed
         */
        char[][] drawable = new char[map.length][map[0].length];
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                drawable[y][x] = map[y][x];
            }
        }
        return drawable;
    }

    private void drawMonsters(char[][] drawable) {
        for (Monster monster : monsters) {
            if (!outOfBounds(monster.getPositionY(), monster.getPositionX())) {
                drawable[monster.getPositionY()][monster.getPositionX()] = 'D';
            }
        }
    }

    public void tick() {
        player.act(map);
        for (Monster monster : monsters) {
            monster.act(map);
        }
    }

    private boolean outOfBounds(int x, int y) {
        return x < 1 || x >= map[0].length - 1 || y < 1 || y >= map.length;
    }

    public Player getPlayer() {
        return player;
    }

}
