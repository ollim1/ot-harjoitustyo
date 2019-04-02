/*
 * @author olli m
 */
package dungeon.ui;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TileMapper {

    private Image tileSet;
    private int dimension;
    private GraphicsContext renderer;
    private int resolutionX;
    private int resolutionY;

    public TileMapper(String filename, int dimension, GraphicsContext renderer, int resolutionX, int resolutionY) {
        this.dimension = dimension;
        this.renderer = renderer;
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;

        this.tileSet = new Image(filename);
    }

    public void drawFrame(char[][] map, int centerX, int centerY) {
        int gridWidth = resolutionX / dimension + 1;
        int gridHeight = resolutionY / dimension + 1;
        int offsetX = centerX - resolutionX / dimension / 2;
        int offsetY = centerY - resolutionY / dimension / 2;

        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                int tilePositionX = offsetX + x;
                int tilePositionY = offsetY + y;
                int tileValue = 2;

                if (tilePositionX >= 0 && tilePositionX < map[0].length
                        && tilePositionY >= 0 && tilePositionY < map.length) {
                    if (map[tilePositionY][tilePositionX] == '@') {
                        tileValue = 0;
                    } else if (map[tilePositionY][tilePositionX] == 'D') {
                        tileValue = 1;
                    } else if (map[tilePositionY][tilePositionX] == ' ') {
                        tileValue = 4;
                    }
                }
                drawTile(tileValue, x, y);
            }
        }
    }

    private void drawTile(int tileValue, int x, int y) {
        renderer.drawImage(tileSet,
                tileValue * dimension, 0,
                dimension, dimension,
                x * dimension - dimension / 2, y * dimension - dimension / 2,
                dimension, dimension);
    }
}
