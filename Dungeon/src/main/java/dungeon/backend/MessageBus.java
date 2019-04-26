/*
 * @author olli m
 */
package dungeon.backend;

import dungeon.domain.Message;
import java.util.ArrayDeque;

/**
 * A message bus for passing ingame status messages. Implemented as a singleton
 * to avoid excessive amounts of injection.
 *
 * @author londes
 */
public class MessageBus {

    private static MessageBus instance = new MessageBus();
    private final ArrayDeque<Message> queue;

    protected MessageBus() {
        this.queue = new ArrayDeque<>();
    }

    /**
     * Fetches an instance of the message bus.
     *
     * @return
     */
    public static MessageBus getInstance() {
        return instance;
    }

    /**
     * Gets the first message queued up in the message bus.
     *
     * @return
     */
    public Message poll() {
        return queue.pollFirst();
    }

    /**
     * Pushes a message at the end of the queue.
     *
     * @param message
     */
    public void push(Message message) {
        queue.addLast(message);
    }

    /**
     * Creates a new Message from a string and pushes at the end of the queue.
     *
     * @param string
     */
    public void push(String string) {
        push(new Message(string));
    }
}
