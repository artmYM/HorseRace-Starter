import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.swing.JTextArea;

public class Race {
    private int raceLength;
    private ArrayList<Horse> horses;
    private JTextArea raceOutput;
    private String trackCondition;

    public Race(int distance, JTextArea raceOutput, String trackCondition) {
        this.raceLength = distance;
        this.horses = new ArrayList<>();
        this.raceOutput = raceOutput;
        this.trackCondition = trackCondition; 
    }

    public void addHorse(Horse theHorse) {
        if (horses.size() < 8) {
            horses.add(theHorse);
            raceOutput.append("Added " + theHorse.getName() + " to the race.\n");
        } else {
            raceOutput.append("Cannot add more horses, race is full.\n");
        }
    }

    public void startRace() {
        boolean finished = false;
        boolean allHorsesFallen = false;

        for (Horse horse : horses) {
            horse.goBackToStart();
        }

        while (!finished && !horses.isEmpty() && !allHorsesFallen) {
            allHorsesFallen = true;

            for (Horse horse : horses) {
                if (!horse.hasFallen()) {
                    allHorsesFallen = false;
                    moveHorse(horse);
                }
            }

            printRace();

            for (Horse horse : horses) {
                if (raceWonBy(horse)) {
                    finished = true;
                    break;
                }
            }

            if (!finished) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    raceOutput.append("The race was interrupted.\n");
                    return;
                }
            }
        }

        if (!allHorsesFallen) {
            announceWinner();
        } else {
            raceOutput.append("All horses have fallen, no winner.\n");
        }
    }

    private void announceWinner() {
        Horse winner = horses.stream()
                             .max((h1, h2) -> h1.getDistanceTravelled() - h2.getDistanceTravelled())
                             .orElse(null);

        if (winner != null) {
            raceOutput.append("The winner is " + winner.getName() + "!\n");
        }
    }

    private void moveHorse(Horse theHorse) {
        if (!theHorse.hasFallen()) {
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
            if (Math.random() < fallProbability) {
                theHorse.fall();
            }
        }
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
}
