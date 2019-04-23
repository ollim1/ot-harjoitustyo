/*
 * @author olli m
 */
package dungeon.ui;

import dungeon.backend.Game;
import dungeon.backend.Settings;
import dungeon.domain.Difficulty;
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
        Game game = new Game();
        game.setMonstersToCreate(5);
        game.initializeMapObjects(mapWidth, mapHeight);
        GameScreen gameScreen = new GameScreen(game, resolutionX, resolutionY, false);
        gameScreen.update();
        window.setScene(gameScreen.getScreen());
    }

    public void runGame(Settings settings) throws IllegalArgumentException {
        Game game = new Game(settings.getDifficulty());
        game.setMonstersToCreate(5);
        game.initializeMapObjects(settings.getMapSize(), settings.getMapSize());
        GameScreen gameScreen = new GameScreen(game, resolutionX, resolutionY, false);
        gameScreen.update();
        window.setScene(gameScreen.getScreen());
    }

    public void showSettingsScreen() {
        Settings settings = new Settings();
        SettingsScreen settingsScreen = new SettingsScreen(this, settings);
        this.window.setScene(settingsScreen.createView());
    }

    public int getResolutionX() {
        return resolutionX;
    }

    public int getResolutionY() {
        return resolutionY;
    }

}
