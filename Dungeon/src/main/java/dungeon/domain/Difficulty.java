/*
 * @author olli m
 */
package dungeon.domain;

import java.util.HashMap;
import java.util.Map;
import squidpony.squidmath.RNG;

public enum Difficulty {

    EASY(new HashMap<ActorType, Double>() {
        {
            put(ActorType.ORC, 0.75);
            put(ActorType.GNOLL, 1.0);
        }
    }, 10, 0.8, 0.01, "normal"),
    NORMAL(new HashMap<ActorType, Double>() {
        {
            put(ActorType.DRAGON, 0.1);
            put(ActorType.ORC, 0.5);
            put(ActorType.GNOLL, 1.0);
        }
    }, 8.0, 0.9, 0.02, "normal"),
    HARD(new HashMap<ActorType, Double>() {
        {
            put(ActorType.DRAGON, 0.2);
            put(ActorType.ORC, 0.27);
            put(ActorType.GNOLL, 1.0);
        }
    }, 7.0, 1.0, 0.03, "hard");
    public final HashMap<ActorType, Double> frequencies;
    public final double visionRadius;
    public final double visibilityThreshold;
    public final double monsterDensity;
    public final String name;

    private Difficulty(HashMap<ActorType, Double> frequencies, double visionRadius, double visibilityThreshold, double monsterDensity, String name) {
        this.frequencies = frequencies;
        this.visionRadius = visionRadius;
        this.visibilityThreshold = visibilityThreshold;
        this.monsterDensity = monsterDensity;
        this.name = name;
    }

    /**
     * Rolls a monster type. Returns the monster type with the lowest number
     * that clears a random number.
     *
     * @param rng RNG from Squidlib
     * @return
     */
    public ActorType rollType(RNG rng) {
        double r = rng.nextDouble();
        ActorType ret = null;
        double smallest = Double.POSITIVE_INFINITY;
        for (Map.Entry<ActorType, Double> monster : frequencies.entrySet()) {
            if (monster.getValue() < smallest && monster.getValue() > r) {
                ret = monster.getKey();
                smallest = monster.getValue();
            }
        }
        return ret;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
