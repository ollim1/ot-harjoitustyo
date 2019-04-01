/*
 * @author olli m
 */
package dungeon.domain;

public enum Direction {

    /* this enumerator should map directions to movements */
    NORTH(0, -1),
    SOUTH(0, 1),
    EAST(1, 0),
    WEST(-1, 0);

    private final int differenceX;
    private final int differenceY;

    private Direction(int differenceX, int differenceY) {
        this.differenceX = differenceX;
        this.differenceY = differenceY;
    }

    public int getDifferenceX() {
        return differenceX;
    }

    public int getDifferenceY() {
        return differenceY;
    }

}
