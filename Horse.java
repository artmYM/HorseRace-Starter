public class Horse {

    private char horseSymbol;
    private String horseName;
    private double horseConfidence;
    private boolean hasFallen;
    private int distanceTravelled;

    // Constructor for Horse class
    public Horse(char horseSymbol, String horseName, double horseConfidence) {
        this.horseSymbol = horseSymbol;
        this.horseName = horseName;
        this.horseConfidence = horseConfidence;
        this.hasFallen = false;
        this.distanceTravelled = 0;
    }
    
    //Other methods of class Horse
    public void fall()
    {
        this.hasFallen = true;
    }
    
    public double getConfidence()
    {
        return this.horseConfidence;
    }
    
    public int getDistanceTravelled()
    {
        return this.distanceTravelled;
    }
    
    public String getName()
    {
        return this.horseName;
    }
    
    public char getSymbol()
    {
        return this.horseSymbol;
    }
    
    public void goBackToStart()
    {
        this.distanceTravelled = 0;
        this.hasFallen = false;
    }
    
    public boolean hasFallen()
    {
        return this.hasFallen;
    }

    public void moveForward() {
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