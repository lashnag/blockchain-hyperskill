package blockchain;

import java.util.LinkedList;
import java.util.List;

public class MessageContainer {

    private volatile List<String> messages = new LinkedList<>();

    public void addMessage(String message) {
        this.messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }

    public void retainAll(List<String> messages){
        this.messages.retainAll(messages);
    }
}
