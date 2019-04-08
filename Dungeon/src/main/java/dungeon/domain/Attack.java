/*
 * @author olli m
 */
package dungeon.domain;

public interface Attack {

    public int apply(Actor source, Actor target);
}
