/*
 * @author olli m
 */
package dungeon.domain;

public class Bite implements Attack {

    @Override
    public int apply(Actor source, Actor target) {
        return target.damage(source.getMaxHealth() / 10 * 2);
    }

}
