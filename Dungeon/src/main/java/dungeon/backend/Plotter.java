/*
 * @author olli m
 */
package dungeon.backend;

import dungeon.domain.Actor;
import dungeon.domain.Node;
import java.util.ArrayList;
import squidpony.squidgrid.BevelFOV;

public class Plotter {

    private Game game;
    private char[][] map;
    private char[][] playerMap;
    private double[][] visibility;
    private double[][] resistances;
    private BevelFOV losCalculator;

    public Plotter(Game game, char[][] map) {
        this.game = game;
        this.map = map;
        setMap(map);
        this.losCalculator = new BevelFOV();
    }

    public void setMap(char[][] map) {
        this.map = map;
        if (map != null) {
            this.playerMap = new char[map.length][map[0].length];
            formatPlayerMap();
            generateResistances();
        }
    }

    private void formatPlayerMap() {
        for (int y = 0; y < playerMap.length; y++) {
            for (int x = 0; x < playerMap[0].length; x++) {
                playerMap[y][x] = '#';
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

    public void update() {
        Node position = game.getPlayer().getPosition();
        int posX = position.getX();
        int posY = position.getY();
        visibility = losCalculator.calculateFOV(resistances, posX, posY);
        char[][] fullMap = populateMap(game.getPlayer());
        for (int x = 0; x < fullMap[0].length; x++) {
            for (int y = 0; y < fullMap.length; y++) {
                if (visibility[x][y] > 0.0) {
                    playerMap[y][x] = fullMap[y][x];
                }
            }
        }
    }

    public double[][] getVisibility() {
        return visibility;
    }

    public char[][] getPlayerMap() {
        return playerMap;
    }

    public char[][] populateMap(Actor actor) {
        /* 
         * returns the map with objects overlayed
         * TODO: determine output based on actor status
         */
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
        return x < 1 || x >= map[0].length - 1 || y < 1 || y >= map.length;
    }
}
