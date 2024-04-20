import java.util.Random;

public class HorseManager {
    private Random random = new Random();
    private Horse[] horses;

    public HorseManager(int numberOfTracks, char userSymbol, String userName, double userConfidence) {
        horses = new Horse[numberOfTracks];
        initializeHorses(userSymbol, userName, userConfidence);
    }

    private void initializeHorses(char userSymbol, String userName, double userConfidence) {
        horses[0] = new Horse(userSymbol, userName, userConfidence);

        for (int i = 1; i < horses.length; i++) {
            char randomSymbol = generateRandomSymbol();
            String randomName = generateRandomName();
            double randomConfidence = random.nextDouble(); 
            horses[i] = new Horse(randomSymbol, randomName, randomConfidence);
        }
    }

    private char generateRandomSymbol() {
        return (char) ('A' + random.nextInt(26));
    }

    private String generateRandomName() {
        String[] names = {"Blitz", "Shadow", "Mystery", "Spirit", "Champion", "Lightning", "Thunder", "Gallop", "Whisper", "Storm"};
        return names[random.nextInt(names.length)];
    }

    public Horse[] getHorses() {
        return horses;
    }
}
