package blockchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chain implements Serializable {

    private static final long serialVersionUID = 3037903299810725761L;

    private static final int SECONDS_TO_INCREASE = 15;
    private static final int SECONDS_TO_DECREASE = 5;

    private final List<Block> blocks = new ArrayList<>();
    private int currentCountZero;
    private final int maximumBlocks;

    Chain(int startCountOfZero, int maximumBlocks){
        this.currentCountZero = startCountOfZero;
        this.maximumBlocks = maximumBlocks;
    }

    public boolean isFull() {
        return maximumBlocks == blocks.size();
    }

    public synchronized boolean addBlock(Block block){
        blocks.add(block);
        if(!this.validate()){
            blocks.remove(block);
            return false;
        }

        if(block.getGenerationSeconds() > SECONDS_TO_INCREASE){
            currentCountZero--;
        }
        if(block.getGenerationSeconds() < SECONDS_TO_DECREASE){
            currentCountZero++;
        }

        return true;
    }

    public synchronized Block getBlock(int index){
        return blocks.get(index);
    }

    public int getCurrentCountZero() {
        return currentCountZero;
    }

    public int size(){
        return blocks.size();
    }

    public boolean validate(){
        for(int i = 1; i < blocks.size(); i++){
            Block currentBlock = blocks.get(i);
            Block previousBlock = blocks.get(i-1);
            if(!currentBlock.getPreviousBlockHash().equals(previousBlock.getHash())){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringToPrint = new StringBuilder();
        for(int i = 0; i < blocks.size(); i++){
            String increaseDecreaseSentence = "N stays the same";
            if(i != 0 && blocks.get(i - 1).getCountZero() != blocks.get(i).getCountZero()) {
                if(blocks.get(i - 1).getCountZero() < blocks.get(i).getCountZero()){
                    increaseDecreaseSentence = "N was increased to " + blocks.get(i).getCountZero();
                }
                else {
                    increaseDecreaseSentence = "N was decreased to " + blocks.get(i).getCountZero();
                }
            }
            stringToPrint.append(blocks.get(i));
            stringToPrint.append("\n");
            stringToPrint.append(increaseDecreaseSentence);
            stringToPrint.append("\n\n");
        }
        return stringToPrint.toString();
    }
}
