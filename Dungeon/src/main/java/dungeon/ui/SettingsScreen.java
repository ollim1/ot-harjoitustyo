/*
 * @author olli m
 */
package dungeon.ui;

import dungeon.backend.Settings;
import dungeon.domain.Difficulty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

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
            return key.toString();
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

    }
    private final Settings settings;
    private final ViewManager viewManager;
    private final Difficulty[] difficulties;
    private final Tuple<String, Integer>[] mapSizes;

    public SettingsScreen(ViewManager viewManager, Settings settings) {
        this.settings = settings;
        this.difficulties = Difficulty.values();
        this.viewManager = viewManager;
        this.mapSizes = new Tuple[]{
            new Tuple("small map size", 40),
            new Tuple("medium map size", 100),
            new Tuple("large map size", 200)};
    }

    public Scene createView() {
        GridPane grid = new GridPane();
        for (int i = 0; i < mapSizes.length; i++) {
            final int ii = i;
            for (int j = 0; j < difficulties.length; j++) {
                Button button = new Button(difficulties[j] + ", " + mapSizes[i]);
                final int jj = j;
                button.setOnMouseClicked(event -> {
                    settings.setDifficulty(difficulties[jj]);
                    settings.setMapSize(mapSizes[ii].getValue());
                    viewManager.runGame(settings);
                });
                grid.add(button, ii, jj);
            }
        }
        return new Scene(grid, viewManager.getResolutionX(), viewManager.getResolutionY());
    }
}
