/*
 * @author olli m
 */
package dungeon.domain;

import dungeon.backend.Game;
import dungeon.backend.MessageBus;

/**
 * An abstract superclass for game actors. Can be sorted by turn order.
 */
public abstract class Actor implements Comparable<Actor>, MapObject {

    private Node position;
    private double health;
    private double maxHealth;
    private double intervalModifier;
    private int nextTurn;
    private DijkstraMap dijkstraMap;
    private ActorType actorType;
    private boolean[] isHostile;
    private Attack attack;

    public Actor(Node position, double maxHealth, ActorType actorType) {
        this.position = position;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        intervalModifier = 1.0;
        nextTurn = 0;
    }

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

    /**
     * Increments the actor's turn value by the specified interval. The interval
     * can be scaled based on actor status. Every Actor is healed 0.01 points
     * per 100 time units.
     *
     * @param interval
     */
    public void incrementTurn(int interval) {
        this.nextTurn += interval * intervalModifier;
        heal(interval);
    }

    private void heal(int interval) {
        setHealth(Math.min(getHealth() + getMaxHealth() * interval / 100 * 0.01, getMaxHealth()));
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
            if (tryAttacking(direction, map, next, game)) {
                return true;
            }
            return false;
        }
    }

    private boolean tryAttacking(Direction direction, char[][] map, Node next, Game game) {
        if (isHostile[map[next.getY()][next.getX()]]) {
            Actor target = game.actorAt(next);
            if (attack != null) {
                if (target != null) {
                    attack.apply(game, this, target);
                    incrementTurn(attack.cost());
                    return true;
                }
                attackNothingMessage();
            }
        }
        return false;
    }

    /**
     * Pushes "x hit nothing" into the message queue.
     */
    private void attackNothingMessage() {
        String sourceString = "You";
        if (actorType != null) {
            sourceString = "The " + actorType.toString();
        }
        MessageBus.getInstance().push(new Message(sourceString + " hit nothing"));
    }

    /**
     * This method handles the Actor doing nothing for one turn.
     */
    public void idle() {
        incrementTurn(100);
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
        return this.nextTurn - that.nextTurn;
    }

    public ActorType getActorType() {
        return actorType;
    }

    public void setActorType(ActorType actorType) {
        this.actorType = actorType;
    }

    public void setDijkstraMap(DijkstraMap dijkstraMap) {
        this.dijkstraMap = dijkstraMap;
    }

    public DijkstraMap getDijkstraMap() {
        return dijkstraMap;
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

}
