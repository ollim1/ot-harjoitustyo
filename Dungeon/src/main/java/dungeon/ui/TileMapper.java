/*
 * @author olli m
 */
package dungeon.ui;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class TileMapper {

    private Image tileSet;
    private int tileSize;
    private GraphicsContext renderer;
    private int resolutionX;
    private int resolutionY;

    public TileMapper(String filename, int tileSize, GraphicsContext renderer, int resolutionX, int resolutionY) {
        this.tileSize = tileSize;
        this.renderer = renderer;
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;
        renderer.setFill(Color.rgb(0, 0, 0, 0.5));

        this.tileSet = new Image(filename);
    }

    public void drawFrame(char[][] map, double[][] losMap, int centerX, int centerY) {
        int gridWidth = resolutionX / tileSize + 1;
        int gridHeight = resolutionY / tileSize + 1;
        int offsetX = centerX - resolutionX / tileSize / 2;
        int offsetY = centerY - resolutionY / tileSize / 2;

        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                int tilePositionX = offsetX + x;
                int tilePositionY = offsetY + y;
                boolean outOfBounds = true;
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
                    outOfBounds = false;
                }
                drawTile(tileValue, x, y);
                if (!outOfBounds && !(losMap[tilePositionX][tilePositionY] > 0.0)) {
                    shade(x, y);
                }
            }
        }
    }

    private void shade(int x, int y) {
        renderer.fillRect(x * tileSize - tileSize / 2, y * tileSize - tileSize / 2,
                tileSize, tileSize);
    }
    
    private void drawTile(int tileValue, int x, int y) {
        renderer.drawImage(tileSet,
                tileValue * tileSize, 0,
                tileSize, tileSize,
                x * tileSize - tileSize / 2, y * tileSize - tileSize / 2,
                tileSize, tileSize);
    }
}
