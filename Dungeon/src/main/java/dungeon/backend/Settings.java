/*
 * @author olli m
 */
package dungeon.backend;

import dungeon.domain.Difficulty;

/**
 * This class stores game settings.
 *
 * @author londes
 */
public class Settings {

    private Difficulty difficulty;
    private int mapSize;

    public Settings() {
        difficulty = Difficulty.NORMAL;
        mapSize = 100;
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

}
