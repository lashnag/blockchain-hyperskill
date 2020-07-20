package blockchain;

public class BlockChainMiner extends Thread {

    private Chain chain;
    private MessageContainer messageContainer;

    public BlockChainMiner(Chain chain, MessageContainer messageContainer){
        this.chain = chain;
        this.messageContainer = messageContainer;
    }

    @Override
    public void run(){
        if(chain.isFull()){
            return;
        }

        Block newBlock;
        int currentCountZero = chain.getCurrentCountZero();
        if(chain.size() == 0){
            newBlock = new Block(currentCountZero, Thread.currentThread().getId(), this.messageContainer);
        }
        else {
            Block previousBlock = chain.getBlock(chain.size() - 1);
            newBlock = new Block(currentCountZero, Thread.currentThread().getId(), previousBlock, this.messageContainer);
        }

        this.chain.addBlock(newBlock);
    }
}
