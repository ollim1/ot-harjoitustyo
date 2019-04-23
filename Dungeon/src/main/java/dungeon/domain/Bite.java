/*
 * @author olli m
 */
package dungeon.domain;

import dungeon.backend.Game;

public class Bite extends Attack {

    @Override
    public double apply(Game game, Actor source, Actor target) {
        int r;
        if (game == null) {
            r = 1;
        } else {
            r = game.getRng().nextInt(game.getDieSize() + 1);
        }
        if (r == 0) {
            return 0;
        }
        double modifier = source.getMaxHealth() / target.getMaxHealth();
        double damage = modifier * (2 + 0.25 * r);
        pushMessage(r, source, target, damage);
        return target.damage(damage);
    }

    @Override
    public int cost() {
        return 100;
    }

    @Override
    public String actionVerb() {
        return "bit";
    }

}
