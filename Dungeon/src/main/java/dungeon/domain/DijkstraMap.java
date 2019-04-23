/*
 * @author olli m
 */
package dungeon.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javafx.scene.paint.Color;

public class DijkstraMap {

    private double escapeBias = 1.6;
    private int[][] values;

    public DijkstraMap(int[][] distance) {
        this.values = distance;
        escapeBias = 1.6;
    }

    public DijkstraMap(int[][] distance, double escapeBias) {
        this(distance);
        this.escapeBias = escapeBias;
    }

    public DijkstraMap(int w, int h) {
        this.values = new int[h][w];
        for (int y = 0; y < values.length; y++) {
            for (int x = 0; x < values.length; x++) {
                values[y][x] = Integer.MAX_VALUE;
            }
        }
    }

    public DijkstraMap copy() {
        int[][] copy = new int[values.length][values[0].length];
        for (int y = 0; y < copy.length; y++) {
            System.arraycopy(values[y], 0, copy[y], 0, copy.length);
        }
        return new DijkstraMap(copy);
    }

    public Color[][] getColorMap(double colorRange, double alpha) {
        int max = max();
        Color[][] colorMap = new Color[values.length][values[0].length];
        for (int y = 0; y < values.length; y++) {
            for (int x = 0; x < values[0].length; x++) {
                if (values[y][x] != Integer.MAX_VALUE) {
                    colorMap[y][x] = Color.hsb((double) values[y][x] * colorRange / max, 1.0, 1.0, alpha);
                } else {
                    colorMap[y][x] = Color.hsb(0.0, 0.0, 0.0, 0.0);
                }
            }
        }
        return colorMap;
    }

    public Direction next(Node point) {
        Direction next = null;
        if (point.getX() <= 0 || point.getY() <= 0
                || point.getX() >= values[0].length - 1
                || point.getY() >= values.length - 1) {
            return null;
        }
        int lowest = values[point.getY()][point.getX()];
        for (Direction candidate : Direction.values()) {
            Node temp = point.translateToNew(candidate);
            int value = values[temp.getY()][temp.getX()];
            if (value <= lowest) {
                lowest = value;
                next = candidate;
            }
        }
        return next;
    }

    public List<Direction> candidates(Node point) {
        List<Direction> directions = new ArrayList<>();
        HashMap<Direction, Node> nodes = new HashMap<>();
        int ux = point.getX();
        int uy = point.getY();
        point.setDistance(values[uy][ux]);
        for (Direction candidate : Direction.values()) {
            processCandidate(point, candidate, uy, ux, directions, nodes);
        }
        Collections.sort(directions, (Direction a, Direction b) -> {
            return nodes.get(a).compareTo(nodes.get(b));
        });
        return directions;
    }

    public void processCandidate(Node point, Direction candidate, int uy, int ux, List<Direction> directions, HashMap<Direction, Node> nodes) {
        Node candidateNode = point.translateToNew(candidate);
        candidateNode.setDistance(values[candidateNode.getY()][candidateNode.getX()]);
        int vx = candidateNode.getX();
        int vy = candidateNode.getY();
        if (values[vy][vx] != Integer.MAX_VALUE
                && values[vy][vx] <= values[uy][ux]) {
            directions.add(candidate);
            nodes.put(candidate, candidateNode);
        }
    }

    private int max() {
        int max = 0;
        for (int y = 0; y < values.length; y++) {
            for (int x = 0; x < values[0].length; x++) {
                if (values[y][x] != Integer.MAX_VALUE && values[y][x] > max) {
                    max = values[y][x];
                }
            }
        }
        return max;
    }

    public DijkstraMap invert() {
        double max = max();
        invertValues(max);
        iterate();
        return this;
    }

    private void invertValues(double max) {
        for (int y = 1; y < values.length - 1; y++) {
            for (int x = 1; x < values[0].length - 1; x++) {
                if (values[y][x] != Integer.MAX_VALUE) {
                    values[y][x] *= -1;
                    values[y][x] += max;
                    values[y][x] *= escapeBias;
                }
            }
        }
    }

    private void iterate() {
        boolean changed;
        do {
            changed = false;
            for (int y = 1; y < values.length - 1; y++) {
                for (int x = 1; x < values[0].length - 1; x++) {
                    if (values[y][x] != Integer.MAX_VALUE) {
                        changed = processCell(x, y, changed);
                    }
                }
            }
        } while (changed);
    }

    private boolean processCell(int x, int y, boolean changed) {
        int smallest = Integer.MAX_VALUE;
        Direction smallestNeighbor = null;
        for (Direction neighbor : Direction.values()) {
            int vx = x + neighbor.getDifferenceX();
            int vy = y + neighbor.getDifferenceY();
            if (values[vy][vx] != Integer.MAX_VALUE) {
                int newValue = values[vy][vx];
                if (newValue < smallest) {
                    smallest = newValue;
                    smallestNeighbor = neighbor;
                }
            }
        }
        changed = updateNeighbor(smallestNeighbor, smallest, y, x, changed);
        return changed;
    }

    private boolean updateNeighbor(Direction smallestNeighbor, int smallest, int y, int x, boolean changed) {
        if (smallestNeighbor != null) {
            int newValue = smallest + smallestNeighbor.cost();
            if (newValue < values[y][x]) {
                values[y][x] = newValue;
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (checkUnformatted()) {
            return "unformatted array";
        }
        for (int y = 0; y < values.length; y++) {
            for (int x = 0; x < values[0].length; x++) {
                appendCharacter(y, x, sb);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private void appendCharacter(int y, int x, StringBuilder sb) {
        if (values[y][x] != Integer.MAX_VALUE) {
            if (values[y][x] == 0) {
                sb.append(".");
            } else {
                sb.append((values[y][x] / 100) % 10);
            }
        } else {
            sb.append("#");
        }
    }

    private boolean checkUnformatted() {
        int max = max();
        if (max == 0) {
            return true;
        }
        return false;
    }
}
