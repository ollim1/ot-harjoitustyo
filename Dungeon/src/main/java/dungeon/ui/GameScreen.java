/*
 * @author olli m
 */
package dungeon.ui;

import dungeon.backend.Game;
import dungeon.domain.Player;
import dungeon.domain.PlayerAction;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class GameScreen {

    private TileMapper tileMapper;
    private Game game;
    private Scene screen;
    private Label healthMeter;
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private int resolutionX;
    private int resolutionY;

    public GameScreen(Game game, int resolutionX, int resolutionY) {
        this.game = game;
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;
        this.healthMeter = new Label("20/20");
        healthMeter.setTextFill(Color.GREEN);
        this.canvas = new Canvas(resolutionX, resolutionY);
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.tileMapper = new TileMapper(
                "file:resources/tileset.png", 32, graphicsContext, resolutionX, resolutionY);
        Group screenRoot = new Group();
        screenRoot.getChildren().add(healthMeter);
        screenRoot.getChildren().add(canvas);
        this.screen = new Scene(screenRoot);

        setKeyboardActions();
    }

    private void setKeyboardActions() {
        screen.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            PlayerAction action;
            if (keyCode == KeyCode.H || keyCode == KeyCode.LEFT) {
                action = PlayerAction.WEST;
            } else if (keyCode == KeyCode.J || keyCode == KeyCode.DOWN) {
                action = PlayerAction.SOUTH;
            } else if (keyCode == KeyCode.K || keyCode == KeyCode.UP) {
                action = PlayerAction.NORTH;
            } else if (keyCode == KeyCode.L || keyCode == KeyCode.RIGHT) {
                action = PlayerAction.EAST;
            } else if (keyCode == KeyCode.PERIOD) {
                action = PlayerAction.STAY;
            } else {
                return;
            }

            game.getPlayer().setAction(action);
            game.tick();
            update();
        });
    }

    public Scene getScreen() {
        return screen;
    }

    public void update() {
        char[][] map = game.drawMap();
        Player player = game.getPlayer();
        tileMapper.drawFrame(map, player.getPositionX(), player.getPositionY());
    }
}
