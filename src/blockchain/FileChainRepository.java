package blockchain;

import java.io.*;

public class FileChainRepository implements ChainRepository {

    private final String dataPathName = "blockchain.data";

    @Override
    public void save(Chain chain){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.dataPathName);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
            objectOutputStream.writeObject(chain);
            objectOutputStream.close();
        }
        catch (IOException exception){
            System.out.println(exception);
        }
    }

    @Override
    public Chain load() {
        try {
            FileInputStream sileInputStream = new FileInputStream(this.dataPathName);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(sileInputStream);
            ObjectInputStream objectOutputStream = new ObjectInputStream(bufferedInputStream);
            Object chain = objectOutputStream.readObject();
            objectOutputStream.close();
            return (Chain) chain;
        }
        catch (IOException|ClassNotFoundException exception){
//            System.out.println(exception);
        }
        return new Chain(0, 5);
    }
}
