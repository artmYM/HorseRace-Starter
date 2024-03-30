public class HorseTest {

    public static void testInitialization() {
        Horse horse = new Horse('\uD83D', "HorseTest", 0.5);
        boolean passed = true;

        if (horse.getSymbol() != '\uD83D') passed = false;
        if (!horse.getName().equals("HorseTest")) passed = false;
        if (horse.getConfidence() != 0.5) passed = false;
        if (horse.hasFallen()) passed = false;
        if (horse.getDistanceTravelled() != 0) passed = false;

        System.out.println("Test Initialization: " + (passed ? "PASSED" : "FAILED"));
    }

    public static void testMoveForward() {
        Horse horse = new Horse('\uD83D', "HorseTest", 0.5);
        horse.moveForward();
        boolean passed = (horse.getDistanceTravelled() == 1);

        System.out.println("Test Move Forward: " + (passed ? "PASSED" : "FAILED"));
    }

    public static void testFall() {
        Horse horse = new Horse('\uD83D', "HorseTest", 0.5);
        horse.fall();
        boolean passed = horse.hasFallen();

        System.out.println("Test Fall: " + (passed ? "PASSED" : "FAILED"));
    }

    public static void testGoBackToStart() {
        Horse horse = new Horse('\uD83D', "HorseTest", 0.5);
        horse.moveForward();
        horse.fall();
        horse.goBackToStart();
        boolean passed = true;

        if (horse.getDistanceTravelled() != 0) passed = false;
        if (horse.hasFallen()) passed = false;

        System.out.println("Test Go Back To Start: " + (passed ? "PASSED" : "FAILED"));
    }

    public static void main(String[] args) {
        testInitialization();
        testMoveForward();
        testFall();
        testGoBackToStart();
    }
}
