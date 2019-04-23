/*
 * @author olli m
 */
package dungeon.domain;

import java.util.HashMap;
import java.util.Map;
import squidpony.squidmath.RNG;

public enum Difficulty {

    EASY(new HashMap<MonsterType, Double>() {
        {
            put(MonsterType.DRAGON, -1.0);
            put(MonsterType.ORC, 0.75);
            put(MonsterType.GNOLL, 1.0);
        }
    }),
    NORMAL(new HashMap<MonsterType, Double>() {
        {
            put(MonsterType.DRAGON, 0.1);
            put(MonsterType.ORC, 0.5);
            put(MonsterType.GNOLL, 1.0);
        }
    }),
    HARD(new HashMap<MonsterType, Double>() {
        {
            put(MonsterType.DRAGON, 0.2);
            put(MonsterType.ORC, 0.27);
            put(MonsterType.GNOLL, 1.0);
        }
    });
    public final HashMap<MonsterType, Double> frequencies;

    private Difficulty(HashMap<MonsterType, Double> frequencies) {
        this.frequencies = frequencies;
    }

    /**
     * Rolls a monster type. Assuming the frequencies in a difficulty level add
     * up to one.
     *
     * @param rng RNG from Squidlib
     * @return
     */
    public MonsterType rollType(RNG rng) {
        double r = rng.nextDouble();
        MonsterType ret = null;
        double smallest = Double.POSITIVE_INFINITY;
        for (Map.Entry<MonsterType, Double> monster : frequencies.entrySet()) {
            if (monster.getValue() < smallest && monster.getValue() > r) {
                ret = monster.getKey();
                smallest = monster.getValue();
            }
        }
        return ret;
    }
}
