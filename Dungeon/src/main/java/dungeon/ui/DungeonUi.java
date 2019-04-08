/*
 * @author olli m
 */
package dungeon.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DungeonUi extends Application {

    private static final int RESOLUTION_X = 640;
    private static final int RESOLUTION_Y = 480;
    private static final int GAME_WIDTH = 40;
    private static final int GAME_HEIGHT = 40;

    @Override
    public void start(Stage window) {
        ViewManager viewManager = new ViewManager(window, RESOLUTION_X, RESOLUTION_Y, GAME_WIDTH, GAME_HEIGHT);
        viewManager.showTitleScreen();
        window.setTitle("dungeon");
        window.show();
    }

    public static void main(String[] args) {
        launch(DungeonUi.class);
    }
}
