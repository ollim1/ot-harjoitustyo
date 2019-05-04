/*
 * @author olli m
 */
package dungeon.domain;

import dungeon.backend.Game;
import dungeon.backend.MessageBus;
import java.util.HashSet;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class MonsterTest {

    private static class MockAttack extends Attack {

        private double power;

        public MockAttack(double power) {
            this.power = power;
        }

        public MockAttack() {
            this(1);
        }

        @Override
        public double apply(Game game, Actor source, Actor target) {
            return target.damage(power);
        }

        @Override
        public int cost() {
            return 100;
        }

        @Override
        public String actionVerb() {
            return "did terrible things to";
        }

    }
    private Game game;
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
    public void monsterAttacks() {
        game.initializeMapObjects(testMap);
        game.createPlayer(3, 3);
        Monster monster = (Monster) game.createMonster(4, 3, ActorType.ORC);
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
        Monster monster = (Monster) game.createMonster(4, 3, ActorType.ORC);
        game.getPlotter().update();
        game.controlActor(monster);
        Node expected = new Node(4, 3);
        Node actual = monster.getPosition();
        if (!expected.equals(actual)) {
            fail("Expected " + expected + ", but got " + actual);
        }
    }

    @Test
    public void monsterFlees() {
        game.initializeMapObjects(testMap);
        game.createPlayer(2, 3);
        Monster monster = (Monster) game.createMonster(3, 3, ActorType.ORC);
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
            fail("Expected (4,2), (4,3) or (4,4), but got " + actual);
        }
    }

    @Test
    public void monsterAttacksThinAir() {
        game.initializeMapObjects(testMap);
        game.createPlayer(2, 3);
        Monster monster = (Monster) game.createMonster(3, 3, ActorType.ORC);
        char[][] illusionMap = new char[testMap.length][testMap[0].length];
        for (int y = 0; y < illusionMap.length; y++) {
            System.arraycopy(testMap[y], 0, illusionMap[y], 0, illusionMap.length);
        }
        illusionMap[4][3] = '@';
        monster.move(Direction.SOUTH, game, illusionMap);
        if (MessageBus.getInstance().poll() == null) {
            fail("Monster did not attack thin air");
        }
    }

}
