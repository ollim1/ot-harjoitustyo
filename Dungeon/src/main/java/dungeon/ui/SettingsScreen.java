/*
 * @author olli m
 */
package dungeon.ui;

import dungeon.backend.Settings;
import dungeon.domain.Difficulty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
    private static final Difficulty[] difficulties = new Difficulty[]{
        Difficulty.EASY, Difficulty.NORMAL, Difficulty.HARD};
    private static final Tuple<String, Integer>[] mapSizes = new Tuple[]{
        new Tuple("small map", 50),
        new Tuple("medium map", 100),
        new Tuple("large map", 200)};

    public SettingsScreen(ViewManager viewManager, Settings settings) {
        this.settings = settings;
        this.viewManager = viewManager;
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
        CheckBox debugCheckBox = new CheckBox("debug mode");
        debugCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                settings.setDebug(newValue);
            }
        }
        );
        grid.add(debugCheckBox, 0, difficulties.length);
        return new Scene(grid, viewManager.getResolutionX(), viewManager.getResolutionY());
    }
}
