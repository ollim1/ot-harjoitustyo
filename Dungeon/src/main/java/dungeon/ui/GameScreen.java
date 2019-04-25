/*
 * @author olli m
 */
package dungeon.ui;

import dungeon.backend.Game;
import dungeon.backend.MessageBus;
import dungeon.backend.Settings;
import dungeon.domain.MapObject;
import dungeon.domain.Message;
import dungeon.domain.Player;
import dungeon.domain.PlayerAction;
import java.util.HashMap;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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
        this.debug = settings.isDebug();
        this.game = new Game(settings);
        game.setMonstersToCreate(5);
        game.initializeMapObjects(settings.getMapSize(), settings.getMapSize());
        game.createPlayer();
        game.spawnMonsters();
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;
        this.healthMeter = new Label(" 40/ 40");
        this.healthMeter.setTextFill(Color.GREEN);
        this.healthMeter.setFont(new Font("Monospace", 24));
        this.canvas = new Canvas(resolutionX, resolutionY);
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

        screenRoot.getChildren().add(healthMeter);
        this.screen = new Scene(screenRoot);
        setInputEvents();
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

    public Scene getScreen() {
        return screen;
    }

    public void update() {
        game.getPlotter().update();
        double[][] losMap = game.getPlotter().getVisibility();
        char[][] levelMap = game.getPlotter().getPlayerLevelMap();
        MapObject[][] objectMap = game.getPlotter().getPlayerObjectMap();

        Player player = game.getPlayer();
        tileMapper.drawFrame(levelMap, objectMap, losMap, player.getPosition().getX(), player.getPosition().getY());
        updateHealthMeter(player);
        flushMessages();
    }

    public void updateDebug() {
        game.getPlotter().update();
        double[][] losMap = game.getPlotter().getVisibility();
        char[][] levelMap = game.getPlotter().getPlayerLevelMap();
        MapObject[][] objectMap = game.getPlotter().getPlayerObjectMap();

        Player player = game.getPlayer();
        game.getPathFinder().computePaths(game.getMap(), player.getPosition().getX(), player.getPosition().getY());
        Color[][] colorMap = game.getPathFinder().dijkstraMap().getColorMap(600, 0.5);
        tileMapper.drawDebugFrame(levelMap, objectMap, losMap, colorMap, player.getPosition().getX(), player.getPosition().getY());
        updateHealthMeter(player);
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
        Label gameOverText = new Label("you are dead");
        gameOverText.setTextFill(Color.RED);
        gameOverText.setFont(new Font("Monospace", 40));
        screenRoot.getChildren().add(gameOverText);
        gameOverText.layout();
        gameOverText.setLayoutX((resolutionX - 214) / 2);
        gameOverText.setLayoutY((resolutionY - 40) / 2);
    }

    private void flushMessages() {
        Message message;
        while ((message = MessageBus.getInstance().poll()) != null) {
            logBox.appendText("\n" + message);
        }
    }
}
