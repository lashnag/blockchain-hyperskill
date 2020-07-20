package blockchain;

public class RandomChatThread extends Thread {

    private MessageContainer messageContainer;

    RandomChatThread(MessageContainer messageContainer){
        this.messageContainer = messageContainer;
    }

    @Override
    public void run() {
        while(true) {
            String randomString = MessageRandomGenerator.generate();
            this.messageContainer.addMessage(randomString);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
