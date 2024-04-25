import java.util.Scanner;

public class RaceTest {
    public static void main(String[] args) {
        // defaultTest();
        // test1();
        // test2();
        // test3();
        // test4();
        // test5();
        // test6();
        // test7();
        // test8();
        // test9();
        // test10();
        // test11();
        // test12();
         test13();
    }

    public static void defaultTest(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter race length (10 to 100):");
        int raceLength = Integer.parseInt(scanner.nextLine().trim());

        char userSymbol = 'U';
        String userName = "UserHorse";
        double userConfidence = 0.9;

        HorseManager horseManager = new HorseManager(8, userSymbol, userName, userConfidence);
        Race race = new Race(raceLength);

        for (Horse horse : horseManager.getHorses()) {
            race.addHorse(horse);
        }

        String input = "yes";
        while (input.equalsIgnoreCase("yes")) {
            try {
                race.startRace();
            } catch (Exception e) {
                System.out.println("Error occurred during the race: " + e.getMessage());
                break;
            }

            for (Horse horse : horseManager.getHorses()) {
                horse.goBackToStart();
            }

            System.out.println("Would you like to play again? (yes/no)");
            input = scanner.nextLine().trim();
        }

        scanner.close();
    }

    // test with maximum horses at a maximum raceLength
    // user may expect all the horses to fall
    public static void test1(){
        char userSymbol = 'U';
        String userName = "UserHorse";
        double userConfidence = 0.9;

        int raceLength = 100;

        HorseManager horseManager = new HorseManager(8, userSymbol, userName, userConfidence);
        Race race = new Race(raceLength);
        for (Horse horse : horseManager.getHorses()) {
            race.addHorse(horse);
        }
        race.startRace();
    }

    // test with maximum horses at a minimum raceLength
    // user may expect a draw between multiple horses
    public static void test2(){
        char userSymbol = 'U';
        String userName = "UserHorse";
        double userConfidence = 0.9;

        int raceLength = 10;

        HorseManager horseManager = new HorseManager(8, userSymbol, userName, userConfidence);
        Race race = new Race(raceLength);
        for (Horse horse : horseManager.getHorses()) {
            race.addHorse(horse);
        }
        race.startRace();
    }

    // test with maximum horses at moderate raceLength
    // user may expect there to be a single winner
    public static void test3(){
        char userSymbol = 'U';
        String userName = "UserHorse";
        double userConfidence = 0.9;

        int raceLength = 25;

        HorseManager horseManager = new HorseManager(8, userSymbol, userName, userConfidence);
        Race race = new Race(raceLength);
        for (Horse horse : horseManager.getHorses()) {
            race.addHorse(horse);
        }
        race.startRace();
    }

    // test with minimum horses at a short distance
    // user may expect there to be a single winner / a draw
    public static void test4(){
        char userSymbol = 'U';
        String userName = "UserHorse";
        double userConfidence = 0.9;

        int raceLength = 10;

        HorseManager horseManager = new HorseManager(2, userSymbol, userName, userConfidence);
        Race race = new Race(raceLength);
        for (Horse horse : horseManager.getHorses()) {
            race.addHorse(horse);
        }
        race.startRace();
    }

    // test with minimum horses at a moderate distance
    // user may expect there to be no winners
    public static void test5(){
        char userSymbol = 'U';
        String userName = "UserHorse";
        double userConfidence = 0.9;

        int raceLength = 25;

        HorseManager horseManager = new HorseManager(2, userSymbol, userName, userConfidence);
        Race race = new Race(raceLength);
        for (Horse horse : horseManager.getHorses()) {
            race.addHorse(horse);
        }
        race.startRace();
    }

    // test with one above maximum horses to demonstrate error handling
    // user may expect the race to begin with 8 horses
    public static void test6(){
        char userSymbol = 'U';
        String userName = "UserHorse";
        double userConfidence = 0.9;

        int raceLength = 20;

        HorseManager horseManager = new HorseManager(9, userSymbol, userName, userConfidence);
        Race race = new Race(raceLength);
        for (Horse horse : horseManager.getHorses()) {
            race.addHorse(horse);
        }
        race.startRace();
    }

    // test with well above maximum horses to demonstrate error handling
    // user may expect the race to begin with 8 horses
    public static void test7(){
        char userSymbol = 'U';
        String userName = "UserHorse";
        double userConfidence = 0.9;

        int raceLength = 20;

        HorseManager horseManager = new HorseManager(120, userSymbol, userName, userConfidence);
        Race race = new Race(raceLength);
        for (Horse horse : horseManager.getHorses()) {
            race.addHorse(horse);
        }
        race.startRace();
    }

    // test with one below minimum horses to demonstrate error handling
    // user may expect to see an error which explains that at least two horses are required
    public static void test8(){
        char userSymbol = 'U';
        String userName = "UserHorse";
        double userConfidence = 0.9;

        int raceLength = 20;

        HorseManager horseManager = new HorseManager(1, userSymbol, userName, userConfidence);
        Race race = new Race(raceLength);
        for (Horse horse : horseManager.getHorses()) {
            race.addHorse(horse);
        }
        race.startRace();
    }

    // test with negative amount of horses to demonstrate error handling
    // user may expect to see an error which explains that at least two horses are required
    public static void test9(){
        char userSymbol = 'U';
        String userName = "UserHorse";
        double userConfidence = 0.9;

        int raceLength = 20;

        HorseManager horseManager = new HorseManager(-1, userSymbol, userName, userConfidence);
        Race race = new Race(raceLength);
        for (Horse horse : horseManager.getHorses()) {
            race.addHorse(horse);
        }
        race.startRace();
    }

    // test with one below minimum race distance to demonstrate error handling
    // user may expect to see an error message which explains that they are using incorrect track length
    public static void test10(){
        char userSymbol = 'U';
        String userName = "UserHorse";
        double userConfidence = 0.9;

        int raceLength = 9;

        HorseManager horseManager = new HorseManager(5, userSymbol, userName, userConfidence);
        Race race = new Race(raceLength);
        for (Horse horse : horseManager.getHorses()) {
            race.addHorse(horse);
        }
        race.startRace();
    }

    // test with one above maximum race distance to demonstrate error handling
    // user may expect to see an error message which explains that they are using incorrect track length
    public static void test11(){
        char userSymbol = 'U';
        String userName = "UserHorse";
        double userConfidence = 0.9;

        int raceLength = 101;

        HorseManager horseManager = new HorseManager(5, userSymbol, userName, userConfidence);
        Race race = new Race(raceLength);
        for (Horse horse : horseManager.getHorses()) {
            race.addHorse(horse);
        }
        race.startRace();
    }

    // test with one below maximum confidence to display the confidence after winning cannot raise above 1.00
    // user may expect to see a horse restart the race with maximum confidence after winning
    public static void test12(){
        char userSymbol = 'U';
        String userName = "UserHorse";
        double userConfidence = 0.99;

        int raceLength = 10;

        HorseManager horseManager = new HorseManager(2, userSymbol, userName, userConfidence);
        Race race = new Race(raceLength);
        for (Horse horse : horseManager.getHorses()) {
            race.addHorse(horse);
        }

        race.startRace();

        try {
                race.startRace();
            } catch (Exception e) {
                System.out.println("Error occurred during the race: " + e.getMessage());
            }

            for (Horse horse : horseManager.getHorses()) {
                horse.goBackToStart();
            }

    }

    // test with one below moderate confidence to display the confidence raises by 0.05 after winning
    // user may expect to see a horse restart the race with 0.05 more confidence after winning
    public static void test13(){
        char userSymbol = 'U';
        String userName = "UserHorse";
        double userConfidence = 0.9;

        int raceLength = 10;

        HorseManager horseManager = new HorseManager(2, userSymbol, userName, userConfidence);
        Race race = new Race(raceLength);
        for (Horse horse : horseManager.getHorses()) {
            race.addHorse(horse);
        }
        race.startRace();
        try {
            race.startRace();
        } catch (Exception e) {
            System.out.println("Error occurred during the race: " + e.getMessage());
        }

        for (Horse horse : horseManager.getHorses()) {
            horse.goBackToStart();
        }
    }
}