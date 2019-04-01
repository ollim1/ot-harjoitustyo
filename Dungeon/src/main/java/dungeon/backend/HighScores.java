/*
 * @author olli m
 */

package dungeon.backend;

import java.util.TreeMap;

public class HighScores {

    private TreeMap<Integer, String> table;

    public HighScores() {
        table = new TreeMap<>();
        table.put(100, "bar");
        table.put(200, "foo");
    }

    public TreeMap<Integer, String> getTable() {
        return table;
    }
}
