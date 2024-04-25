import java.util.ArrayList;
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
    }

    public void addHorse(Horse theHorse) {
        if (horses.size() < 8) {
            horses.add(theHorse);
            System.out.println("Added " + theHorse.getName() + " to the race.");
        } else {
            System.out.println("Cannot add more horses, race is full.");
        }
    }

    public void startRace() {
        for (Horse horse : horses) {
            horse.goBackToStart();
        }

        boolean finished = false;
        while (!finished && !horses.isEmpty()) {
            boolean allHorsesFallen = true;
            List<Horse> finishingHorses = new ArrayList<>();

            for (Horse horse : horses) {
                if (!horse.hasFallen()) {
                    allHorsesFallen = false;
                    moveHorse(horse);
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

            if (!finished) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("The race was interrupted.");
                    return;
                }
            }
        }
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
        }
        System.out.println(topBottomBorder);
    }
    
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