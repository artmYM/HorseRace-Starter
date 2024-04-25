import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class HorseManager {
    private Random random = new Random();
    private Horse[] horses;

    public HorseManager(int numberOfTracks, char userSymbol, String userName, double userConfidence) {
<<<<<<< HEAD
=======
        if (numberOfTracks < 2) {
            throw new IllegalArgumentException("At least two horses are required to initialize HorseManager.");
        } else if (numberOfTracks > 8){numberOfTracks = 8;}
>>>>>>> main
        horses = new Horse[numberOfTracks];
        initializeHorses(userSymbol, userName, userConfidence);
    }

    private void initializeHorses(char userSymbol, String userName, double userConfidence) {
<<<<<<< HEAD

        List<String> availableNames = new ArrayList<String>(List.of(
=======
        List<String> availableNames = new ArrayList<>(List.of(
>>>>>>> main
            "Blitz", "Shadow", "Mystery", "Spirit", "Champion", "Lightning", "Thunder", "Gallop", "Whisper", "Storm"
        ));
        
        if (horses.length > availableNames.size()) {
            throw new IllegalArgumentException("Not enough unique names to assign to horses");
        }

        horses[0] = new Horse(userSymbol, userName, userConfidence);
        availableNames.remove(userName);

        for (int i = 1; i < horses.length; i++) {
            int index = random.nextInt(availableNames.size());
            String randomName = availableNames.get(index);
            char randomSymbol = randomName.charAt(0);
            double randomConfidence = 0.5 + random.nextDouble() * 0.5;
            horses[i] = new Horse(randomSymbol, randomName, randomConfidence);
            availableNames.remove(index);
        }
    }

    public Horse[] getHorses() {
        return horses;
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> main
