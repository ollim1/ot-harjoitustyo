/*
 * @author olli m
 */
package dungeon.ui;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TitleScreen {

    private ViewManager viewManager;

    public TitleScreen(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public Scene createView(int resolutionX, int resolutionY) {
        Label titleText = new Label("dungeon");
        Label startText = new Label("start game");
        Label highScoresText = new Label("high scores");
        Label quitText = new Label("quit");
        BorderPane titleTextContainer = new BorderPane(titleText);
        BorderPane startButton = new BorderPane(startText);
        BorderPane highScoresButton = new BorderPane(highScoresText);
        BorderPane quitButton = new BorderPane(quitText);
        ArrayList<BorderPane> choiceButtons = new ArrayList<>();
        addPanesToList(choiceButtons, startButton, highScoresButton, quitButton);
        startButton.setOnMouseClicked(event -> {
            viewManager.runGame();
        });
        quitButton.setOnMouseClicked(event -> {
            Platform.exit();
        });

        setFormat(titleText);
        setChoiceButtonTextFormat(choiceButtons);

        VBox choices = new VBox();
        choices.getChildren().addAll(choiceButtons);

        BorderPane layout = new BorderPane(choices, titleTextContainer, null, null, null);

        Scene titleScreen = new Scene(layout, resolutionX, resolutionY, Color.GHOSTWHITE);
        return titleScreen;
    }

    private void setFormat(Label titleText) {
        titleText.setFont(new Font("Arial", 100));
        titleText.setContentDisplay(ContentDisplay.CENTER);
        titleText.setTextFill(Color.BLACK);
    }

    private void addPanesToList(ArrayList<BorderPane> list, BorderPane... panes) {
        for (BorderPane pane : panes) {
            list.add(pane);
        }
    }

    private void setChoiceButtonTextFormat(ArrayList<BorderPane> choiceButtons) {
        for (BorderPane button : choiceButtons) {
            button.setPadding(new Insets(16));
            Label label = (Label) button.getCenter();
            label.setFont(new Font("Arial", 67));
            label.setContentDisplay(ContentDisplay.CENTER);
            label.setTextFill(Color.BLACK);
            button.setOnMouseEntered(event -> {
                ((Label) button.getCenter()).setTextFill(Color.GRAY);
            });
            button.setOnMouseExited(event -> {
                ((Label) button.getCenter()).setTextFill(Color.BLACK);
            });
        }
    }
}
