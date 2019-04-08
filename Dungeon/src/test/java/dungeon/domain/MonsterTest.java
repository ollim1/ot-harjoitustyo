/*
 * @author olli m
 */
package dungeon.domain;

import dungeon.backend.Game;
import dungeon.backend.PathFinder;
import java.util.ArrayList;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class MonsterTest {

    private static class MockGame extends Game {

        public Player player;
        public char[][] map;
        public PathFinder pathFinder;
        public ArrayList<Actor> actors;

        public MockGame() throws IllegalArgumentException {
            super(10, 10);
            this.actors = new ArrayList<>();
        }

        public MockGame(Player player, char[][] map, PathFinder pathFinder, int width, int height) throws IllegalArgumentException {
            super(width, height);
            this.player = player;
            this.map = map;
            this.pathFinder = pathFinder;
            this.actors = new ArrayList<>();
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
        public char[][] populateMap(Actor actor) {
            char[][] copy = new char[map.length][map[0].length];
            for (int y = 0; y < map.length; y++) {
                for (int x = 0; x < map[0].length; x++) {
                    copy[y][x] = map[y][x];
                }
            }
            for (Actor a : actors) {
                copy[a.getPosition().getY()][a.getPosition().getX()] = a.getSymbol();
            }
            return copy;
        }

        @Override
        public Actor characterAt(Node point) {
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
    public void setUp() {
        try {
            game = new MockGame();
        } catch (Exception e) {
            System.err.println("illegal argument exception: " + e.getMessage());
        }
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
        game.map = testMap;
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
        char[][] populatedMap = game.populateMap(monster);
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
        Node expected = new Node(4, 3);
        Node actual = monster.getPosition();
        if (!expected.equals(actual)) {
            fail("expected " + expected + ", but got " + actual);
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
        Node expected = new Node(4, 3);
        Node actual = monster.getPosition();
        if (!expected.equals(actual)) {
            fail("expected " + expected + ", but got " + actual);
        }
    }
}
