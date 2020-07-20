package blockchain;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    private final static int POOL_SIZE = 10;
    private final static int COUNT_MINERS = 100;

    public static void main(String[] args) throws InterruptedException {
        ChainRepository chainRepository = new FileChainRepository();
        Chain chain = chainRepository.load();

        MessageContainer messageContainer = new MessageContainer();
        ExecutorService executorService = Executors.newFixedThreadPool(POOL_SIZE);
        executorService.submit(new RandomChatThread(messageContainer));
        for (int i = 0; i < COUNT_MINERS; i++) {
            executorService.submit(new BlockChainMiner(chain, messageContainer));
        }
        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);

        chainRepository.save(chain);
        System.out.println(chain);
    }
}
