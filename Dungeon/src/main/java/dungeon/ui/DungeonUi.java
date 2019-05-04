/*
 * @author olli m
 */
package dungeon.ui;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * A skeleton class that creates a ViewManager.
 *
 * @author londes
 */
public class DungeonUi extends Application {

    private static final int RESOLUTION_X = 800;
    private static final int RESOLUTION_Y = 700;

    @Override
    public void start(Stage window) {
        ViewManager viewManager = new ViewManager(window, RESOLUTION_X, RESOLUTION_Y);
        viewManager.showTitleScreen();
        window.setTitle("dungeon");
        window.show();
    }

    public static void main(String[] args) {
        launch(DungeonUi.class);
    }
}
