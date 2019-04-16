/*
 * @author olli m
 */
package dungeon.domain;

public enum Direction {

    /* this enumerator should map directions to movements */
    NORTH(0, -1, 100),
    NORTHWEST(-1, -1, 141),
    WEST(-1, 0, 100),
    SOUTHWEST(-1, 1, 141),
    SOUTH(0, 1, 100),
    SOUTHEAST(1, 1, 141),
    EAST(1, 0, 100),
    NORTHEAST(1, -1, 141);

    private final int differenceX;
    private final int differenceY;
    private final int cost;

    private Direction(int differenceX, int differenceY, int cost) {
        this.differenceX = differenceX;
        this.differenceY = differenceY;
        this.cost = cost;
    }

    public int getDifferenceX() {
        return differenceX;
    }

    public int getDifferenceY() {
        return differenceY;
    }

    public int cost() {
        return cost;
    }

    public Direction opposite() {
        if (this == NORTH) {
            return SOUTH;
        } else if (this == NORTHWEST) {
            return SOUTHEAST;
        } else if (this == WEST) {
            return EAST;
        } else if (this == SOUTHWEST) {
            return NORTHEAST;
        } else if (this == SOUTH) {
            return NORTH;
        } else if (this == SOUTHEAST) {
            return NORTHWEST;
        } else if (this == EAST) {
            return WEST;
        } else {
            return SOUTHWEST;
        }
    }
}
