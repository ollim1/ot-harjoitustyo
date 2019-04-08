/*
 * @author olli m
 */
package dungeon.backend;

import dungeon.domain.Actor;
import dungeon.domain.Monster;
import dungeon.domain.Node;
import dungeon.domain.Player;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

public class Game {

    private Random random;
    private char[][] map;
    private Player player;
    private PriorityQueue<Actor> queue;
    private ArrayList<Actor> actors;
    private PathFinder pathFinder;

    public Game(int width, int height) throws IllegalArgumentException {
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException("invalid map size");
        }
        random = new Random();
        MapGenerator mapGenerator = new MapGenerator(random, width, height);
        mapGenerator.generateMap();
        map = mapGenerator.getMap();

        this.queue = new PriorityQueue<>();
        this.actors = new ArrayList<>();
        this.pathFinder = new PathFinder();
    }

    public void createMonster() {
        char[][] temporaryMap = populateMap(null);
        int monsterX;
        int monsterY;
        while (true) {
            monsterX = random.nextInt(temporaryMap[0].length);
            monsterY = random.nextInt(temporaryMap.length);
            if (map[monsterY][monsterX] == ' ') {
                break;
            }
        }
        Monster monster = new Monster(monsterX, monsterY);
        this.actors.add(monster);
        this.queue.add(monster);
    }

    public void createPlayer() {
        int playerX;
        int playerY;
        while (true) {
            playerX = random.nextInt(map[0].length);
            playerY = random.nextInt(map.length);
            if (map[playerY][playerX] == ' ') {
                break;
            }
        }
        player = new Player(playerX, playerY);
        this.actors.add(player);
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
        for (Actor actor : actors) {
            if (!outOfBounds(actor.getPosition().getX(), actor.getPosition().getY())) {
                drawable[actor.getPosition().getY()][actor.getPosition().getX()] = actor.getSymbol();
            }
        }
    }

    public Actor characterAt(Node point) {
        for (Actor actor : actors) {
            if (actor.getPosition().equals(point)) {
                return actor;
            }
        }
        return null;
    }

    public void playRound() {
        // using a priority queue to keep track of action lengths
        // may add variable actor speed and realistic diagonal movement speed
        // insert or reinsert the player into the queue
        queue.add(player);

        while (true) {
            Actor actor = queue.poll();
            if (actor.getClass() == Player.class) { // is the actor a player?
                // wait for input from UI
                // when the input has been processed and the player has acted,
                break;
            }
            // actor is not a player
            if (actor.getClass() == Monster.class) {
                // putting this here for now
                ((Monster) actor).alert(this);
            }
            actor.incrementTurn(actor.act(this, populateMap(actor)));
            queue.add(actor);
        }
    }

    private boolean outOfBounds(int x, int y) {
        return x < 1 || x >= map[0].length - 1 || y < 1 || y >= map.length;
    }

    public Player getPlayer() {
        return player;
    }

    public char[][] getMap() {
        return map;
    }

    public PathFinder getPathFinder() {
        return pathFinder;
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

}
