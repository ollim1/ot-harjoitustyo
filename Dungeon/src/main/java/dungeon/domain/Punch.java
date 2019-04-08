/*
 * @author olli m
 */

package dungeon.domain;

public class Punch implements Attack {

    @Override
    public double apply(Actor source, Actor target) {
        return target.damage(source.getMaxHealth() / 10 * 4);
    }

}
