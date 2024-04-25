import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;

public class GamblingGUI extends JFrame {
    private Horse[] horses;
    private int userBalance;
    private JLabel balanceLabel;
    private JTextArea horseList;
    private JTextField betAmount;
    private JButton placeBetButton;
    private JComboBox<String> horseSelection;

    public GamblingGUI(Horse[] horses) {
        this.horses = horses;
        setTitle("Gambling Window");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        initUI();
        loadUserBalance();
    }

    private void initUI() {
        horseList = new JTextArea();
        horseList.setEditable(false);

        Map<String, Horse> horseStats = loadHorseStats();
        displayHorses(horseStats);

        horseSelection = new JComboBox<>(Arrays.stream(horses).map(Horse::getName).toArray(String[]::new));
        betAmount = new JTextField(10);
        placeBetButton = new JButton("Place Bet");
        placeBetButton.addActionListener(e -> placeBet());

        JPanel betPanel = new JPanel();
        balanceLabel = new JLabel("Current Balance: $" + userBalance);
        betPanel.add(balanceLabel);
        betPanel.add(new JLabel("Select Horse:"));
        betPanel.add(horseSelection);
        betPanel.add(new JLabel("Bet Amount:"));
        betPanel.add(betAmount);
        betPanel.add(placeBetButton);

        add(new JScrollPane(horseList), BorderLayout.CENTER);
        add(betPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadUserBalance() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("balance.txt"));
            userBalance = Integer.parseInt(lines.get(0).trim());
        } catch (IOException e) {
            e.printStackTrace();
            userBalance = 100;
        }
        balanceLabel.setText("Current Balance: $" + userBalance);
    }

    private void placeBet() {
        String selectedHorse = (String) horseSelection.getSelectedItem();
        int bet;
        try {
            bet = Integer.parseInt(betAmount.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid bet amount.");
            return;
        }
        if (bet > userBalance) {
            JOptionPane.showMessageDialog(this, "Insufficient balance to place bet.");
            return;
        }
        userBalance -= bet;
        balanceLabel.setText("Current Balance: $" + userBalance);
    
        Horse selectedHorseObject = Arrays.stream(horses).filter(h -> h.getName().equals(selectedHorse)).findFirst().orElse(null);
        double odds = calculateOdds(selectedHorseObject);
    
        saveBetInfo(selectedHorse, bet, odds);
        dispose();
    }
    

    private void saveBetInfo(String horseName, int bet, double odds) {
        try {
            List<String> betInfo = List.of(horseName + ":" + bet + ":" + String.format("%.2f", odds));
            Files.write(Paths.get("bets.txt"), betInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    private void displayHorses(Map<String, Horse> horseStats) {
        StringBuilder sb = new StringBuilder("Choose a horse to bet on:\n");
        for (Horse horse : horses) {
            if (horseStats.containsKey(horse.getName())) {
                Horse statsHorse = horseStats.get(horse.getName());
                double odds = calculateOdds(statsHorse);
                sb.append("------------------------------\n")
                  .append("Horse: ").append(horse.getName()).append("\n")
                  .append("Wins: ").append(statsHorse.getWins() / 2).append("\n")
                  .append("Losses: ").append(statsHorse.getLosses() / 2).append("\n")
                  .append("Win Rate: ").append(String.format("%.2f%%", statsHorse.getWinRate())).append("\n")
                  .append("Recent Placements: ").append(statsHorse.getRecentPlacements().toString()).append("\n")
                  .append("Average Placement: ").append(String.format("%.2f", statsHorse.getAveragePlacement())).append("\n")
                  .append("Odds: ").append(String.format("%.2f", odds)).append("\n");
            }
        }
        sb.append("------------------------------\n");
        horseList.setText(sb.toString());
    }
    

    private double calculateOdds(Horse horse) {
        double winRate = horse.getWinRate();
        double averagePlacement = horse.getAveragePlacement();
        if (winRate == 0) return 10.0; 
        return (Math.max(1.0, 10 - winRate * averagePlacement / 10.0)) * 2.5; 
    }
    

    private Map<String, Horse> loadHorseStats() {
        Map<String, Horse> statsMap = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get("horse_stats.txt"));
            for (String line : lines) {
                String[] parts = line.split(":");
                if (parts.length >= 4) {
                    String name = parts[0];
                    int wins = Integer.parseInt(parts[1]);
                    int losses = Integer.parseInt(parts[2]);
                    List<String> placements = Arrays.stream(parts[3].split(","))
                                                .collect(Collectors.toList());
                    Horse horse = new Horse(' ', name, 0);
                    horse.setWins(wins);
                    horse.setLosses(losses);
                    horse.setRecentPlacements(placements);
                    statsMap.put(name, horse);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statsMap;
    }
}
