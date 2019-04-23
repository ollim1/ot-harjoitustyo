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
            super(game, map, Double.NaN);
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
        public int[][] distance;
        public Node oldCenter;

        public MockPathFinder(int[][] distanceMap, Node oldCenter) {
            this.distance = distanceMap;
            this.oldCenter = oldCenter;
        }

        @Override
        public DijkstraMap dijkstraMap() {
            int[][] copy = new int[distance.length][distance[0].length];
            for (int y = 0; y < copy.length; y++) {
                for (int x = 0; x < copy[0].length; x++) {
                    copy[y][x] = distance[y][x];
                }
            }
            return new DijkstraMap(copy);
        }

        @Override
        public void computePaths(char[][] map, int x, int y) {
        }

        @Override
        public Node getOldCenter() {
            return oldCenter;
        }

    }

    private static class MockAttack implements Attack {

        @Override
        public double apply(Game game, Actor source, Actor target) {
            return target.damage(1);
        }

        @Override
        public int cost() {
            return 100;
        }
    }
    private Game game;
    private Monster monster;
    private char[][] testMap;

    public MonsterTest() {
    }

    @Before
    public void setUp() throws IllegalArgumentException {
        game = new Game();
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
    public void monsterAttacks() {
        game.initializeMapObjects(testMap);
        game.createPlayer(3, 3);
        monster = (Monster) game.createMonster(4, 3, MonsterType.ORC);
        monster.setAttack(new MockAttack());
        game.getPlotter().update();
        game.controlActor(monster);
    }

    @Test
    public void monsterStaysStillIfNoPathExists() {
        testMap = new char[][]{
            "######".toCharArray(),
            "#  # #".toCharArray(),
            "#  # #".toCharArray(),
            "#  # #".toCharArray(),
            "#  # #".toCharArray(),
            "######".toCharArray()
        };
        game.initializeMapObjects(testMap);
        game.createPlayer(2, 3);
        monster = (Monster) game.createMonster(4, 3, MonsterType.ORC);
        game.getPlotter().update();
        game.controlActor(monster);
        Node expected = new Node(4, 3);
        Node actual = monster.getPosition();
        if (!expected.equals(actual)) {
            fail("expected " + expected + ", but got " + actual);
        }
    }

    @Test
    public void monsterFlees() {
        game.initializeMapObjects(testMap);
        game.createPlayer(2, 3);
        monster = (Monster) game.createMonster(3, 3, MonsterType.ORC);
        monster.setFleeThreshold(2.0);
        game.getPlotter().update();
        game.controlActor(monster);
        HashSet<Node> expected = new HashSet<>();
        expected.add(new Node(4, 2));
        expected.add(new Node(4, 3));
        expected.add(new Node(4, 4));
        expected.add(new Node(3, 2));
        expected.add(new Node(3, 4));
        Node actual = monster.getPosition();
        if (!expected.contains(actual)) {
            fail("expected (4,2), (4,3) or (4,4), but got " + actual);
        }
    }

}
