package main;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StatsManager {
    private static final String SAVE_FILE = "data/stats.json";
    private static StatsManager instance;

    private StatsManager() {
        // приватний конструктор для Singleton
    }

    public static StatsManager getInstance() {
        if (instance == null) {
            instance = new StatsManager();
        }
        return instance;
    }

    private JSONObject loadStats() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(SAVE_FILE)), StandardCharsets.UTF_8);
            return (JSONObject) new JSONParser().parse(content);
        } catch (IOException | ParseException e) {
            return new JSONObject(); // повертаємо пустий JSON
        }
    }
    

    @SuppressWarnings("unchecked")
    public void saveResult(String result) {
        JSONObject stats = loadStats();

        Long playerWins = stats.get("playerWins") != null ? (Long) stats.get("playerWins") : 0L;
        Long dealerWins = stats.get("dealerWins") != null ? (Long) stats.get("dealerWins") : 0L;

        if (result.contains("Player wins")) {
            playerWins++;
        } else {
            dealerWins++;
        }

        stats.put("playerWins", playerWins);
        stats.put("dealerWins", dealerWins);

        try (FileWriter file = new FileWriter(SAVE_FILE)) {
            file.write(stats.toJSONString());
            System.out.println("Game stats saved to " + SAVE_FILE);
        } catch (IOException e) {
            System.out.println("Failed to save stats: " + e.getMessage());
        }
    }
 
    public void showStats() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(SAVE_FILE)), StandardCharsets.UTF_8);
            JSONObject stats = (JSONObject) new JSONParser().parse(content);
    
            Long playerWins = stats.get("playerWins") != null ? (Long) stats.get("playerWins") : 0L;
            Long dealerWins = stats.get("dealerWins") != null ? (Long) stats.get("dealerWins") : 0L;
    
            System.out.println("Statistics:");
            System.out.println("Player wins: " + playerWins);
            System.out.println("Dealer wins: " + dealerWins);
        } catch (IOException | ParseException e) {
            System.out.println("No statistics available yet.");
        }
    }
}