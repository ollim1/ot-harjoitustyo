/*
 * @author olli m
 */
package dungeon.domain;

public interface Attack {

    public double apply(Actor source, Actor target);
}
