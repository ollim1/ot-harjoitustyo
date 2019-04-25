/*
 * @author olli m
 */
package dungeon.backend;

import dungeon.domain.DijkstraMap;
import dungeon.domain.Direction;
import dungeon.domain.Node;
import java.util.PriorityQueue;

public class PathFinder {

    /**
     * Using Dijkstra's algorithm to get every path at once. The resulting set
     * of paths is stored as a two-dimensional array of Direction objects that
     * represents a directed acyclic graph flowing towards the destination. The
     * class also stores the destination of the last graph.
     */
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

    public void computePaths(char[][] map, int x, int y) {
        visited = new boolean[map.length][map[0].length];
        distance = new int[map.length][map[0].length];
        this.map = map;
        oldCenter = new Node(x, y, 0);

        formatDistances(x, y);
        PriorityQueue<Node> heap = setUpHeap(x, y);
        dijkstra(x, y, heap);
    }

    public DijkstraMap dijkstraMap() {
        if (distance == null) {
            return null;
        }
        int[][] copy = new int[distance.length][distance[0].length];
        for (int y = 0; y < copy.length; y++) {
            for (int x = 0; x < copy[0].length; x++) {
                copy[y][x] = distance[y][x];
            }
        }
        return new DijkstraMap(distance);
    }

    public int[][] getDistances() {
        return distance;
    }

    public Node getOldCenter() {
        return oldCenter;
    }

    private void dijkstra(int startX, int startY, PriorityQueue<Node> heap) {
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
                        if (distance[vy][vx] == Integer.MAX_VALUE || distance[vy][vx] > newDistance) {
                            newPathFound(vy, vx, newDistance, heap);
                        }
                    }
                }
            }
        }
    }

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
