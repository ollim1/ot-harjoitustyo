/*
 * @author olli m
 */
package dungeon.backend;

import dungeon.domain.DijkstraMap;
import dungeon.domain.Direction;
import dungeon.domain.Node;
import java.util.PriorityQueue;

/**
 * A pathfinder using Dijkstra's algorithm. The algorithm calculates every
 * shortest path from a specific point. The resulting set of paths is stored as
 * a two-dimensional array of distance values called a Dijkstra map. Distances
 * are stored in the form of distance on a Cartesian plane multiplied by 100.
 * The class also stores the destination of the last graph created.
 *
 * @author londes
 */
public class PathFinder {

    private boolean[][] visited;
    private char[][] map;
    private int[][] distance;
    private Node oldCenter;
    private int totalCells;

    /**
     * Populates the Direction array representing the shortest paths from any
     * point in the map to the destination.
     */
    public PathFinder() {
        this.oldCenter = new Node(-1, -1);
    }

    /**
     * Computes the shortest distances starting from node (x, y). Will add
     * support for multiple focal nodes later, which should make it possible to
     * prioritize specific nodes.
     *
     * @param map
     * @param x
     * @param y
     */
    public void computePaths(char[][] map, int x, int y) {
        visited = new boolean[map.length][map[0].length];
        distance = new int[map.length][map[0].length];
        this.map = map;
        oldCenter = new Node(x, y, 0);

        formatDistances(x, y);
        PriorityQueue<Node> heap = setUpHeap(x, y);
        dijkstra(heap);
    }

    /**
     * Returns a new DijkstraMap with copy of the distance map.
     *
     * @return
     */
    public DijkstraMap getDijkstraMap() {
        if (distance == null) {
            return null;
        }
        int[][] copy = new int[distance.length][distance[0].length];
        for (int y = 0; y < copy.length; y++) {
            System.arraycopy(distance[y], 0, copy[y], 0, copy[0].length);
        }
        return new DijkstraMap(distance);
    }

    public int[][] getDistances() {
        return distance;
    }

    public Node getOldCenter() {
        return oldCenter;
    }

    private void dijkstra(PriorityQueue<Node> heap) {
        while (!heap.isEmpty()) {
            Node next = heap.poll();
            int ux = next.getX();
            int uy = next.getY();
            if (map[uy][ux] == ' ' && !visited[uy][ux]) {
                visited[uy][ux] = true;
                for (Direction neighbour : Direction.values()) {
                    int vx = ux + neighbour.getDifferenceX();
                    int vy = uy + neighbour.getDifferenceY();
                    if (map[vy][vx] == ' ') {
                        int newDistance = distance[uy][ux] + neighbour.cost();
                        if (distance[vy][vx] > newDistance) {
                            newPathFound(vy, vx, newDistance, heap);
                        }
                    }
                }
            }
        }
    }

    /**
     * Counts the amount of free nodes in a provided map.
     *
     * @param map
     * @param radius
     * @return
     */
    public int mapSize(char[][] map, double radius) {
        visited = new boolean[map.length][map[0].length];
        this.map = map;
        totalCells = 0;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x] == ' ') {
                    totalCells++;
                }
            }
        }
        return totalCells - (int) (Math.PI * radius * radius + 1);
    }

    private void newPathFound(int vy, int vx, int newDistance, PriorityQueue<Node> heap) {
        distance[vy][vx] = newDistance;
        heap.add(new Node(vx, vy, newDistance));
    }

    private PriorityQueue<Node> setUpHeap(int startX, int startY) {
        PriorityQueue<Node> heap = new PriorityQueue();
        heap.add(new Node(startX, startY, 0));
        return heap;
    }

    private void formatDistances(int startX, int startY) {
        for (int y = 0; y < distance.length; y++) {
            for (int x = 0; x < distance[0].length; x++) {
                distance[y][x] = Integer.MAX_VALUE;
            }
        }
        distance[startY][startX] = 0;
    }
}
