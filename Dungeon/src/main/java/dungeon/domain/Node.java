/*
 * @author olli m
 */
package dungeon.domain;

public class Node implements Comparable<Node> {

    private int x;
    private int y;
    private int distance;

    public Node(int x, int y, int distance) {
        this.x = x;
        this.y = y;
        this.distance = distance;
    }

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.distance = -1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Node translate(Direction direction) {
        this.x += direction.getDifferenceX();
        this.y += direction.getDifferenceY();
        return this;
    }

    public Node translateToNew(Direction direction) {
        return new Node(this.x + direction.getDifferenceX(), this.y + direction.getDifferenceY());
    }

    public double differenceTo(Node that) {
        int differenceX = this.x - that.x;
        int differenceY = this.y - that.y;
        return Math.sqrt(differenceX * differenceX + differenceY * differenceY);
    }

    @Override
    public int compareTo(Node that) {
        if (this.distance == -1 || that.distance == -1) {
            return Integer.MAX_VALUE;
        }
        return this.distance - that.distance;
    }

    /**
     * For HashMaps. I'm not going to take risks by removing this.
     *
     * @return
     */
    @Override
    public int hashCode() {
        return y * 10000 + y;
    }

    /**
     * Can't remember if I've used a List of Nodes or a Map of Nodes anywhere in
     * code or in testing. This may be needed.
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }

        Node that = (Node) o;
        return this.x == that.x && this.y == that.y;
    }
}
