/*
 * @author olli m
 */
package dungeon.ui;

import dungeon.backend.Game;
import dungeon.domain.Monster;
import dungeon.domain.Player;
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
    private int resolutionX;
    private int resolutionY;

    public GameScreen(Game game, int resolutionX, int resolutionY) {
        this.game = game;
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;
        this.healthMeter = new Label(" 20/ 20");
        healthMeter.setTextFill(Color.GREEN);
        healthMeter.setFont(new Font("Monospace", 24));
        this.canvas = new Canvas(resolutionX, resolutionY);
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.tileMapper = new TileMapper(
                "file:resources/tileset.png", 32, graphicsContext, resolutionX, resolutionY);
        Group screenRoot = new Group();
        screenRoot.getChildren().add(canvas);

        screenRoot.getChildren().add(healthMeter);
        this.screen = new Scene(screenRoot);
        game.createPlayer();
        game.createMonster();

        setKeyboardActions(screenRoot);
    }

    private void setKeyboardActions(Group root) {
        screen.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            Player player = game.getPlayer();
            player.setAction(keyCode);
            player.incrementTurn(player.act(game, game.populateMap(player)));
            game.playRound();
            update();
        });
    }

    public Scene getScreen() {
        return screen;
    }

    public void update() {
        char[][] map = game.populateMap(game.getPlayer());
        Player player = game.getPlayer();
        tileMapper.drawFrame(map, player.getPosition().getX(), player.getPosition().getY());
        healthMeter.setText(String.format("%3d/%3d", player.getHealth(), player.getMaxHealth()));
    }
}
