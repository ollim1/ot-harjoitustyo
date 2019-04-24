/*
 * @author olli m
 */
package dungeon.ui;

import dungeon.domain.MapObject;
import java.util.HashMap;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class TileMapper {

    private Image tileSet;
    private int tileSize;
    private GraphicsContext renderer;
    private int resolutionX;
    private int resolutionY;
    private int gridWidth;
    private int gridHeight;
    private static final Color SHADE = Color.rgb(0, 0, 0, 0.5);
    private static final HashMap<Character, Integer> tileValues = new HashMap<Character, Integer>() {
        {
            put('_', 0);
            put('#', 1);
            put(' ', 2);
            put('@', 3);
            put('o', 4);
            put('d', 5);
            put('D', 6);
        }
    };

    public TileMapper(String filename, int tileSize, GraphicsContext renderer, int resolutionX, int resolutionY) {
        this.tileSize = tileSize;
        this.renderer = renderer;
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;
        this.gridWidth = resolutionX / tileSize + 1;
        this.gridHeight = resolutionY / tileSize + 1;

        this.tileSet = new Image(filename);
    }

    public void drawFrame(char[][] map, double[][] losMap, int centerX, int centerY) {
        int offsetX = centerX - resolutionX / tileSize / 2;
        int offsetY = centerY - resolutionY / tileSize / 2;

        drawGrid(offsetX, offsetY, map);
        drawGrid(offsetX, offsetY, losMap);
    }

    public void drawFrame(char[][] levelMap, MapObject[][] objectMap, double[][] losMap, int centerX, int centerY) {
        int offsetX = centerX - resolutionX / tileSize / 2;
        int offsetY = centerY - resolutionY / tileSize / 2;

        drawGrid(offsetX, offsetY, levelMap);
        drawGrid(offsetX, offsetY, objectMap);
        drawGrid(offsetX, offsetY, losMap);
    }

    public void drawDebugFrame(char[][] map, double[][] losMap, Color[][] colors, int centerX, int centerY) {
        drawFrame(map, losMap, centerX, centerY);
        drawColorMap(colors, centerX, centerY);
    }

    public void drawDebugFrame(char[][] levelMap, MapObject[][] objectMap, double[][] losMap, Color[][] colors, int centerX, int centerY) {
        drawFrame(levelMap, objectMap, losMap, centerX, centerY);
        drawColorMap(colors, centerX, centerY);
    }

    private void drawGrid(int offsetX, int offsetY, char[][] map) {
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                int tilePositionX = offsetX + x;
                int tilePositionY = offsetY + y;
                int tileValue = 0;

                if (tilePositionX >= 0 && tilePositionX < map[0].length
                        && tilePositionY >= 0 && tilePositionY < map.length) {
                    tileValue = tileValues.getOrDefault(map[tilePositionY][tilePositionX], 0);
                }
                drawTile(tileValue, x, y);
            }
        }
    }

    private void drawGrid(int offsetX, int offsetY, MapObject[][] objectMap) {
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                int tilePositionX = offsetX + x;
                int tilePositionY = offsetY + y;
                int tileValue = 0;

                if (tilePositionX >= 0 && tilePositionX < objectMap[0].length
                        && tilePositionY >= 0 && tilePositionY < objectMap.length
                        && objectMap[tilePositionY][tilePositionX] != null) {
                    tileValue = tileValues.getOrDefault(objectMap[tilePositionY][tilePositionX].getSymbol(), 0);
                }
                if (tileValue > 0) {
                    drawTile(tileValue, x, y);
                }
            }
        }
    }

    private void drawGrid(int offsetX, int offsetY, double[][] losMap) {
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                int tilePositionX = offsetX + x;
                int tilePositionY = offsetY + y;
                if (tilePositionX >= 0 && tilePositionX < losMap.length
                        && tilePositionY >= 0 && tilePositionY < losMap[0].length
                        && !(losMap[tilePositionX][tilePositionY] > 0.0)) {
                    shade(SHADE, x, y);
                }
            }
        }
    }

    private void drawColorMap(Color[][] colors, int centerX, int centerY) {
        int offsetX = centerX - resolutionX / tileSize / 2;
        int offsetY = centerY - resolutionY / tileSize / 2;
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                int tilePositionX = offsetX + x;
                int tilePositionY = offsetY + y;
                if (tilePositionX >= 0 && tilePositionX < colors[0].length
                        && tilePositionY >= 0 && tilePositionY < colors.length) {
                    shade(colors[tilePositionY][tilePositionX], x, y);
                }
            }
        }
    }

    private void shade(Color color, int x, int y) {
        renderer.setFill(color);
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
