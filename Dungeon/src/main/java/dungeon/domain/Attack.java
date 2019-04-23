/*
 * @author olli m
 */
package dungeon.domain;

import dungeon.backend.Game;
import dungeon.backend.MessageBus;
import javafx.scene.paint.Color;

public abstract class Attack {

    public abstract double apply(Game game, Actor source, Actor target);

    public abstract int cost();

    public void pushMessage(int r, Actor source, Actor target, double damage) {
        Color messageColor = Color.BLACK;
        if (r == 20) {
            messageColor = Color.RED;
        }
        String sourceString = "The " + (source.getActorType() == null ? null : source.getActorType().toString());
        String targetString = "the " + (target.getActorType() == null ? null : target.getActorType().toString());

        if (source.getClass() == Player.class) {
            sourceString = "You";
        }
        if (target.getClass() == Player.class) {
            if (source.getClass() == Player.class) {
                targetString = "yourself";
            }
            targetString = "you";
        }
        MessageBus.getInstance().push(new Message(sourceString + " " + actionVerb() + " " + targetString + " for " + String.format("%1.0f", damage) + " damage", messageColor));
    }

    public abstract String actionVerb();
}
