/*
 * @author olli m
 */
package dungeon.domain;

import javafx.scene.paint.Color;

public class Message {

    private String text;
    private Color color;

    public Message(String text, Color color) {
        this.text = text;
        this.color = color;
    }

    public Message(String text) {
        this.text = text;
        this.color = Color.BLACK;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }

}
