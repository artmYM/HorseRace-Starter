import javax.swing.*;
import java.awt.*;

public class RaceWindow extends JFrame {
    private JTextArea textArea;
    private JButton restartButton;
    private JButton gambleButton;
    private Race race;
    private Horse[] horses;

    public RaceWindow(starRaceGUI gui) {
        setTitle("Race Results");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        textArea = new JTextArea(20, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        restartButton = new JButton("Restart Race");
        restartButton.addActionListener(e -> restartRace());
        restartButton.setEnabled(false);

        gambleButton = new JButton("Go to Gambling");
        gambleButton.addActionListener(e -> openGamblingGUI());
        gambleButton.setEnabled(false);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(restartButton);
        buttonPanel.add(gambleButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);

        startRace(gui);
    }

    private void openGamblingGUI() {
        new GamblingGUI(horses).setVisible(true);
    }

    private void startRace(starRaceGUI gui) {
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
            SwingUtilities.invokeLater(() -> gambleButton.setEnabled(true));
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
        SwingUtilities.invokeLater(() -> new RaceWindow(new starRaceGUI()));
    }
}
