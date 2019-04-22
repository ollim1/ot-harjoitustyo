/*
 * @author olli m
 */
package dungeon.domain;

import dungeon.backend.Game;

/**
 * An abstract superclass for game actors. Can be sorted by turn order.
 */
public abstract class Actor implements Comparable<Actor> {

    private Node position;
    private double health;
    private double maxHealth;
    private double intervalModifier;
    private int nextTurn;
    private DijkstraMap dijkstraMap;
    private boolean[] isHostile;
    private Attack attack;

    /**
     * This method commits character actions as determined by AI or player input
     * and game or character state.
     *
     * @param game
     * @param map
     */
    public abstract void act(Game game, char[][] map);

    public abstract char getSymbol();

    /**
     * Subtracts the specified amount of health from the actor's health points.
     *
     * @param amount
     * @return
     */
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

    public void setDijkstraMap(DijkstraMap dijkstraMap) {
        this.dijkstraMap = dijkstraMap;
    }

    public DijkstraMap getDijkstraMap() {
        return dijkstraMap;
    }

    public void setIntervalModifier(double intervalModifier) {
        this.intervalModifier = intervalModifier;
    }

    public double getIntervalModifier() {
        return intervalModifier;
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

    /**
     * Increments the actor's turn value by the specified interval. The interval
     * can be scaled based on actor status.
     *
     * @param interval
     */
    public void incrementTurn(int interval) {
        this.nextTurn += interval * intervalModifier;
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

    /**
     * This method handles actor movement. If an actor hits an obstacle whose
     * symbol represents a hostile actor, it will try to attack.
     *
     * @param direction
     * @param game
     * @param map a map created by PopulatedMap
     * @return
     */
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
                    attack.apply(game, this, target);
                    incrementTurn(direction.cost());
                    return true;
                }
            }
            return false;
        }
    }

    public void idle() {
        incrementTurn(100);
    }

    public void heal() {
        setHealth(Math.min(getHealth() + getMaxHealth() * 0.01, getMaxHealth()));
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

    public double distanceTo(int x, int y) {
        int dx = x - position.getX();
        int dy = y - position.getY();
        return Math.sqrt(dx * dx + dy * dy);
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
