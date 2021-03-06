/*
 * @author olli m
 */
package dungeon.domain;

/**
 * A class for passing game settings between classes.
 *
 * @author londes
 */
public class Settings {

    private Difficulty difficulty;
    private int mapSize;
    private boolean debug;

    public Settings() {
        difficulty = Difficulty.NORMAL;
        mapSize = 100;
        debug = false;
    }

    public Settings(Difficulty difficulty, int mapSize, boolean debug) {
        this.difficulty = difficulty;
        this.mapSize = mapSize;
        this.debug = debug;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setMapSize(int mapSize) {
        this.mapSize = mapSize;
    }

    public int getMapSize() {
        return mapSize;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isDebug() {
        return debug;
    }

}
