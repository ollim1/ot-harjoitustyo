/*
 * @author olli m
 */
package dungeon.domain;

public enum MonsterType {

    DRAGON(35, 0.9, 0.0, 2.0, 0.3, 0.9, 'D', new Bite()),
    ORC(20, 0.8, 10, 1.5, 0.3, 0.8, 'o', new Punch()),
    GNOLL(25, 0.8, 10, 1.5, 0.1, 0.8, 'd', new Punch());

    public final double maxHealth;
    public final double visionRatio;
    public final double alertRadius;
    public final double escapeBias;
    public final double fleeThreshold;
    public final double safeThreshold;
    public final char symbol;
    public final Attack attack;

    private MonsterType(double maxHealth, double visionRatio, double alertRadius, double escapeBias, double fleeThreshold, double safeThreshold, char symbol, Attack attack) {
        this.maxHealth = maxHealth;
        this.visionRatio = visionRatio;
        this.alertRadius = alertRadius;
        this.escapeBias = escapeBias;
        this.fleeThreshold = fleeThreshold;
        this.safeThreshold = safeThreshold;
        this.symbol = symbol;
        this.attack = attack;
    }

}
