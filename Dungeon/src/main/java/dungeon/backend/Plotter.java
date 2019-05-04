/*
 * @author olli m
 */
package dungeon.backend;

import dungeon.domain.Actor;
import dungeon.domain.MapObject;
import dungeon.domain.Node;
import java.util.ArrayList;
import squidpony.squidgrid.BevelFOV;

/**
 * This class handles plotting the level map. It should draw what an Actor can
 * see. For now only the player has a special status, but in the future
 * vision-affecting statuses may be implemented for all Actors.
 */
public class Plotter {

    private Game game;
    private char[][] map;
    private char[][] playerMap;
    private char[][] playerLevelMap;
    private MapObject[][] playerObjectMap;
    private double[][] visibility;
    private double[][] resistances;
    private double radius;
    private BevelFOV losCalculator;

    /**
     * Only one Plotter should be needed per level map, so the map can be passed
     * here. Actually, the Game object already passes the map.
     *
     * @param game
     * @param map
     * @param radius the radius of the player's field of view
     */
    public Plotter(Game game, char[][] map, double radius) {
        this.game = game;
        this.map = map;
        this.radius = radius;
        setMap(map);
        this.losCalculator = new BevelFOV();
    }

    public void setMap(char[][] map) {
        this.map = map;
        if (map != null) {
            this.playerMap = new char[map.length][map[0].length];
            playerLevelMap = new char[map.length][map[0].length];
            playerObjectMap = new MapObject[map.length][map[0].length];
            formatPlayerMaps();
            generateResistances();
        }
    }

    private void formatPlayerMaps() {
        for (int y = 0; y < playerMap.length; y++) {
            for (int x = 0; x < playerMap[0].length; x++) {
                playerMap[y][x] = '_';
                playerLevelMap[y][x] = '_';
            }
        }
    }

    private void generateResistances() {
        resistances = new double[map[0].length][map.length];
        for (int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                if (map[y][x] == '#') {
                    resistances[x][y] = 1.0;
                } else {
                    resistances[x][y] = 0.0;
                }
            }
        }
    }

    /**
     * Updates the visibility map, the populated map, the level map and the
     * object map based on the player's field of view.
     */
    public void update() {
        updateVisibility();
        updateLevelMaps();
        updateObjectMap();
    }

    private void updateObjectMap() {
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map.length; y++) {
                if (visibility[x][y] > 0.0) {
                    playerObjectMap[y][x] = null;
                }
            }
        }
        for (MapObject mapObject : game.getMapObjects()) {
            Node position = mapObject.getPosition();
            int x = position.getX();
            int y = position.getY();
            if (x >= 0 && x < map[0].length
                    && y >= 0 && y < map.length && visibility[x][y] > 0.0) {
                playerObjectMap[y][x] = mapObject;
            }
        }
    }

    private void updateLevelMaps() {
        char[][] fullMap = populateMap(game.getPlayer());
        for (int x = 0; x < fullMap[0].length; x++) {
            for (int y = 0; y < fullMap.length; y++) {
                if (visibility[x][y] > 0.0) {
                    playerMap[y][x] = fullMap[y][x];
                    playerLevelMap[y][x] = map[y][x];
                }
            }
        }
    }

    private void updateVisibility() {
        Node position = game.getPlayer().getPosition();
        int posX = position.getX();
        int posY = position.getY();
        visibility = losCalculator.calculateFOV(resistances, posX, posY, radius);
    }

    public double[][] getVisibility() {
        return visibility;
    }

    public char[][] getPlayerMap() {
        return playerMap;
    }

    public MapObject[][] getPlayerObjectMap() {
        return playerObjectMap;
    }

    public char[][] getPlayerLevelMap() {
        return playerLevelMap;
    }

    /**
     * This method determines whether or not a point is within sight.
     *
     * @param point
     * @return
     */
    public boolean isVisible(Node point) {
        return visibility[point.getX()][point.getY()] > 0.0;
    }

    /**
     * Returns a copy of the full map of the level with actors overlaid.
     *
     * @param actor
     * @return map with actors overlaid
     */
    public char[][] populateMap(Actor actor) {
        char[][] drawable = copyMap();
        drawActors(drawable);

        return drawable;
    }

    private char[][] copyMap() {
        char[][] drawable = new char[map.length][map[0].length];
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                drawable[y][x] = map[y][x];
            }
        }
        return drawable;
    }

    private void drawActors(char[][] drawable) {
        ArrayList<Actor> actors = game.getActors();
        for (Actor actor : actors) {
            if (!outOfBounds(actor.getPosition().getX(), actor.getPosition().getY())) {
                drawable[actor.getPosition().getY()][actor.getPosition().getX()] = actor.getSymbol();
            }
        }
    }

    private boolean outOfBounds(int x, int y) {
        return x < 1 || x >= map[0].length - 1 || y < 1 || y >= map.length - 1;
    }
}
