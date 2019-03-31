/*
 * @author olli m
 */
package dungeon.ui;

import dungeon.backend.GameLogic;
import dungeon.backend.HighScores;
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

    private static final int SCREEN_WIDTH = 640;
    private static final int SCREEN_HEIGHT = 480;

    @Override
    public void start(Stage window) {
        Scene titleScreen = createTitleScreen(null, null);
        window.setScene(titleScreen);
        window.setTitle("dungeon");
        window.show();
    }

    private Scene createTitleScreen(GameLogic gameLogic, HighScores highScores) {
   
        Label titleText = new Label("dungeon");
        Label startText = new Label("start game");
        Label highScoresText = new Label("high scores");
        Label quitText = new Label("quit");
        BorderPane titleTextContainer = new BorderPane(titleText);
        BorderPane startButton = new BorderPane(startText);
        BorderPane highScoresButton = new BorderPane(highScoresText);
        BorderPane quitButton = new BorderPane(quitText);
        ArrayList<BorderPane> choiceButtons = new ArrayList<>();
        choiceButtons.add(startButton);
        choiceButtons.add(highScoresButton);
        choiceButtons.add(quitButton);

        titleText.setFont(new Font("Arial", 100));
        titleText.setContentDisplay(ContentDisplay.CENTER);
        titleText.setTextFill(Color.BLACK);
        for (BorderPane button : choiceButtons) {
            button.setPadding(new Insets(16));
            Label label = (Label) button.getCenter();
            label.setFont(new Font("Arial", 67));
            label.setContentDisplay(ContentDisplay.CENTER);
            label.setTextFill(Color.BLACK);
            button.setOnMouseEntered(e -> {
                ((Label)button.getCenter()).setTextFill(Color.GRAY);
            });
            button.setOnMouseExited(e -> {
                ((Label)button.getCenter()).setTextFill(Color.BLACK);
            });
        }

        VBox choices = new VBox();
        choices.getChildren().addAll(choiceButtons);

        BorderPane layout = new BorderPane(choices, titleTextContainer, null, null, null);
        
        Scene titleScreen = new Scene(layout, 640, 480, Color.GHOSTWHITE);
        return titleScreen;
    }

    public static void main(String[] args) {
        launch(DungeonUi.class);
    }
}
