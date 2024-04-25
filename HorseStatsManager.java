import java.io.*;
import java.util.*;

public class HorseStatsManager {
    private static final String STATS_FILE = "horse_stats.txt";

    static {
        ensureFileExists();
    }

    private static void ensureFileExists() {
        File file = new File(STATS_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Failed to create new stats file: " + e.getMessage());
            }
        }
    }

    public static void loadHorseStats(Horse horse) {
        ensureFileExists();
        try (BufferedReader reader = new BufferedReader(new FileReader(STATS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length < 4) { 
                    System.err.println("Corrupted data for horse: " + line);
                    continue; 
                }
                if (parts[0].trim().equals(horse.getName().trim())) { 
                    try {
                        horse.setWins(Integer.parseInt(parts[1].trim()));
                        horse.setLosses(Integer.parseInt(parts[2].trim()));
                        horse.setRecentPlacements(Arrays.asList(parts[3].split(",")));
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format in stats file: " + e.getMessage());
                    }
                    return;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading horse stats: " + e.getMessage());
        }
    }

    public static void updateHorseStats(Horse horse) {
        ensureFileExists();
        List<String> lines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(STATS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length < 4) {
                    System.err.println("Corrupted line ignored: " + line);
                    continue; 
                }
                if (parts[0].trim().equals(horse.getName().trim())) {

                    lines.add(formatStatsLine(horse));
                    found = true;
                } else {
                    lines.add(line);
                }
            }
            if (!found) {
                lines.add(formatStatsLine(horse)); 
            }
        } catch (IOException e) {
            System.err.println("Error reading horse stats: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STATS_FILE, false))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing horse stats: " + e.getMessage());
        }
    }

    private static String formatStatsLine(Horse horse) {
        String recentPlacements = String.join(",", horse.getRecentPlacements());
        return horse.getName() + ":" + horse.getWins() + ":" + horse.getLosses() + ":" + recentPlacements;
    }
}
