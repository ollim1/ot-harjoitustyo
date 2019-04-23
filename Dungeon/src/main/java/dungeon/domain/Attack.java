/*
 * @author olli m
 */
package dungeon.domain;

import dungeon.backend.Game;

public interface Attack {

    public double apply(Game game, Actor source, Actor target);

    public int cost();
}
