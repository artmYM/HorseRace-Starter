<<<<<<< HEAD
import java.util.LinkedList;
import java.util.List;

public class Horse {
    private int horseID;
=======
public class Horse {

>>>>>>> main
    private char horseSymbol;
    private String horseName;
    private double horseConfidence;
    private boolean hasFallen;
    private int distanceTravelled;
<<<<<<< HEAD
    private boolean hasWonRecentRace;
    private int wins;
    private int losses;
    private int movesBeforeFall;
    private int totalFalls;
    private LinkedList<Integer> recentPlacements = new LinkedList<>();
    private static int nextID = 1;
    

    public Horse(char horseSymbol, String horseName, double horseConfidence) {
        this.horseID = nextID++;
=======

    // Constructor for Horse class
    public Horse(char horseSymbol, String horseName, double horseConfidence) {
>>>>>>> main
        this.horseSymbol = horseSymbol;
        this.horseName = horseName;
        this.horseConfidence = horseConfidence;
        this.hasFallen = false;
        this.distanceTravelled = 0;
        this.hasWonRecentRace = false;
        HorseStatsManager.loadHorseStats(this);
    }
    
<<<<<<< HEAD
    public double getWinRate() {
        int totalRaces = wins + losses;
        return totalRaces > 0 ? ((double) wins / totalRaces) * 100 : 0;
    }

    public double getAveragePlacement() {
        return recentPlacements.isEmpty() ? 0 :
               recentPlacements.stream().mapToInt(i -> i).average().orElse(0);
    }

    public List<String> getRecentPlacements() {
        List<String> placementsAsStrings = new LinkedList<>();
        for (Integer placement : recentPlacements) {
            placementsAsStrings.add(placement.toString());
        }
        return placementsAsStrings;
    }

    public void setRecentPlacements(List<String> placements) {
        recentPlacements.clear();
        for (String placement : placements) {
            recentPlacements.add(Integer.parseInt(placement));
        }
    }

    public void addRecentPlacement(int placement) {
        recentPlacements.addLast(placement);
        while (recentPlacements.size() > 5) {
            recentPlacements.removeFirst();
        }
        updateStatistics();
    }

    public int getMovesBeforeFall() {
        return movesBeforeFall;
    }

    public void setMovesBeforeFall(int movesBeforeFall) {
        this.movesBeforeFall = movesBeforeFall;
    }

    public int getTotalFalls() {
        return totalFalls;
    }

    public void setTotalFalls(int totalFalls) {
        this.totalFalls = totalFalls;
    }

    public void setWins(int wins) {
        this.wins = wins;
        updateStatistics();
    }

    public void recordWin() {
        System.out.println("Incrementing wins for " + getName());
        this.wins++;
        addRecentPlacement(1); 
        updateStatistics();
    }
    
    public void recordLoss() {
        System.out.println("Incrementing losses for " + getName());
        this.losses++;
        updateStatistics();
    }
    

    public void setLosses(int losses) {
        this.losses = losses;
        updateStatistics();
    }

    public void recordFall(int moves) {
        this.hasFallen = true;
        this.totalFalls++;
        this.movesBeforeFall += moves;
        updateStatistics();
    }

    private void updateStatistics() {
        HorseStatsManager.updateHorseStats(this);
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getID() {
        return horseID;
    }

    public boolean hasWonRecentRace() {
        return hasWonRecentRace;
    }

    public void setWonRecentRace(boolean won) {
        this.hasWonRecentRace = won;
    }

    public void fall() {
=======
    //Other methods of class Horse
    public void fall()
    {
>>>>>>> main
        this.hasFallen = true;
    }

    public double getConfidence() {
        return this.horseConfidence;
    }

    public int getDistanceTravelled() {
        return this.distanceTravelled;
    }

    public String getName() {
        return this.horseName;
    }

    public char getSymbol() {
        return this.horseSymbol;
    }

    public void goBackToStart() {
        this.distanceTravelled = 0;
        this.hasFallen = false;
    }

    public boolean hasFallen() {
        return this.hasFallen;
    }

    public void moveForward() {
<<<<<<< HEAD
        this.distanceTravelled++;
    }

    public void setConfidence(double newConfidence) {
        this.horseConfidence = newConfidence;
    }

    public void setSymbol(char newSymbol) {
        this.horseSymbol = newSymbol;
    }

    public void incrementWins() {
        wins++;
    }

    public void incrementLosses() {
        losses++;
    }
}
=======
        if (!hasFallen) {
            this.distanceTravelled++;
        } else {
            System.out.println("Cannot move forward, horse has fallen.");
        }
    }

    public void setConfidence(double newConfidence) {
        try {
            if (newConfidence < 0 || newConfidence > 1) {
                throw new IllegalArgumentException("Confidence must be between 0 and 1.");
            }
            this.horseConfidence = newConfidence;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void setSymbol(char newSymbol) {
        try {
            if (!Character.isLetter(newSymbol)) {
                throw new IllegalArgumentException("Symbol must be a letter.");
            }
            this.horseSymbol = newSymbol;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

}
>>>>>>> main
