import javax.swing.*;
import java.awt.*;

public class RaceWindow extends JFrame {
    public RaceWindow(GUI gui) {
        setTitle("Race Results");
        setSize(500, 400); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea(20, 40); 
        textArea.setEditable(false);
        Font font = new Font("Monospaced", Font.PLAIN, 12);
        textArea.setFont(font);  

        if (gui.getTrackLength() != -1 && gui.getNumberOfTracks() != -1) {

            HorseManager horseManager = new HorseManager(
                gui.getNumberOfTracks(),
                gui.getSelectedCharacter(), 
                gui.getHorseName(),
                0.9
            );

            StringBuilder raceDetails = new StringBuilder("Race Configuration\n");
            raceDetails.append(String.format("Track Length: %d\n", gui.getTrackLength()))
                        .append(String.format("Number of Tracks: %d\n\n", gui.getNumberOfTracks()));

           
            Horse[] horses = horseManager.getHorses();
            for (int i = 0; i < horses.length; i++) {
                raceDetails.append(String.format("Horse %d: Name: %s, Symbol: %s, Confidence: %.2f\n",
                    i + 1, horses[i].getName(), horses[i].getSymbol(), horses[i].getConfidence()));
            }

            textArea.setText(raceDetails.toString());
        } else {
            textArea.setText("Error in retrieving race details. Ensure all inputs are correctly filled.");
        }

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
}
