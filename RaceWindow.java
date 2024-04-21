import javax.swing.*;
import java.awt.*;

public class RaceWindow extends JFrame {
    private JTextArea textArea;

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
        setVisible(true);

        startRace(gui);
    }

    private void startRace(GUI gui) {
        String trackCondition = gui.getTrackStatus();
        if (gui.getTrackLength() != -1 && gui.getNumberOfTracks() != -1) {
            Race race = new Race(gui.getTrackLength(), textArea, trackCondition);

            HorseManager horseManager = new HorseManager(
                gui.getNumberOfTracks(),
                gui.getSelectedCharacter(), 
                gui.getHorseName(),
                0.9 
            );

            Horse[] horses = horseManager.getHorses();
            for (Horse horse : horses) {
                race.addHorse(horse);
            }

            new Thread(() -> race.startRace()).start();
        } else {
            textArea.setText("Error in retrieving race details. Ensure all inputs are correctly filled.");
        }
    }
}
