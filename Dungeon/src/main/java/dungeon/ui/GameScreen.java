/*
 * @author olli m
 */
package dungeon.ui;

import dungeon.backend.Game;
import dungeon.backend.HighScores;
import dungeon.backend.MessageBus;
import dungeon.backend.Plotter;
import dungeon.domain.Difficulty;
import dungeon.domain.Settings;
import dungeon.domain.MapObject;
import dungeon.domain.Message;
import dungeon.domain.Node;
import dungeon.domain.Player;
import dungeon.domain.PlayerAction;
import java.sql.SQLException;
import java.util.HashMap;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameScreen {

    private static final int LOGBOX_HEIGHT = 100;
    private static final int TILESIZE = 32;
    private int resolutionX;
    private int resolutionY;
    private boolean debug;
    private ViewManager viewManager;
    private TileMapper tileMapper;
    private Game game;
    private Scene screen;
    private Label healthMeter;
    private Label scoreText;
    private Canvas canvas;
    private TextArea logBox;
    private GraphicsContext graphicsContext;
    private Group screenRoot;
    private Label debugStats;
    private static final HashMap<KeyCode, PlayerAction> legalKeyCodes = new HashMap<KeyCode, PlayerAction>() {
        {

            put(KeyCode.Y, PlayerAction.NORTHWEST);
            put(KeyCode.U, PlayerAction.NORTHEAST);
            put(KeyCode.H, PlayerAction.WEST);
            put(KeyCode.J, PlayerAction.SOUTH);
            put(KeyCode.K, PlayerAction.NORTH);
            put(KeyCode.L, PlayerAction.EAST);
            put(KeyCode.B, PlayerAction.SOUTHWEST);
            put(KeyCode.N, PlayerAction.SOUTHEAST);
            put(KeyCode.PERIOD, PlayerAction.STAY);
        }
    };

    public GameScreen(ViewManager viewManager, Settings settings, int resolutionX, int resolutionY) {
        this.viewManager = viewManager;
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;

        this.debug = settings.isDebug();
        this.game = new Game(settings);
        game.setMonstersToCreate(5);
        game.initializeMapObjects(settings.getMapSize(), settings.getMapSize());
        game.createPlayer();
        game.spawnMonsters();

        this.healthMeter = new Label(" 40/ 40");
        this.healthMeter.setTextFill(Color.GREEN);
        this.scoreText = new Label("0");
        this.scoreText.setTextFill(Color.WHITE);
        setStatusFonts(healthMeter, scoreText);
        VBox stats = new VBox(healthMeter, scoreText);
        stats.setAlignment(Pos.BASELINE_RIGHT);
        stats.setMinWidth(100);

        this.canvas = new Canvas(resolutionX, resolutionY - LOGBOX_HEIGHT);
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.tileMapper = new TileMapper(
                "file:resources/tileset.png", TILESIZE, graphicsContext, resolutionX, resolutionY - LOGBOX_HEIGHT);
        this.screenRoot = new Group();
        this.logBox = new TextArea();
        this.logBox.setMinSize(resolutionX, LOGBOX_HEIGHT);
        this.logBox.setMaxSize(resolutionX, LOGBOX_HEIGHT);
        this.logBox.layout();
        this.logBox.setLayoutY(resolutionY - LOGBOX_HEIGHT);
        this.logBox.setEditable(false);
        this.logBox.setFocusTraversable(false);
        this.logBox.setMouseTransparent(true);
        screenRoot.getChildren().add(canvas);
        screenRoot.getChildren().add(logBox);
        if (debug) {
            this.debugStats = new Label();
            debugStats.layout();
            this.debugStats.setLayoutY(resolutionY - LOGBOX_HEIGHT);
            this.debugStats.setLayoutX(resolutionX * 2 / 3);
            this.logBox.setMinSize(resolutionX * 2 / 3, LOGBOX_HEIGHT);
            this.logBox.setMaxSize(resolutionX * 2 / 3, LOGBOX_HEIGHT);
            screenRoot.getChildren().add(debugStats);
        }

        screenRoot.getChildren().add(stats);
        this.screen = new Scene(screenRoot);
        setInputEvents();
    }

    private void setStatusFonts(Label... labelList) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(0.0);
        dropShadow.setOffsetY(0.0);
        dropShadow.setRadius(5.0);
        for (Label label : labelList) {
            label.setEffect(dropShadow);
            label.setFont(new Font("Monospace", 20));
        }
    }

    private void setInputEvents() {
        screen.setOnKeyPressed(event -> {
            if (legalKeyCodes.containsKey(event.getCode())) {
                game.insertAction(legalKeyCodes.get(event.getCode()));
                if (game.isGameOver()) {
                    gameOver();
                }
                if (debug) {
                    updateDebug();
                } else {
                    update();
                }
            }
        });
    }

    public Scene createView() {
        return screen;
    }

    public void update() {
        Plotter plotter = game.getPlotter();
        plotter.update();
        double[][] losMap = plotter.getVisibility();
        char[][] levelMap = plotter.getPlayerLevelMap();
        MapObject[][] objectMap = plotter.getPlayerObjectMap();

        Player player = game.getPlayer();
        Node position = player.getPosition();
        tileMapper.drawFrame(levelMap, objectMap, losMap, position.getX(), position.getY());
        updateHealthMeter(player);
        scoreText.setText(Integer.toString(game.getScore()));
        flushMessages();
    }

    public void updateDebug() {
        game.getPlotter().update();
        double[][] losMap = game.getPlotter().getVisibility();
        char[][] levelMap = game.getPlotter().getPlayerLevelMap();
        MapObject[][] objectMap = game.getPlotter().getPlayerObjectMap();

        Player player = game.getPlayer();
        Node position = player.getPosition();
        game.getPathFinder().computePaths(game.getMap(), position.getX(), position.getY());
        Color[][] colorMap = game.getPathFinder().getDijkstraMap().getColorMap(600, 0.5);
        tileMapper.drawDebugFrame(levelMap, objectMap, losMap, colorMap, position.getX(), position.getY());
        updateHealthMeter(player);
        scoreText.setText(Integer.toString(game.getScore()));
        flushMessages();
        debugStats.setText("debug\n" + "turn value: " + game.getPlayer().getNextTurn());
    }

    private void updateHealthMeter(Player player) {
        if (player.getHealth() < player.getMaxHealth() * 0.3) {
            healthMeter.setTextFill(Color.RED);
        } else if (player.getHealth() < player.getMaxHealth() * 0.5) {
            healthMeter.setTextFill(Color.YELLOW);
        } else {
            healthMeter.setTextFill(Color.GREEN);
        }
        healthMeter.setText(String.format("%3.0f/%3.0f", player.getHealth(), player.getMaxHealth()));
    }

    private void gameOver() {
        screen.setOnKeyPressed(event -> {
            return;
        });
        Label gameOverText = new Label("you died");
        gameOverText.setTextFill(Color.RED);
        gameOverText.setFont(new Font("Monospace", resolutionX / 20 * 1.5));
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(0.0);
        dropShadow.setOffsetY(0.0);
        dropShadow.setRadius(5.0);
        gameOverText.setEffect(dropShadow);
        VBox gameOverTextBox = new VBox(gameOverText);
        gameOverTextBox.setMinHeight(resolutionY - LOGBOX_HEIGHT);
        gameOverTextBox.setMinWidth(resolutionX);
        screenRoot.getChildren().add(gameOverTextBox);
        gameOverTextBox.setAlignment(Pos.CENTER);
        flushMessages();
        if (!debug) {
            checkHighScore();
        }
        screen.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                viewManager.showTitleScreen();
            }
        });
        screen.setOnMouseClicked(event -> viewManager.showTitleScreen());
    }

    private void checkHighScore() {
        HighScores highScores;
        try {
            highScores = new HighScores();
            int score = game.getScore();
            Difficulty difficulty = game.getDifficulty();
            if (highScores.isHighScore(score, difficulty)) {
                String name = getPlayerName(highScores.getCHARLIMIT());
                if (name != null && !name.isEmpty()) {
                    highScores.addHighScore(name, score, difficulty);
                    viewManager.showHighScoresScreen();
                }
            }
        } catch (SQLException e) {
            MessageBus.getInstance().push("Could not contact high scores database");
            flushMessages();
            return;
        }
    }

    private String getPlayerName(int limit) {
        VBox layout = new VBox();
        layout.setMinWidth(100);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setSpacing(20);
        Label label = new Label("a new high score\ninsert name here");
        Label help = new Label("character limit " + limit + ", press return to finish");
        TextField nameField = new TextField();
        layout.getChildren().add(label);
        layout.getChildren().add(nameField);
        layout.getChildren().add(help);
        Scene scene = new Scene(layout);
        Stage stage = new Stage();
        stage.setScene(scene);
        nameField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                stage.close();
            }
        });
        stage.setTitle("high score");
        stage.showAndWait();
        String name = nameField.getText();
        if (name != null) {
            name = name.trim();
            if (name.length() > limit) {
                name = name.substring(limit);
            }
        }
        return nameField.getText();
    }

    private void flushMessages() {
        Message message;
        while ((message = MessageBus.getInstance().poll()) != null) {
            logBox.appendText("\n" + message);
        }
    }
}
