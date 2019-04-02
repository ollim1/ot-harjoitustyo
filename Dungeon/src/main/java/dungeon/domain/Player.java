/*
 * @author olli m
 */
package dungeon.domain;

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

    public PlayerAction getAction() {
        return action;
    }

    @Override
    public void act(char[][] map) {
        switch (action) {
            case EAST:
                super.move(Direction.EAST, map);
                break;
            case NORTH:
                super.move(Direction.NORTH, map);
                break;
            case WEST:
                super.move(Direction.WEST, map);
                break;
            case SOUTH:
                super.move(Direction.SOUTH, map);
                break;
            default:
                break;
        }
    }

}
