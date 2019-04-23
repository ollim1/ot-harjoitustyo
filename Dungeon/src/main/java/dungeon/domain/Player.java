/*
 * @author olli m
 */
package dungeon.domain;

import dungeon.backend.Game;
import java.util.HashMap;

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
        super.setHealth(MAX_HEALTH);
        super.setMaxHealth(MAX_HEALTH);
        super.setPosition(new Node(x, y));
        super.setIntervalModifier(1.0);
        boolean[] hostileSymbols = new boolean[Character.MAX_VALUE];
        for (ActorType monsterType : ActorType.values()) {
            hostileSymbols[monsterType.symbol] = true;
        }
        super.setHostileSymbols(hostileSymbols);
        super.setNextTurn(0);
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

    @Override
    public void act(Game game, char[][] map) {
        if (action == PlayerAction.STAY) {
            super.idle();
        } else {
            super.move(ACTION_TO_DIRECTION_TRANSLATION.get(action), game, map);
        }
        heal();
    }

}
