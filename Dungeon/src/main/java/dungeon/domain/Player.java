/*
 * @author olli m
 */
package dungeon.domain;

import dungeon.backend.Game;
import javafx.scene.input.KeyCode;

public class Player extends Actor {

    private static final int MAX_HEALTH = 20;
    private PlayerAction action;

    public Player(int x, int y) {
        super.setHealth(MAX_HEALTH);
        super.setMaxHealth(MAX_HEALTH);
        super.setPosition(new Node(x, y));
        boolean[] hostileSymbols = new boolean[Character.MAX_VALUE];
        hostileSymbols['D'] = true;
        super.setHostileSymbols(hostileSymbols);
        super.setInterval(100);
        super.setNextTurn(0);
    }

    @Override
    public char getSymbol() {
        return '@';
    }

    public void setAction(PlayerAction action) {
        this.action = action;
    }

    public void setAction(KeyCode keyCode) {
        if (keyCode == KeyCode.H || keyCode == KeyCode.LEFT) {
            action = PlayerAction.WEST;
        } else if (keyCode == KeyCode.J || keyCode == KeyCode.DOWN) {
            action = PlayerAction.SOUTH;
        } else if (keyCode == KeyCode.K || keyCode == KeyCode.UP) {
            action = PlayerAction.NORTH;
        } else if (keyCode == KeyCode.L || keyCode == KeyCode.RIGHT) {
            action = PlayerAction.EAST;
        } else if (keyCode == KeyCode.PERIOD) {
            action = PlayerAction.STAY;
        }
    }

    public PlayerAction getAction() {
        return action;
    }

    public void heal() {
        setHealth(Math.min(getHealth() + getMaxHealth() * 0.01, getMaxHealth()));
    }
    
    @Override
    public int act(Game game, char[][] map) {
        if (action == PlayerAction.EAST) {
            super.move(Direction.EAST, game, map);
        } else if (action == PlayerAction.NORTH) {
            super.move(Direction.NORTH, game, map);
        } else if (action == PlayerAction.WEST) {
            super.move(Direction.WEST, game, map);
        } else if (action == PlayerAction.SOUTH) {
            super.move(Direction.SOUTH, game, map);
        }
        return 100;
    }

}
