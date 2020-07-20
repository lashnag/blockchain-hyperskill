package blockchain;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Block implements Serializable {

    private int magicNumber = 0;
    private final int countZero;
    private final long miner;
    private final int id;
    private final long timestamp;
    private final String previousBlockHash;
    private final String blockHash;
    private List<String> messages;
    private long generationSeconds = 0;

    public Block(int countZero, long miner, MessageContainer messageContainer){
        this.countZero = countZero;
        this.miner = miner;
        this.id = 1;
        this.timestamp = new Date().getTime();
        this.previousBlockHash = "0";
        this.blockHash = this.generateHash(messageContainer);
    }

    public Block(int countZero, long miner, Block previousBlock, MessageContainer messageContainer){
        this.countZero = countZero;
        this.miner = miner;
        this.id = previousBlock.id + 1;
        this.timestamp = new Date().getTime();
        this.previousBlockHash = previousBlock.blockHash;
        this.blockHash = this.generateHash(messageContainer);
    }

    public int getCountZero() {
        return countZero;
    }

    public String getHash(){
        return this.blockHash;
    }

    private String generateHash(MessageContainer messageContainer){
        LocalDateTime generationTimeStart = LocalDateTime.now();
        String startHashPart = "0".repeat(this.countZero);
        while (true){
            List<String> currentMessages = messageContainer.getMessages();
            this.magicNumber = (new Random()).nextInt();
            String currentHash = StringUtil.applySha256(
                String.valueOf(this.timestamp) +
                this.id +
                this.magicNumber +
                previousBlockHash +
                currentMessages.toString()
            );
            if(currentHash.substring(0, this.countZero).equals(startHashPart)){
                LocalDateTime generationTimeEnd = LocalDateTime.now();
                generationSeconds = Duration.between(generationTimeStart, generationTimeEnd).toSeconds();
                messageContainer.retainAll(currentMessages);
                this.messages = currentMessages;
                return currentHash;
            }
        }
    }

    public long getGenerationSeconds(){
        return this.generationSeconds;
    }

    public String getPreviousBlockHash(){
        return previousBlockHash;
    }

    @Override
    public String toString() {
        StringBuilder currentMessagesString = new StringBuilder();
        for(String message : this.messages){
            currentMessagesString.append(message);
            currentMessagesString.append("\n");
        }

        return "Block:\n" +
            "Created by miner # " + this.miner + "\n" +
            "Id: " + this.id + "\n" +
            "Timestamp: " + this.timestamp + "\n" +
            "Magic number: " + this.magicNumber + "\n" +
            "Hash of the previous block: \n" +
            this.previousBlockHash + "\n" +
            "Hash of the block: \n" +
            this.blockHash + "\n" +
            "Block data: \n" +
            currentMessagesString.toString() +
            "Block was generating for " + this.generationSeconds + " seconds";
    }
}
