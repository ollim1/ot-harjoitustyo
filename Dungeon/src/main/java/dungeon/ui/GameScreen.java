/*
 * @author olli m
 */
package dungeon.ui;

import dungeon.backend.Game;
import dungeon.domain.Player;
import dungeon.domain.PlayerAction;
import java.util.HashMap;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameScreen {

    private TileMapper tileMapper;
    private Game game;
    private Scene screen;
    private Label healthMeter;
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Group screenRoot;
    private int resolutionX;
    private int resolutionY;
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

    public GameScreen(Game game, int resolutionX, int resolutionY) {
        this.game = game;
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;
        this.healthMeter = new Label(" 40/ 40");
        this.healthMeter.setTextFill(Color.GREEN);
        this.healthMeter.setFont(new Font("Monospace", 24));
        this.canvas = new Canvas(resolutionX, resolutionY);
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.tileMapper = new TileMapper(
                "file:resources/tileset.png", 32, graphicsContext, resolutionX, resolutionY);
        this.screenRoot = new Group();
        screenRoot.getChildren().add(canvas);

        screenRoot.getChildren().add(healthMeter);
        this.screen = new Scene(screenRoot);
        game.createPlayer();
        game.createMonsters();
        setInputEvents();
    }

    private void setInputEvents() {
        screen.setOnKeyPressed(event -> {
            if (legalKeyCodes.containsKey(event.getCode())) {
                game.insertAction(legalKeyCodes.get(event.getCode()));
                if (game.isGameOver()) {
                    gameOver();
                }
                update();
            }
        });
    }

    public Scene getScreen() {
        return screen;
    }

    public void update() {
        game.getPlotter().update();
        char[][] map = game.getPlotter().getPlayerMap();
        double[][] losMap = game.getPlotter().getVisibility();

        Player player = game.getPlayer();
        tileMapper.drawFrame(map, losMap, player.getPosition().getX(), player.getPosition().getY());
        if (player.getHealth() < player.getMaxHealth() * 0.3) {
            healthMeter.setTextFill(Color.RED);
        } else if (player.getHealth() < player.getMaxHealth() * 0.5) {
            healthMeter.setTextFill(Color.YELLOW);
        } else {
            healthMeter.setTextFill(Color.GREEN);
        }
        healthMeter.setText(String.format("%3.0f/%3.0f", player.getHealth(), player.getMaxHealth()));
    }

    public void updateDebug() {
        game.getPlotter().update();
        char[][] map = game.getPlotter().getPlayerMap();
        double[][] losMap = game.getPlotter().getVisibility();

        Player player = game.getPlayer();
        game.getPathFinder().computePaths(game.getMap(), player.getPosition().getX(), player.getPosition().getY());
        Color[][] colorMap = game.getPathFinder().dijkstraMap().getColorMap(600, 0.5);
        tileMapper.drawDebugFrame(map, losMap, colorMap, player.getPosition().getX(), player.getPosition().getY());
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
}
