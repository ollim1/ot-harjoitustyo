/*
 * @author olli m
 */
package dungeon.ui;

import dungeon.backend.HighScores;
import java.sql.SQLException;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class HighScoresScreen {

    private HighScores highScores;
    private ViewManager viewManager;

    public HighScoresScreen(ViewManager viewManager) {
        this.viewManager = viewManager;
        try {
            highScores = new HighScores();
        } catch (SQLException e) {
        }
    }

    public Scene createListView() {
        Label title = new Label("high scores");
        title.setFont(new Font("monospace", 30));
        Label easyLabel = new Label("easy");
        Label normalLabel = new Label("normal");
        Label hardLabel = new Label("hard");
        setFont(new Font("monospace", 20), easyLabel, normalLabel, hardLabel);
        Label easyTable = new Label("");
        Label normalTable = new Label("");
        Label hardTable = new Label("");
        setFont(new Font("monospace", 14), easyTable, normalTable, hardTable);

        GridPane layout = new GridPane();
        Pane coverPane = new Pane();
        coverPane.setBackground(Background.EMPTY);
        coverPane.setMinWidth(viewManager.getResolutionX());
        coverPane.setMinHeight(viewManager.getResolutionY());
        coverPane.setOnMouseClicked(event -> viewManager.showTitleScreen());

        Pane container = new Pane();
        container.setBackground(Background.EMPTY);
        container.setMinWidth(viewManager.getResolutionX());
        container.setMinHeight(viewManager.getResolutionY());
        container.getChildren().add(layout);
        container.getChildren().add(coverPane);
        Scene scene = new Scene(container, viewManager.getResolutionX(), viewManager.getResolutionY(), Color.GHOSTWHITE);
        layout.add(title, 1, 0);
        if (highScores == null) {
            Label errorText = new Label("could not load database");
            errorText.setFont(new Font("monospace", 20));
            layout.add(errorText, 1, 1);
            layout.add(new Label(""), 2, 5);
        } else {
            layout.add(easyLabel, 0, 1);
            layout.add(normalLabel, 1, 1);
            layout.add(hardLabel, 2, 1);
            layout.add(easyTable, 0, 2, 1, 4);
            layout.add(normalTable, 1, 2, 1, 4);
            layout.add(hardTable, 2, 2, 1, 4);
        }
        return scene;
    }

    private void setFont(Font font, Label... labelList) {
        for (Label label : labelList) {
            label.setFont(font);
        }
    }
}
