/*
 * @author olli m
 */
package dungeon.domain;

import dungeon.backend.Game;
import java.util.HashMap;

/**
 * This class represents the player character. The abstract methods of the Actor
 * class are implemented in a way that allows manual control. This class is
 * relatively small, because most of the necessary functionality is implemented
 * in the Actor class.
 *
 * @author londes
 */
public class Player extends Actor {

    private static final int MAX_HEALTH = 40;
    private PlayerAction action;
    private static final HashMap<PlayerAction, Direction> ACTION_TO_DIRECTION_TRANSLATION = new HashMap<PlayerAction, Direction>() {
        {
            put(PlayerAction.NORTH, Direction.NORTH);
            put(PlayerAction.NORTHWEST, Direction.NORTHWEST);
            put(PlayerAction.WEST, Direction.WEST);
            put(PlayerAction.SOUTHWEST, Direction.SOUTHWEST);
            put(PlayerAction.SOUTH, Direction.SOUTH);
            put(PlayerAction.SOUTHEAST, Direction.SOUTHEAST);
            put(PlayerAction.EAST, Direction.EAST);
            put(PlayerAction.NORTHEAST, Direction.NORTHEAST);
        }
    };

    public Player(int x, int y) {
        super(new Node(x, y), MAX_HEALTH, null);
        boolean[] hostileSymbols = new boolean[Character.MAX_VALUE];
        for (ActorType monsterType : ActorType.values()) {
            hostileSymbols[monsterType.symbol] = true;
        }
        super.setHostileSymbols(hostileSymbols);
    }

    @Override
    public char getSymbol() {
        return '@';
    }

    public void setAction(PlayerAction action) {
        this.action = action;
    }

    public PlayerAction getAction() {
        return action;
    }

    /**
     * This method carries out the action that has been set.
     *
     * @param game
     * @param map a populated map
     */
    @Override
    public void act(Game game, char[][] map) {
        if (action == PlayerAction.STAY) {
            idle();
        } else {
            super.move(ACTION_TO_DIRECTION_TRANSLATION.get(action), game, map);
        }
    }

}
