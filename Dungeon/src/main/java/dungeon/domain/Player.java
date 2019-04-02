/*
 * @author olli m
 */
package dungeon.domain;

import javafx.scene.input.KeyCode;

public class Player extends Character {

    private static final int MAX_HEALTH = 20;
    private PlayerAction action;

    public Player(int x, int y) {
        super.setHealth(MAX_HEALTH);
        super.setPositionX(x);
        super.setPositionY(y);
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

    @Override
    public void act(char[][] map) {
        if (action == PlayerAction.EAST) {
            super.move(Direction.EAST, map);
        } else if (action == PlayerAction.NORTH) {
            super.move(Direction.NORTH, map);
        } else if (action == PlayerAction.WEST) {
            super.move(Direction.WEST, map);
        } else if (action == PlayerAction.SOUTH) {
            super.move(Direction.SOUTH, map);
        }
    }

}
