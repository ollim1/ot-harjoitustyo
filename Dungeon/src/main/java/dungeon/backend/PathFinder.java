/*
 * @author olli m
 */
package dungeon.backend;

import dungeon.domain.Direction;
import dungeon.domain.Node;
import java.util.PriorityQueue;

public class PathFinder {

    // Using Dijkstra's algorithm to get every path at once
    // the resulting set of paths is stored as a two-dimensional array of Direction objects
    // that represents a directed acyclic graph towards the destination
    // should be possible to add diagonal movement by just modifying the Direction enumerator
    private boolean[][] visited;
    private char[][] map;
    private int[][] distance;
    private Direction[][] paths;
    private Node oldPlayerPosition;

    public void computePaths(char[][] map, int x, int y) {
        visited = new boolean[map.length][map[0].length];
        distance = new int[map.length][map[0].length];
        paths = new Direction[map.length][map[0].length];
        this.map = map;
        oldPlayerPosition = new Node(x, y, 0);
        dijkstra(x, y);
    }

    public Direction[][] getPaths() {
        return paths;
    }

    public Node getOldPlayerPosition() {
        return oldPlayerPosition;
    }

    private void dijkstra(int startX, int startY) {
        formatDistances(startY, startX);
        PriorityQueue<Node> heap = setUpHeap(startX, startY);
        while (!heap.isEmpty()) {
            Node next = heap.poll();
            int ux = next.getX();
            int uy = next.getY();
            if (map[uy][ux] == ' ' && !visited[uy][ux]) {
                visited[uy][ux] = true;
                for (Direction neighbour : Direction.values()) {
                    int vx = ux + neighbour.getDifferenceX();
                    int vy = uy + neighbour.getDifferenceY();
                    int newDistance = distance[uy][ux] + neighbour.cost();
                    if (distance[vy][vx] == Integer.MAX_VALUE || distance[vy][vx] > newDistance) {
                        newPathFound(vy, vx, newDistance, neighbour, heap);
                    }
                }
            }
        }
    }

    private void newPathFound(int vy, int vx, int newDistance, Direction neighbour, PriorityQueue<Node> heap) {
        distance[vy][vx] = newDistance;
        paths[vy][vx] = neighbour.opposite();
        heap.add(new Node(vx, vy, newDistance));
    }

    private PriorityQueue<Node> setUpHeap(int startX, int startY) {
        PriorityQueue<Node> heap = new PriorityQueue();
        heap.add(new Node(startX, startY, 0));
        return heap;
    }

    private void formatDistances(int startY, int startX) {
        for (int y = 0; y < distance.length; y++) {
            for (int x = 0; x < distance[0].length; x++) {
                distance[y][x] = Integer.MAX_VALUE;
            }
        }
        distance[startY][startX] = 0;
    }
}
