package blockchain;

public interface ChainRepository {
    void save(Chain chain);
    Chain load();
}
