/*
 * @author olli m
 */
package dungeon.ui;

import dungeon.backend.ViewManager;
import dungeon.backend.Game;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DungeonUi extends Application {

    private static final int RESOLUTION_X = 640;
    private static final int RESOLUTION_Y = 480;
    private static final int GAME_WIDTH = 40;
    private static final int GAME_HEIGHT = 40;

    @Override
    public void start(Stage window) {
        ViewManager viewManager = new ViewManager(window, RESOLUTION_X, RESOLUTION_Y, GAME_WIDTH, GAME_HEIGHT);

        TitleScreen titleScreen = new TitleScreen(viewManager);
        Scene titleScreenView = titleScreen.createView(RESOLUTION_X, RESOLUTION_Y);
        window.setScene(titleScreenView);
        window.setTitle("dungeon");
        window.show();
    }

    public static void main(String[] args) {
        launch(DungeonUi.class);
    }
}
