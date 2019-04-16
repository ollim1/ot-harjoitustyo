/*
 * @author olli m
 */
package dungeon.backend;

import dungeon.domain.Actor;
import dungeon.domain.Bite;
import dungeon.domain.Monster;
import dungeon.domain.Node;
import dungeon.domain.Player;
import dungeon.domain.PlayerAction;
import dungeon.domain.Punch;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;
import javafx.scene.input.KeyCode;

public class Game {

    private Random random;
    private char[][] map;
    private Plotter plotter;
    private Player player;
    private PriorityQueue<Actor> queue;
    private ArrayList<Actor> actors;
    private PathFinder pathFinder;
    private boolean gameOver;

    public Game() {
        gameOver = false;
        this.queue = new PriorityQueue<>();
        this.actors = new ArrayList<>();
        this.pathFinder = new PathFinder();
    }

    public void initializeMapObjects(int width, int height) throws IllegalArgumentException {
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException("invalid map size");
        }
        random = new Random();
        MapGenerator mapGenerator = new MapGenerator(random, width, height);
        mapGenerator.generateMap();
        map = mapGenerator.getMap();
        plotter = new Plotter(this, map);
    }

    public void createMonster() {
        char[][] temporaryMap = plotter.populateMap(null);
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
        monster.setAttack(new Bite());
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
        player.setAttack(new Punch());
    }

    public Actor actorAt(Node point) {
        for (Actor actor : actors) {
            if (actor.getPosition().equals(point)) {
                return actor;
            }
        }
        return null;
    }

    public void insertAction(PlayerAction action) {
        if (gameOver) {
            return;
        }
        player.setAction(action);
        player.act(this, plotter.populateMap(player));
        playRound();
    }

    public void playRound() {
        // using a priority queue to keep track of action lengths
        // may add variable actor speed and realistic diagonal movement speed
        // insert or reinsert the player into the queue
        if (gameOver) {
            return;
        }
        queue.add(player);

        while (!queue.isEmpty()) {
            Actor actor = queue.poll();
            if (actor.getHealth() <= 0) {
                if (cleanUpActor(actor)) {
                    return;
                }
                continue;
            }
            if (actor.getClass() == Player.class) { // is the actor a player?
                // wait for input from UI
                break;
            }
            controlActor(actor);
        }
    }

    private boolean cleanUpActor(Actor actor) {
        if (actor.getClass() == Player.class) {
            endGame();
            return true;
        }
        actors.remove(actor);
        return false;
    }

    private void controlActor(Actor actor) {
        // actor is not a player
        if (actor.getClass() == Monster.class) {
            // putting this here for now
            ((Monster) actor).alert(this);
        }
        actor.act(this, plotter.populateMap(actor));
        queue.add(actor);
    }

    private void endGame() {
        gameOver = true;
    }

    public boolean isGameOver() {
        return gameOver;
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

    public Plotter getPlotter() {
        return plotter;
    }

}
