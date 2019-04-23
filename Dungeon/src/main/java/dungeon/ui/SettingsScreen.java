/*
 * @author olli m
 */
package dungeon.ui;

import dungeon.backend.Settings;
import dungeon.domain.Difficulty;
import java.util.Set;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SettingsScreen {

    private static class Tuple<K, V> {

        private K key;
        private V value;

        public Tuple(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return value.toString();
        }

    }
    private Settings settings;
    private Stage window;

    public SettingsScreen(Settings settings, Stage window) {
        this.settings = settings;
        this.window = window;
    }

    public void draw() {
        GridPane grid = new GridPane();
        Difficulty[] difficulties = Difficulty.values();
        Tuple[] mapSizes = new Tuple[]{
            new Tuple(40, "small map size"),
            new Tuple(100, "medium map size"),
            new Tuple(200, "large map size")};
        for (int i = 0; i < difficulties.length; i++) {

        }
    }
}
