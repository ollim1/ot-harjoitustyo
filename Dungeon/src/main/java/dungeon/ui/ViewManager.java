/*
 * @author olli m
 */
package dungeon.ui;

import dungeon.backend.Game;
import dungeon.ui.GameScreen;
import dungeon.ui.TitleScreen;
import javafx.stage.Stage;

public class ViewManager {

    private int resolutionX;
    private int resolutionY;
    private int mapWidth;
    private int mapHeight;
    private TitleScreen titleScreen;
    private Stage window;

    public ViewManager(Stage window, int resolutionX, int resolutionY, int mapWidth, int mapHeight) {
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.window = window;
    }

    public void showTitleScreen() {
        this.titleScreen = new TitleScreen(this);
        this.window.setScene(titleScreen.createView(resolutionX, resolutionY));
    }

    public void runGame() throws IllegalArgumentException {
        Game game;
        game = new Game();
        game.initializeMapObjects(mapWidth, mapHeight);
        GameScreen gameScreen = new GameScreen(game, resolutionX, resolutionY);
        gameScreen.update();
        window.setScene(gameScreen.getScreen());
    }

}
