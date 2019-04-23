/*
 * @author olli m
 */
package dungeon.backend;

import dungeon.domain.Message;
import java.util.ArrayDeque;

public class MessageBus {

    private static MessageBus instance = new MessageBus();
    private final ArrayDeque<Message> queue;

    protected MessageBus() {
        this.queue = new ArrayDeque<>();
    }

    public static MessageBus getInstance() {
        return instance;
    }

    public Message poll() {
        return queue.pollFirst();
    }

    public void push(Message message) {
        queue.addLast(message);
    }
}
