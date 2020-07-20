package blockchain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MessageRandomGenerator {

    private static List<String> names = new ArrayList<>(Arrays.asList("Bob", "Ann", "Din", "Clara"));
    private static List<String> messages = new ArrayList<>(Arrays.asList("Hello", "How are you?", "Bye", "Good"));

    public static String generate(){
        Random random = new Random();
        return names.get(random.nextInt(names.size())) + ": " + messages.get(random.nextInt(names.size()));
    }
}
