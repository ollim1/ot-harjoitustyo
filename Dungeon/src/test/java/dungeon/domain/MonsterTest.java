/*
 * @author olli m
 */
package dungeon.domain;

import dungeon.backend.Game;
import dungeon.backend.PathFinder;
import dungeon.backend.Plotter;
import java.util.ArrayList;
import java.util.HashSet;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class MonsterTest {

    private static class MockPlotter extends Plotter {

        public MockPlotter(Game game, char[][] map) {
            super(game, map);
        }

        @Override
        public char[][] getPlayerMap() {
            throw new UnsupportedOperationException("operation not supported");
        }

        @Override
        public double[][] getVisibility() {
            throw new UnsupportedOperationException("operation not supported");
        }

    }

    private static class MockGame extends Game {

        public Player player;
        public char[][] map;
        public PathFinder pathFinder;
        public ArrayList<Actor> actors;
        public Plotter plotter;

        public MockGame() throws IllegalArgumentException {
            this.actors = new ArrayList<>();
            this.plotter = new MockPlotter(this, map);
        }

        public MockGame(Player player, char[][] map, PathFinder pathFinder, int width, int height) throws IllegalArgumentException {
            this.player = player;
            this.map = map;
            this.pathFinder = pathFinder;
            this.actors = new ArrayList<>();
            this.plotter = new MockPlotter(this, map);
        }

        @Override
        public Plotter getPlotter() {
            return plotter;
        }

        @Override
        public Player getPlayer() {
            return this.player;
        }

        @Override
        public char[][] getMap() {
            return this.map;
        }

        @Override
        public PathFinder getPathFinder() {
            return this.pathFinder;
        }

        @Override
        public ArrayList<Actor> getActors() {
            return actors;
        }

        @Override
        public Actor actorAt(Node point) {
            for (Actor actor : actors) {
                if (actor.getPosition().equals(point)) {
                    return actor;
                }
            }
            return null;
        }

        public void addActor(Actor actor) {
            actors.add(actor);
        }
    }

    private static class MockPathFinder extends PathFinder {

        public Direction[][] paths;
        public Node oldPlayerPosition;

        public MockPathFinder(Direction[][] paths, Node oldPlayerPosition) {
            this.paths = paths;
            this.oldPlayerPosition = oldPlayerPosition;
        }

        @Override
        public Direction[][] getPaths() {
            return paths;
        }

        @Override
        public void computePaths(char[][] map, int x, int y) {
        }

        @Override
        public Node getOldPlayerPosition() {
            return oldPlayerPosition;
        }

    }

    private static class MockAttack implements Attack {

        @Override
        public double apply(Actor source, Actor target) {
            return target.damage(1);
        }

    }
    private MockGame game;
    private Monster monster;
    private char[][] testMap;

    public MonsterTest() {
    }

    @Before
    public void setUp() throws IllegalArgumentException {
        game = new MockGame();
        testMap = new char[][]{
            "######".toCharArray(),
            "#    #".toCharArray(),
            "#    #".toCharArray(),
            "#    #".toCharArray(),
            "#    #".toCharArray(),
            "######".toCharArray()
        };
    }

    @Test
    public void constructorAndSettersWork() {
        monster = new Monster(1, 1);
        monster.setState(ActorState.ATTACK);
    }

    @Test
    public void monsterMoves() {
        Direction[][] testPath = new Direction[6][6];
        for (int y = 0; y < testPath.length; y++) {
            for (int x = 0; x < testPath[0].length; x++) {
                testPath[y][x] = Direction.WEST;
            }
        }

        MockPathFinder pathFinder = new MockPathFinder(testPath, new Node(-1, -1));
        game.map = testMap;
        game.pathFinder = pathFinder;
        monster = new Monster(3, 3);
        Player player = new Player(1, 1);
        game.player = player;
        monster.setState(ActorState.ATTACK);
        monster.setPaths(testPath);
        monster.setAttack(new MockAttack());
        monster.act(game, testMap);
        Node expected = new Node(2, 3);
        Node actual = monster.getPosition();
        if (!expected.equals(actual)) {
            fail("Expected " + expected + ", but got " + actual);
        }
    }

    @Test
    public void monsterAttacks() {
        Direction[][] testPath = new Direction[6][6];
        for (int y = 0; y < testPath.length; y++) {
            for (int x = 0; x < testPath[0].length; x++) {
                testPath[y][x] = Direction.WEST;
            }
        }

        MockPathFinder pathFinder = new MockPathFinder(testPath, new Node(-1, -1));
        System.out.println("game " + game);
        game.map = testMap;
        game.plotter.setMap(game.map);
        game.pathFinder = pathFinder;
        monster = new Monster(3, 3);
        Player player = new Player(2, 3);
        double originalHealth = player.getHealth();
        game.player = player;
        monster.setState(ActorState.ATTACK);
        monster.setPaths(testPath);
        monster.setAttack(new MockAttack());
        game.addActor(player);
        game.addActor(monster);
        System.out.println("plotter: " + game.getPlotter());
        char[][] populatedMap = game.getPlotter().populateMap(monster);
        monster.act(game, populatedMap);
        double expected = originalHealth - 1;
        double actual = player.getHealth();
        if (expected != actual) {
            fail("Expected " + expected + ", but got " + actual);
        }
    }

    @Test
    public void monsterStaysStillIfNoPathExists() {
        PathFinder pathFinder = new PathFinder();
        game.map = testMap;
        game.pathFinder = pathFinder;
        monster = new Monster(4, 3);
        monster.setState(ActorState.ATTACK);
        testMap = new char[][]{
            "######".toCharArray(),
            "#  # #".toCharArray(),
            "#  # #".toCharArray(),
            "#  # #".toCharArray(),
            "#  # #".toCharArray(),
            "######".toCharArray()
        };
        pathFinder.computePaths(testMap, 2, 3);
        Direction[][] paths = pathFinder.getPaths();
        monster.setPaths(paths);
        monster.act(game, testMap);
        Node expected = new Node(4, 3);
        Node actual = monster.getPosition();
        if (!expected.equals(actual)) {
            fail("expected " + expected + ", but got " + actual);
        }
    }

    @Test
    public void monsterFlees() {
        PathFinder pathFinder = new PathFinder();
        game.map = testMap;
        game.pathFinder = pathFinder;
        monster = new Monster(3, 3);
        Player player = new Player(2, 3);
        game.player = player;
        monster.setState(ActorState.FLEE);
        pathFinder.computePaths(testMap, 2, 3);
        Direction[][] paths = pathFinder.getPaths();
        monster.setPaths(paths);
        monster.act(game, testMap);
        HashSet<Node> expected = new HashSet<>();
        expected.add(new Node(4, 2));
        expected.add(new Node(4, 3));
        expected.add(new Node(4, 4));
        Node actual = monster.getPosition();
        if (!expected.contains(actual)) {
            fail("expected (4,2), (4,3) or (4,4), but got " + actual);
        }
    }

    @Test
    public void monsterFleesIfLowHP() {
        game.map = testMap;
        monster = new Monster(3, 3);
        monster.setHealth((int) (monster.getMaxHealth() * 0.3));
        Player player = new Player(2, 3);
        game.player = player;
        monster.act(game, testMap);
        HashSet<Node> expected = new HashSet<>();
        expected.add(new Node(4, 2));
        expected.add(new Node(4, 3));
        expected.add(new Node(4, 4));
        Node actual = monster.getPosition();
        if (!expected.contains(actual)) {
            fail("expected (4,2), (4,3) or (4,4), but got " + actual);
        }
    }
}
