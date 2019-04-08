/*
 * @author olli m
 */
package dungeon.domain;

public enum Direction {

    /* this enumerator should map directions to movements */
    NORTH(0, -1, 100),
    SOUTH(0, 1, 100),
    EAST(1, 0, 100),
    WEST(-1, 0, 100);

    private final int differenceX;
    private final int differenceY;
    private final int distanceTraveled;

    private Direction(int differenceX, int differenceY, int distanceTraveled) {
        this.differenceX = differenceX;
        this.differenceY = differenceY;
        this.distanceTraveled = distanceTraveled;
    }

    public int getDifferenceX() {
        return differenceX;
    }

    public int getDifferenceY() {
        return differenceY;
    }

    public int getDistanceTraveled() {
        return distanceTraveled;
    }

    public Direction getOpposite() {
        if (this == NORTH) {
            return SOUTH;
        } else if (this == SOUTH) {
            return NORTH;
        } else if (this == EAST) {
            return WEST;
        } else if (this == WEST) {
            return EAST;
        }
        return null;
    }
}
