import java.util.ArrayList;
<<<<<<< HEAD
import java.util.concurrent.TimeUnit;
import javax.swing.JTextArea;
import java.util.function.Consumer;
import java.util.HashSet;
import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Race {
    private int raceLength;
    private ArrayList<Horse> horses;
    private JTextArea raceOutput;
    private String trackCondition;
    private Consumer<Horse> onWinnerDeclared;

    public Race(int distance, JTextArea raceOutput, String trackCondition, Consumer<Horse> onWinnerDeclared) {
        this.raceLength = distance;
        this.horses = new ArrayList<>();
        this.raceOutput = raceOutput;
        this.trackCondition = trackCondition;
        this.onWinnerDeclared = onWinnerDeclared;
=======
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Race {
    private int raceLength;
    private ArrayList<Horse> horses;

    public Race(int distance) {
        if (distance < 10 || distance > 100) {
            throw new IllegalArgumentException("Race length must be between 10 and 100.");
        }
        this.raceLength = distance;
        this.horses = new ArrayList<>();
>>>>>>> main
    }

    public void addHorse(Horse theHorse) {
        if (horses.size() < 8) {
<<<<<<< HEAD
            if (theHorse.hasWonRecentRace()) {
                double newConfidence = Math.min(1.0, theHorse.getConfidence() + 0.05); 
                theHorse.setConfidence(newConfidence);
                theHorse.setWonRecentRace(false); 
            }
            horses.add(theHorse);
            raceOutput.append("Added " + theHorse.getName() + " to the race.\n");
        } else {
            raceOutput.append("Cannot add more horses, race is full.\n");
=======
            horses.add(theHorse);
            System.out.println("Added " + theHorse.getName() + " to the race.");
        } else {
            System.out.println("Cannot add more horses, race is full.");
>>>>>>> main
        }
    }

    public void startRace() {
<<<<<<< HEAD
        boolean finished = false;
        boolean allHorsesFallen = false;
    
        for (Horse horse : horses) {
            horse.goBackToStart();
        }
    
        while (!finished && !horses.isEmpty() && !allHorsesFallen) {
            allHorsesFallen = true;
    
=======
        for (Horse horse : horses) {
            horse.goBackToStart();
        }

        boolean finished = false;
        while (!finished && !horses.isEmpty()) {
            boolean allHorsesFallen = true;
            List<Horse> finishingHorses = new ArrayList<>();

>>>>>>> main
            for (Horse horse : horses) {
                if (!horse.hasFallen()) {
                    allHorsesFallen = false;
                    moveHorse(horse);
<<<<<<< HEAD
                }
            }
    
            printRace();
    
            for (Horse horse : horses) {
                if (raceWonBy(horse)) {
                    horse.setWonRecentRace(true);
                    finished = true;
                    if (onWinnerDeclared != null) {
                        onWinnerDeclared.accept(horse);
                    }
                    break;
                }
            }
    
=======
                    if (raceWonBy(horse)) {
                        finishingHorses.add(horse);
                    }
                }
            }

            printRace(); 

            // Print race outcome for draws and winners before final printing of the race track
            if (allHorsesFallen) {
                System.out.println("All horses have fallen, no winner.");
                finished = true;
            } else if (finishingHorses.size() > 1) {
                System.out.println("It's a draw between: ");
                for (Horse horse : finishingHorses) {
                    System.out.println(horse.getName());
                    double newConfidence = Math.min(1.0, horse.getConfidence() + 0.05);
                    horse.setConfidence(newConfidence); // Increase confidence immediately
                }
                finished = true;
            } else if (finishingHorses.size() == 1) {
                Horse winner = finishingHorses.get(0);
                System.out.println("The winner is " + winner.getName() + "!");
                double newConfidence = Math.min(1.0, winner.getConfidence() + 0.05);
                winner.setConfidence(newConfidence); // Increase confidence immediately
                finished = true;
            }

>>>>>>> main
            if (!finished) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
<<<<<<< HEAD
                    raceOutput.append("The race was interrupted.\n");
=======
                    System.out.println("The race was interrupted.");
>>>>>>> main
                    return;
                }
            }
        }
<<<<<<< HEAD
    
        if (finished) {
            announceWinner();
            finishRace();
        } else {
            raceOutput.append("All horses have fallen, no winner.\n");
        }
    }
    
    private void announceWinner() {
        Horse winner = horses.stream()
                             .filter(horse -> !horse.hasFallen())
                             .max((h1, h2) -> h1.getDistanceTravelled() - h2.getDistanceTravelled())
                             .orElse(null);

        if (winner != null) {
            raceOutput.append("The winner is " + winner.getName() + "!\n");
        }
    }

    private void moveHorse(Horse theHorse) {
        boolean isUsersHorse = (horses.indexOf(theHorse) == 0);
    
        double extraStepProbability = 0;
    
        if (isUsersHorse) {
            Set<String> purchasedItems = readPurchasedItems();
            extraStepProbability = 0.05 * purchasedItems.size();
        }
    
        if (!theHorse.hasFallen()) {
            theHorse.moveForward();
            double fallProbability = 0.1 * theHorse.getConfidence() * theHorse.getConfidence();
            if ("Rainy".equals(trackCondition)) {
                fallProbability += 0.01;
            } else if ("Icy".equals(trackCondition)) {
                fallProbability += 0.02;
                if (Math.random() < 0.4) {
                    theHorse.moveForward();
                }
            }
    
            if (Math.random() < theHorse.getConfidence()) {
                theHorse.moveForward();
            }
            if (Math.random() < extraStepProbability) {
                theHorse.moveForward(); 
            }
            if (Math.random() < fallProbability) {
                theHorse.fall();
                theHorse.setConfidence(Math.max(0.0, theHorse.getConfidence() - 0.01));
            }
        }
    }
    
    
    private Set<String> readPurchasedItems() {
        File file = new File("purchasedItems.txt");
        Set<String> items = new HashSet<>();
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    items.add(line.trim());
                }
            } catch (IOException ex) {
                raceOutput.append("Failed to read purchased items.\n");
            }
        }
        return items;
    }
    

    private boolean raceWonBy(Horse theHorse) {
        return theHorse.getDistanceTravelled() >= raceLength;
    }

    private void printRace() {
        StringBuilder builder = new StringBuilder("\u000C");
        builder.append("Race Track:\n");
        for (Horse horse : horses) {
            builder.append(printLane(horse)).append("\n");
        }
        raceOutput.setText(builder.toString());
    }

    private String printLane(Horse theHorse) {
        StringBuilder lane = new StringBuilder();
        lane.append("|");

        for (int i = 0; i < theHorse.getDistanceTravelled(); i++) {
            lane.append(" ");
        }

        if (theHorse.hasFallen()) {
            lane.append("X");
        } else {
            lane.append(theHorse.getSymbol());
        }

        for (int i = theHorse.getDistanceTravelled() + 1; i < raceLength; i++) {
            lane.append(" ");
        }

        lane.append("|");
        lane.append(" ").append(theHorse.getName()).append(" (Confidence: ").append(String.format("%.2f", theHorse.getConfidence())).append(")");
        return lane.toString();
    }

    public int getRaceLength() {
        return raceLength;
    }

    public String getTrackCondition() {
        return trackCondition;
    }
    
    public Horse getWinner() {
        return horses.stream()
                     .filter(horse -> !horse.hasFallen())
                     .max((h1, h2) -> h1.getDistanceTravelled() - h2.getDistanceTravelled())
                     .orElse(null);
    }

    public void finishRace() {
        printRace();
        rankHorses();
        updateRecentRankings(); 
        handleBettingOutcome(); 
    
        if (onWinnerDeclared != null) {
            Horse winner = getWinner();
            if (winner != null) {
                onWinnerDeclared.accept(winner);
            }
        }
    }
    
    private void handleBettingOutcome() {
        Horse winner = getWinner();
        File file = new File("bets.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line = reader.readLine();  
                if (line != null && !line.isEmpty()) {
                    String[] betDetails = line.split(":");
                    if (betDetails.length == 3) {
                        String betHorseName = betDetails[0];
                        int betAmount = Integer.parseInt(betDetails[1]);
                        double multiplier = Double.parseDouble(betDetails[2]);
    
                        int balance = readCurrentBalance();
                        boolean won = winner != null && winner.getName().equals(betHorseName);
                        if (won) {
                            int winnings = (int) (betAmount * (multiplier - 1));
                            balance += winnings;
                            raceOutput.append(String.format("You won! Bet on %s earned $%d. New balance: $%d.\n", betHorseName, winnings, balance));
                        } else {
                            balance -= betAmount;
                            raceOutput.append(String.format("You lost! Bet on %s cost you $%d. New balance: $%d.\n", betHorseName, betAmount, balance));
                        }
                        saveNewBalance(balance);
                    }
                }
            } catch (IOException e) {
                raceOutput.append("Error reading bets or updating balance.\n");
            }
        
            clearBetsFile();
        }
    }
    

    private void clearBetsFile() {
        try {
           
            new PrintWriter("bets.txt").close();
        } catch (FileNotFoundException e) {
            raceOutput.append("Failed to clear bets file.\n");
        }
    }
    
    private int readCurrentBalance() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("balance.txt"));
            return Integer.parseInt(lines.get(0).trim());
        } catch (IOException e) {
            raceOutput.append("Failed to read balance. Using default balance of 100.\n");
            return 100;
=======
    }

    private void moveHorse(Horse theHorse) {
        if (!theHorse.hasFallen()) {
            double fallProbability = 0.1 * theHorse.getConfidence() * theHorse.getConfidence();
            if (Math.random() < theHorse.getConfidence()) {
                theHorse.moveForward();
            }
            if (Math.random() < fallProbability) {
                theHorse.fall();
                theHorse.setConfidence(Math.max(0.0, theHorse.getConfidence() - 0.01)); // Decrease confidence on fall
            }
        }
    }

    private boolean raceWonBy(Horse theHorse) {
        return theHorse.getDistanceTravelled() >= raceLength;
    }

    private void printRace() {
        System.out.println("\033[H\033[2J"); 
        System.out.println("Race Track:");
    
        String topBottomBorder = new String(new char[raceLength + 2]).replace('\0', '=');
    
        System.out.println(topBottomBorder); 
        for (Horse horse : horses) {
            System.out.println(printLane(horse));
>>>>>>> main
        }
        System.out.println(topBottomBorder);
    }
    
<<<<<<< HEAD
    private void saveNewBalance(int balance) {
        try {
            Files.write(Paths.get("balance.txt"), Collections.singletonList(String.valueOf(balance)));
        } catch (IOException e) {
            raceOutput.append("Failed to save new balance.\n");
        }
    }
    

    private void updateRecentRankings() {
        for (int i = 0; i < horses.size(); i++) {
            Horse horse = horses.get(i);
            horse.addRecentPlacement(i + 1);  
        }
    }

    private void rankHorses() {
        horses.sort((h1, h2) -> {
            if (h1.getDistanceTravelled() == h2.getDistanceTravelled()) {
                return Double.compare(h2.getConfidence(), h1.getConfidence()); 
            }
            return h2.getDistanceTravelled() - h1.getDistanceTravelled(); 
        });

        StringBuilder ranking = new StringBuilder();
        ranking.append("Final race rankings:\n");
        int rank = 1;

        for (Horse horse : horses) {
            ranking.append(rank).append(" - ").append(horse.getName())
                   .append(" (Distance: ").append(horse.getDistanceTravelled())
                   .append(", Confidence: ").append(String.format("%.2f", horse.getConfidence()))
                   .append(")\n");
            rank++;
        }

        raceOutput.append(ranking.toString());
    }
    
}
=======
    private String printLane(Horse theHorse) {
        StringBuilder lane = new StringBuilder("|");
        for (int i = 0; i < theHorse.getDistanceTravelled(); i++) {
            lane.append(" ");
        }

        lane.append(theHorse.hasFallen() ? "X" : theHorse.getSymbol());

        for (int i = theHorse.getDistanceTravelled() + 1; i < raceLength; i++) {
            lane.append(" ");
        }

        lane.append("|").append(" ").append(theHorse.getName()).append(" (Confidence: ").append(String.format("%.2f", theHorse.getConfidence()) + ")");
        return lane.toString();
    }
}
>>>>>>> main
