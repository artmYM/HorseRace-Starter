import javax.swing.*;
import java.awt.*;

public class GamblingGUI extends JFrame {
    private Horse[] horses;

    public GamblingGUI(Horse[] horses) {
        this.horses = horses;
        setTitle("Gambling Window");
        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextArea horseList = new JTextArea();
        horseList.setEditable(false);

        StringBuilder sb = new StringBuilder("Choose a horse to bet on:\n");
        for (Horse horse : horses) {
            sb.append(horse.getName()).append(" - Wins: ").append((horse.getWins())/2).append("\n");
        }
        horseList.setText(sb.toString());

        JScrollPane scrollPane = new JScrollPane(horseList);
        add(scrollPane, BorderLayout.CENTER);

    }
}
