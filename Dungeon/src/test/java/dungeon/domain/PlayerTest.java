/*
 * @author olli m
 */
package dungeon.domain;

import dungeon.backend.Game;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {

    private Game game;

    public PlayerTest() {
    }

    @Before
    public void setUp() {
        game = new Game();
        game.initializeMapObjects(new char[][]{
            "#######".toCharArray(),
            "#     #".toCharArray(),
            "#     #".toCharArray(),
            "#     #".toCharArray(),
            "#     #".toCharArray(),
            "#     #".toCharArray(),
            "#######".toCharArray(),});
    }

    @Test
    public void playerActs() {
        game.createPlayer(3, 3);
        game.insertAction(PlayerAction.NORTH);
        Node expected = new Node(3, 2);
        if (!game.getPlayer().getPosition().equals(expected)) {
            fail("The player character did not move north");
        }
        game.insertAction(PlayerAction.SOUTH);
        expected = new Node(3, 3);
        if (!game.getPlayer().getPosition().equals(expected)) {
            fail("The player character did not move south");
        }
        game.insertAction(PlayerAction.WEST);
        expected = new Node(2, 3);
        if (!game.getPlayer().getPosition().equals(expected)) {
            fail("The player character did not move west");
        }
        game.insertAction(PlayerAction.EAST);
        game.insertAction(PlayerAction.EAST);
        expected = new Node(4, 3);
        if (!game.getPlayer().getPosition().equals(expected)) {
            fail("The player character did not move east");
        }
        game.insertAction(PlayerAction.SOUTHWEST);
        game.insertAction(PlayerAction.NORTHWEST);
        expected = new Node(2, 3);
        if (!game.getPlayer().getPosition().equals(expected)) {
            fail("The player character did not move west diagonally");
        }

        game.insertAction(PlayerAction.NORTHEAST);
        game.insertAction(PlayerAction.SOUTHEAST);
        expected = new Node(4, 3);
        if (!game.getPlayer().getPosition().equals(expected)) {
            fail("The player character did not move east diagonally");
        }
        game.insertAction(PlayerAction.STAY);
        if (!game.getPlayer().getPosition().equals(expected)) {
            fail("The player does not stay still");
        }
    }
    
    @Test
    public void movementCostsATurn() {
        game.createPlayer(3,3);
        game.insertAction(PlayerAction.NORTH);
        if (game.getPlayer().getNextTurn() == 0) {
            fail("The player character's movement does not have a price");
        }
    }
}
