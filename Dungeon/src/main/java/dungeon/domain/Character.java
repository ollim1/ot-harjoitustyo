/*
 * @author olli m
 */
package dungeon.domain;

public abstract class Character {

    private int positionX;
    private int positionY;
    private int health;
    private int maxHealth;

    public abstract void act(char[][] map);

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getHealth() {
        return health;
    }

    public boolean move(Direction direction, char[][] map) {
        int newX = positionX + direction.getDifferenceX();
        int newY = positionY + direction.getDifferenceY();
        if (isFree(map, newX, newY)) {
            positionX = newX;
            positionY = newY;
            return true;
        }
        return false;
    }

    private boolean isFree(char[][] map, int x, int y) {
        if (x >= 0 || x < map[0].length
                || y >= 0 || y < map.length) {
            return map[y][x] == ' ';
        }
        return false;
    }
}
