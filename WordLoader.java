import java.io.*;
import java.util.*;

public class WordLoader {
    List<String> words;

    public WordLoader(String filePath) {
        words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    words.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public String getRandomWord() {
        if (words.isEmpty()) return "";
        Random rand = new Random();
        return words.get(rand.nextInt(words.size()));
    }
}