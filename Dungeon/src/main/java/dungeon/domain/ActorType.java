/*
 * @author olli m
 */
package dungeon.domain;

public enum ActorType {

    ORC(20, 0.8, 10, 1.5, 0.3, 0.8, 'o', 100, "orc", new Punch()),
    GNOLL(25, 0.8, 10, 1.5, 0.2, 0.8, 'd', 150, "gnoll", new Punch()),
    DRAGON(35, 0.9, 0.0, 2.0, 0.3, 0.9, 'D', 200, "dragon", new Bite());

    public final double maxHealth;
    public final double visionRatio;
    public final double alertRadius;
    public final double escapeBias;
    public final double fleeThreshold;
    public final double safeThreshold;
    public final char symbol;
    public final int points;
    public final String name;
    public final Attack attack;

    private ActorType(double maxHealth, double visionRatio, double alertRadius, double escapeBias, double fleeThreshold, double safeThreshold, char symbol, int points, String name, Attack attack) {
        this.maxHealth = maxHealth;
        this.visionRatio = visionRatio;
        this.alertRadius = alertRadius;
        this.escapeBias = escapeBias;
        this.fleeThreshold = fleeThreshold;
        this.safeThreshold = safeThreshold;
        this.symbol = symbol;
        this.points = points;
        this.name = name;
        this.attack = attack;
    }

    @Override
    public String toString() {
        return name;
    }

}
