/*
 * @author olli m
 */
package dungeon.domain;

import dungeon.backend.Game;

public abstract class Actor implements Comparable<Actor> {

    private Node position;
    private double health;
    private double maxHealth;
    private int interval;
    private int nextTurn;
    private boolean[] isHostile;
    private Attack attack;

    public abstract void act(Game game, char[][] map);

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getInterval() {
        return interval;
    }

    public abstract char getSymbol();

    public double damage(double amount) {
        if (amount > health) {
            double originalHealth = health;
            health = 0;
            return originalHealth;
        } else {
            health -= amount;
            return amount;
        }
    }

    public void setAttack(Attack attack) {
        this.attack = attack;
    }

    public Attack getAttack() {
        return attack;
    }

    public void setNextTurn(int nextTurn) {
        this.nextTurn = nextTurn;
    }

    public void incrementTurn() {
        this.nextTurn += this.interval;
    }

    public void incrementTurn(int interval) {
        this.nextTurn += interval;
    }

    public int getNextTurn() {
        return nextTurn;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public Node getPosition() {
        return position;
    }

    public void setPosition(Node position) {
        this.position = position;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setHostileSymbols(boolean[] hostileSymbols) {
        this.isHostile = hostileSymbols;
    }

    public boolean move(Direction direction, Game game, char[][] map) {
        Node next = position.translateToNew(direction);
        if (isFree(map, next.getX(), next.getY())) {
            position.translate(direction);
            incrementTurn(direction.cost());
            return true;
        } else {
            if (isHostile[map[next.getY()][next.getX()]]) {
                Actor target = game.actorAt(next);
                if (attack != null && target != null) {
                    attack.apply(this, target);
                }
            }
        }
        incrementTurn(direction.cost());
        return false;
    }

    public void idle() {
        incrementTurn();
    }

    private boolean isFree(char[][] map, int x, int y) {
        if (x >= 0 || x < map[0].length
                || y >= 0 || y < map.length) {
            return map[y][x] == ' ';
        }
        return false;
    }

    public double distanceTo(Actor that) {
        return this.position.differenceTo(that.position);
    }

    @Override
    public int compareTo(Actor that) {
        if (that.nextTurn == this.nextTurn) {
            if (this.getClass() == Player.class) {
                return -1;
            } else if (that.getClass() == Player.class) {
                return 1;
            }
        }
        return this.nextTurn - that.nextTurn;
    }

}
