/*
 * @author olli m
 */
package dungeon.ui;

import dungeon.backend.Game;
import dungeon.domain.Settings;
import dungeon.domain.Difficulty;
import javafx.stage.Stage;

public class ViewManager {

    private int resolutionX;
    private int resolutionY;
    private TitleScreen titleScreen;
    private Stage window;

    public ViewManager(Stage window, int resolutionX, int resolutionY) {
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;
        this.window = window;
    }

    public void showTitleScreen() {
        this.titleScreen = new TitleScreen(this);
        this.window.setScene(titleScreen.createView(resolutionX, resolutionY));
    }

    public void runGame(Settings settings) throws IllegalArgumentException {
        GameScreen gameScreen = new GameScreen(this, settings, resolutionX, resolutionY);
        if (settings.isDebug()) {
            gameScreen.updateDebug();
        } else {
            gameScreen.update();
        }
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
