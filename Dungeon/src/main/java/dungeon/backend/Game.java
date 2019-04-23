/*
 * @author olli m
 */
package dungeon.backend;

import dungeon.domain.Actor;
import dungeon.domain.Difficulty;
import dungeon.domain.Monster;
import dungeon.domain.ActorType;
import dungeon.domain.Message;
import dungeon.domain.Node;
import dungeon.domain.Player;
import dungeon.domain.PlayerAction;
import dungeon.domain.Punch;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;
import squidpony.squidmath.RNG;

/**
 * This class handles game logic. The has to be initialized with a separate
 * method call. This makes the class easier to test.
 */
public class Game {

    private RNG rng;
    private char[][] map;
    private Plotter plotter;
    private Player player;
    private PriorityQueue<Actor> queue;
    private ArrayList<Actor> actors;
    private PathFinder pathFinder;
    private int mapSize;
    private int monstersToCreate;
    private double monsterDensity;
    private int dieSize;
    private double visionRadius;
    private double visibilityThreshold;
    private boolean gameOver;
    private boolean debug;
    private Difficulty difficulty;

    public Game() {
        gameOver = false;
        this.queue = new PriorityQueue<>();
        this.actors = new ArrayList<>();
        this.pathFinder = new PathFinder();
        this.difficulty = Difficulty.NORMAL;
        this.monsterDensity = difficulty.monsterDensity;
        this.visionRadius = difficulty.visionRadius;
        this.visibilityThreshold = difficulty.visibilityThreshold;
        this.dieSize = 20;
        this.debug = false;
    }

    public Game(Settings settings) {
        this();
        this.difficulty = settings.getDifficulty();
        this.visionRadius = difficulty.visionRadius;
        this.visibilityThreshold = difficulty.visibilityThreshold;
        this.monsterDensity = difficulty.monsterDensity;
        this.debug = settings.isDebug();
    }

    /**
     * This method creates a MapGenerator object, calls generateMap() and stores
     * the map into the map variable.
     *
     * @param width
     * @param height
     * @throws IllegalArgumentException
     */
    public void initializeMapObjects(int width, int height) throws IllegalArgumentException {
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException("invalid map size");
        }
        rng = new RNG((new Random()).nextInt());
        MapGenerator mapGenerator = new MapGenerator(rng, width, height);
        mapGenerator.generateMap();
        char[][] proceduralMap = mapGenerator.getMap();
        initializeMapObjects(proceduralMap);
    }

    public void initializeMapObjects(char[][] map) throws IllegalArgumentException {
        if (map == null) {
            throw new IllegalArgumentException("map is null");
        }
        this.map = map;
        mapSize = pathFinder.mapSize(this.map, visionRadius);
        plotter = new Plotter(this, this.map, visionRadius);
    }

    /**
     * This method creates a monster at a random location and places in in the
     * queue.
     */
    public Actor createMonster() {
        char[][] populatedMap = plotter.populateMap(null);
        int x;
        int y;
        do {
            x = rng.nextInt(populatedMap[0].length);
            y = rng.nextInt(populatedMap.length);
        } while (populatedMap[y][x] != ' '
                || player.distanceTo(x, y) < visionRadius);
        return createMonster(x, y);
    }

    public Actor createMonster(int x, int y) {
        ActorType monsterType = difficulty.rollType(rng);
        return createMonster(x, y, monsterType);
    }

    public Actor createMonster(int x, int y, ActorType monsterType) {
        Monster monster = new Monster(x, y, monsterType);
        monster.setVisionRatio(visionRadius * visibilityThreshold);
        this.actors.add(monster);
        this.queue.add(monster);
        return monster;
    }

    /**
     * This method creates the player character at a random location.
     */
    public Actor createPlayer() {
        char[][] populatedMap = plotter.populateMap(null);
        int x;
        int y;
        do {
            x = rng.nextInt(populatedMap[0].length);
            y = rng.nextInt(populatedMap.length);
        } while (populatedMap[y][x] != ' ');
        return createPlayer(x, y);
    }

    public Actor createPlayer(int x, int y) {
        player = new Player(x, y);
        this.actors.add(player);
        player.setAttack(new Punch());
        return player;
    }

    /**
     * This method returns a reference to whatever object is located at specific
     * map coordinates.
     *
     * @param point
     * @return the Actor with corresponding coordinates
     */
    public Actor actorAt(Node point) {
        for (Actor actor : actors) {
            if (actor.getPosition().equals(point)) {
                return actor;
            }
        }
        return null;
    }

    /**
     * This method allows a UI class to control the Player object. The player's
     * turn value is incremented by the movement cost upon calling the method
     * act(). After this the player is placed into the queue.
     *
     * @param action
     */
    public void insertAction(PlayerAction action) {
        if (gameOver) {
            return;
        }
        player.setAction(action);
        player.act(this, plotter.populateMap(player));
        playRound();
    }

    /**
     * The game loop. Using a minimum priority queue to determine turn order,
     * which allows intuitive implementation of variable action lengths. When
     * the Player and a different Actor have the same turn value, the Player
     * will always have priority over the other Actor. The loop breaks when the
     * player reemerges from the queue.
     *
     */
    public void playRound() {
        if (gameOver) {
            return;
        }
        queue.add(player);

        spawnMonsters();
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

    public void spawnMonsters() {
        if ((actors.size() - 1) / (double) mapSize < monsterDensity) {
            createMonsters();
        }
    }

    public void createMonsters(int n) {
        n = Math.min(mapSize, actors.size() + n) - actors.size();
        for (int i = 0; i < n; i++) {
            createMonster();
        }
    }

    public void createMonsters() {
        int n = Math.min(mapSize, actors.size() + monstersToCreate) - actors.size();
        for (int i = 0; i < n; i++) {
            createMonster();
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

    public void controlActor(Actor actor) {
        if (actor.getClass() == Monster.class) {
            if (plotter.isVisible(actor.getPosition())) {
                ((Monster) actor).reactOnSight(this);
            }
        }
        actor.act(this, plotter.populateMap(actor));
        queue.add(actor);
    }

    private void endGame() {
        gameOver = true;
        MessageBus.getInstance().push(new Message("You died"));
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

    public void setMonstersToCreate(int monstersToCreate) {
        this.monstersToCreate = monstersToCreate;
    }

    public RNG getRng() {
        return rng;
    }

    public int getDieSize() {
        return dieSize;
    }

    public double getVisionRadius() {
        return visionRadius;
    }

}
