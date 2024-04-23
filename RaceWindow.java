import javax.swing.*;
import java.awt.*;

public class RaceWindow extends JFrame {
    private JTextArea textArea;
    private JButton restartButton;
    private Race race;
    private Horse[] horses;

    public RaceWindow(GUI gui) {
        setTitle("Race Results");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        textArea = new JTextArea(20, 40);
        textArea.setEditable(false);
        Font font = new Font("Monospaced", Font.PLAIN, 12);
        textArea.setFont(font);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        restartButton = new JButton("Restart Race");
        restartButton.addActionListener(e -> restartRace());
        restartButton.setEnabled(false);
        add(restartButton, BorderLayout.SOUTH);

        setVisible(true);

        startRace(gui);
    }

    private void startRace(GUI gui) {
        String trackCondition = gui.getTrackStatus();
        if (gui.getTrackLength() != -1 && gui.getNumberOfTracks() != -1) {
            race = new Race(gui.getTrackLength(), textArea, trackCondition, this::displayWinner);

            HorseManager horseManager = new HorseManager(
                gui.getNumberOfTracks(),
                gui.getSelectedCharacter(), 
                gui.getHorseName(),
                0.6
            );

            horses = horseManager.getHorses();
            for (Horse horse : horses) {
                race.addHorse(horse);
            }

            runRaceThread();
        } else {
            textArea.setText("Error in retrieving race details. Ensure all inputs are correctly filled.");
        }
    }

    private void runRaceThread() {
        new Thread(() -> {
            race.startRace();
            SwingUtilities.invokeLater(() -> restartButton.setEnabled(true));
        }).start();
    }

    private void restartRace() {
        textArea.setText("");
        race = new Race(race.getRaceLength(), textArea, race.getTrackCondition(), this::displayWinner);

        for (Horse horse : horses) {
            horse.goBackToStart();
            race.addHorse(horse);
        }

        runRaceThread();
    }

    private void displayWinner(Horse winner) {
        SwingUtilities.invokeLater(() -> {
            textArea.append("\nWinner: " + winner.getName());
            textArea.setCaretPosition(textArea.getDocument().getLength());
            updateHorseStats();
        });
    }

    private void updateHorseStats() {
        for (Horse horse : horses) {
            if (horse == race.getWinner()) {
                horse.incrementWins();
            } else {
                horse.incrementLosses();
            }
            HorseStatsManager.updateHorseStats(horse);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RaceWindow(new GUI()));
    }
}
