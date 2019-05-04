/*
 * @author olli m
 */
package dungeon.ui;

import dungeon.backend.HighScores;
import dungeon.dao.domain.Record;
import dungeon.domain.Difficulty;
import java.sql.SQLException;
import java.util.List;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class HighScoresScreen {

    private HighScores highScores;
    private ViewManager viewManager;
    private Label[] easyNames;
    private Label[] easyScores;
    private Label[] normalNames;
    private Label[] normalScores;
    private Label[] hardNames;
    private Label[] hardScores;

    public HighScoresScreen(ViewManager viewManager) {
        this.viewManager = viewManager;
        try {
            highScores = new HighScores();
        } catch (SQLException e) {
        }
    }

    public Scene createListView() {
        Label title = new Label("high scores");
        title.setFont(new Font("monospace", 67));
        Label easyLabel = new Label("easy");
        Label normalLabel = new Label("normal");
        Label hardLabel = new Label("hard");
        setLabelStyle(new Font("monospace", 30), easyLabel, normalLabel, hardLabel);

        easyNames = new Label[10];
        normalNames = new Label[10];
        hardNames = new Label[10];
        easyScores = new Label[10];
        normalScores = new Label[10];
        hardScores = new Label[10];
        fillTable(easyNames);
        fillTable(normalNames);
        fillTable(hardNames);
        fillTable(easyScores);
        fillTable(normalScores);
        fillTable(hardScores);

        HBox scoreTable = new HBox();
        scoreTable.setMinWidth(viewManager.getResolutionX());
        scoreTable.setSpacing(10);
        scoreTable.setPadding(new Insets(10, 10, 10, 10));
        VBox easyTable = new VBox();
        VBox normalTable = new VBox();
        VBox hardTable = new VBox();
        setup(easyTable, normalTable, hardTable);
        GridPane easyPane = new GridPane();
        GridPane normalPane = new GridPane();
        GridPane hardPane = new GridPane();
        easyTable.getChildren().add(easyLabel);
        normalTable.getChildren().add(normalLabel);
        hardTable.getChildren().add(hardLabel);
        easyTable.getChildren().add(easyPane);
        normalTable.getChildren().add(normalPane);
        hardTable.getChildren().add(hardPane);
        scoreTable.getChildren().add(easyTable);
        scoreTable.getChildren().add(normalTable);
        scoreTable.getChildren().add(hardTable);
        VBox layout = new VBox();
        layout.setBackground(Background.EMPTY);
        layout.setMinWidth(viewManager.getResolutionX());
        layout.setMinHeight(viewManager.getResolutionY());
        layout.setAlignment(Pos.TOP_CENTER);
        layout.getChildren().add(title);
        layout.getChildren().add(scoreTable);

        Scene scene = new Scene(layout, viewManager.getResolutionX(), viewManager.getResolutionY(), Color.GHOSTWHITE);
        if (highScores == null) {
            Label errorText = new Label("could not load database");
            errorText.setFont(new Font("monospace", 24));
            layout.getChildren().add(errorText);
        } else {
            for (int i = 0; i < easyNames.length; i++) {
                easyPane.add(easyNames[i], 0, i + 1);
            }
            for (int i = 0; i < easyScores.length; i++) {
                easyPane.add(easyScores[i], 1, i + 1);
            }
            for (int i = 0; i < normalNames.length; i++) {
                normalPane.add(normalNames[i], 0, i + 1);
            }
            for (int i = 0; i < normalScores.length; i++) {
                normalPane.add(normalScores[i], 1, i + 1);
            }
            for (int i = 0; i < hardNames.length; i++) {
                hardPane.add(hardNames[i], 0, i + 1);
            }
            for (int i = 0; i < hardScores.length; i++) {
                hardPane.add(hardScores[i], 1, i + 1);
            }
            List<Record> easyList = highScores.getTable(Difficulty.EASY);
            for (int i = 0; i < easyList.size(); i++) {
                Record record = easyList.get(i);
                easyNames[i].setText(record.getPerson().toString());
                easyScores[i].setText(Integer.toString(record.getScore()));
            }
            List<Record> normalList = highScores.getTable(Difficulty.NORMAL);
            for (int i = 0; i < normalList.size(); i++) {
                Record record = normalList.get(i);
                normalNames[i].setText(record.getPerson().toString());
                normalScores[i].setText(Integer.toString(record.getScore()));
            }
            List<Record> hardList = highScores.getTable(Difficulty.HARD);
            for (int i = 0; i < hardList.size(); i++) {
                Record record = hardList.get(i);
                hardNames[i].setText(record.getPerson().toString());
                hardScores[i].setText(Integer.toString(record.getScore()));
            }
        }
        scene.setOnMouseClicked(event -> viewManager.quit());
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                viewManager.quit();
            }
        });
        return scene;
    }

    private void setup(VBox... list) {
        for (VBox vBox : list) {
            vBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 2; -fx-border-color: black;");
            vBox.setAlignment(Pos.CENTER);
        }
    }

    private void fillTable(Label[] labels) {
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new Label("");
            labels[i].setFont(new Font("monospace", 24));
            labels[i].setTextFill(Color.GRAY);
            labels[i].setMinWidth((viewManager.getResolutionX() - 45) / 6);
            labels[i].setAlignment(Pos.BASELINE_RIGHT);
        }
    }

    private void setLabelStyle(Font font, Label... labelList) {
        for (Label label : labelList) {
            label.setFont(font);
            label.setAlignment(Pos.CENTER);
//            label.setMinWidth(233);
            GridPane.setHalignment(label, HPos.CENTER);
        }
    }
}
