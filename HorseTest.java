public class HorseTest {
    
    /**
     * Test initialization of the Horse object.
     */
    public static void testInitialization() {
        Horse horse = new Horse('\uD83D', "HorseTest", 0.5);
        
        assert horse.getSymbol() == '\uD83D' : "Symbol initialization failed.";
        assert horse.getName().equals("HorseTest") : "Name initialization failed.";
        assert horse.getConfidence() == 0.5 : "Confidence initialization failed.";
        assert !horse.hasFallen() : "Initial fallen state should be false.";
        assert horse.getDistanceTravelled() == 0 : "Initial distance should be zero.";
        
        System.out.println("Test Initialization: PASSED");
    }
    
    /**
     * Test the moveForward method and its condition check.
     */
    public static void testMoveForward() {
        Horse horse = new Horse('\uD83D', "HorseTest", 0.5);
        horse.moveForward();
        
        assert horse.getDistanceTravelled() == 1 : "Move forward method failed when horse has not fallen.";
        
        horse.fall();
        horse.moveForward();  // This should not increase distance
        assert horse.getDistanceTravelled() == 1 : "Move forward should not work when horse has fallen.";
        
        System.out.println("Test Move Forward: PASSED");
    }
    
    /**
     * Test the fall method.
     */
    public static void testFall() {
        Horse horse = new Horse('\uD83D', "HorseTest", 0.5);
        horse.fall();
        
        assert horse.hasFallen() : "Fall method failed.";
        
        System.out.println("Test Fall: PASSED");
    }
    
    /**
     * Test the goBackToStart method.
     */
    public static void testGoBackToStart() {
        Horse horse = new Horse('\uD83D', "HorseTest", 0.5);
        horse.moveForward();
        horse.fall();
        horse.goBackToStart();
        
        assert horse.getDistanceTravelled() == 0 && !horse.hasFallen() : "Go back to start method failed.";
        
        System.out.println("Test Go Back To Start: PASSED");
    }

    /**
     * Test the setConfidence method with both valid and invalid values.
     */
    public static void testSetConfidence() {
        Horse horse = new Horse('\uD83D', "HorseTest", 0.5);
        
        // Valid confidence value
        horse.setConfidence(0.8);
        assert horse.getConfidence() == 0.8 : "Setting valid confidence failed.";
        
        // Invalid confidence value
        horse.setConfidence(1.2);  // This should not change the confidence
        assert horse.getConfidence() == 0.8 : "Confidence should not update to invalid value.";

        // Invalid confidence value type
        horse.setConfidence('a');  // This should not change the confidence
        assert horse.getConfidence() == 0.8 : "Confidence should not update to invalid value.";
        
        System.out.println("Test Set Confidence: PASSED");
    }

    /**
     * Test the setSymbol method with both valid and invalid values.
     */
    public static void testSetSymbol() {
        Horse horse = new Horse('\uD83D', "HorseTest", 0.5);
        
        // Valid symbol
        horse.setSymbol('H');
        assert horse.getSymbol() == 'H' : "Setting valid symbol failed.";
        
        // Invalid symbol
        horse.setSymbol('1');  // This should not change the symbol
        assert horse.getSymbol() == 'H' : "Symbol should not update to invalid value.";
        
        System.out.println("Test Set Symbol: PASSED");
    }

    public static void main(String[] args) {
        testInitialization();
        testMoveForward();
        testFall();
        testGoBackToStart();
        testSetConfidence();
        testSetSymbol();
    }
}