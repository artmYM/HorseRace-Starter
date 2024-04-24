import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class GUI extends JFrame {
    private JTextField trackLengthField;
    private JTextField noTracksField;
    private JComboBox<String> horseBreed;
    private JComboBox<String> horseColor;
    private JComboBox<String> trackStatus;
    private JButton startButton;
    private JTextField horseNameField;
    private char selectedCharacter = '\u0000';
    private JLabel selectedCharacterLabel;

    public GUI() {
        createUI();
    }

    private void createUI() {
        setTitle("Race Configurator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(240, 240, 240));

        setupMenuBar();
        setupEditorPanels(mainPanel);
        setupButtons(mainPanel);

        add(mainPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Race Settings");
        menu.setFont(new Font("Serif", Font.BOLD, 16));
        menuBar.add(menu);
        setJMenuBar(menuBar);
        menuBar.setBackground(new Color(200, 200, 200));
    }

    private void setupEditorPanels(JPanel panel) {
        // Track Editor Section
        JPanel trackPanel = new JPanel();
        trackPanel.setLayout(new BoxLayout(trackPanel, BoxLayout.Y_AXIS));
        trackPanel.setBorder(BorderFactory.createTitledBorder("Track Editor"));
        trackPanel.setBackground(new Color(230, 230, 250));
        
        trackPanel.add(new JLabel("Track Length (10-50):"));
        trackLengthField = new JTextField(10);
        trackPanel.add(trackLengthField);
        
        trackPanel.add(new JLabel("Number of Tracks (2-8):"));
        noTracksField = new JTextField(10);
        trackPanel.add(noTracksField);
        
        trackPanel.add(new JLabel("Track Status:"));
        trackStatus = new JComboBox<>(new String[]{"Sunny", "Rainy", "Icy"});
        trackPanel.add(trackStatus);

        panel.add(trackPanel);

        JPanel horsePanel = new JPanel();
        horsePanel.setLayout(new BoxLayout(horsePanel, BoxLayout.Y_AXIS));
        horsePanel.setBorder(BorderFactory.createTitledBorder("Horse Editor"));
        horsePanel.setBackground(new Color(230, 230, 250));
        
        horsePanel.add(new JLabel("Horse Breed:"));
        horseBreed = new JComboBox<>(new String[]{"Thoroughbred", "Arabian", "Quarter Horse"});
        horsePanel.add(horseBreed);
        
        horsePanel.add(new JLabel("Horse Color:"));
        horseColor = new JComboBox<>(new String[]{"Black", "White", "Brown"});
        horsePanel.add(horseColor);
        
        horsePanel.add(new JLabel("Horse Name:"));
        horseNameField = new JTextField(10);
        horsePanel.add(horseNameField);
        
        horsePanel.add(new JLabel("Selected Horse Character:"));
        selectedCharacterLabel = new JLabel("None selected");
        horsePanel.add(selectedCharacterLabel);
        
        JButton characterButton = new JButton("Select Character");
        characterButton.addActionListener(this::openCharacterSelectionWindow);
        horsePanel.add(characterButton);

        panel.add(horsePanel);
    }

    private void setupButtons(JPanel panel) {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        buttonPanel.setBackground(new Color(240, 240, 240));
    
        startButton = new JButton("Start Race");
        startButton.addActionListener(this::startRace);
        startButton.setBackground(new Color(153, 204, 255));
        startButton.setOpaque(true);
        startButton.setBorderPainted(false);
        buttonPanel.add(startButton);
    
        JButton shopButton = new JButton("Shop");
        shopButton.addActionListener(this::openShopWindow);
        shopButton.setBackground(new Color(204, 204, 204));
        shopButton.setOpaque(true);
        shopButton.setBorderPainted(false);
        buttonPanel.add(shopButton);
    
        panel.add(buttonPanel);
    }
    

    private void openCharacterSelectionWindow(ActionEvent e) {
        JFrame characterWindow = new JFrame("Select Horse Character");
        characterWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        characterWindow.setLayout(new FlowLayout());

        char[] characters = {'\u2655', '\u2654', '♘', '\u2665', '\u2605', '\u2656', '\u2657', '♙', '♠', '♣', '♦', '♪', '☀', '❄', '∞'};
        for (char character : characters) {
            JButton charButton = new JButton(String.valueOf(character));
            charButton.addActionListener(event -> {
                selectedCharacter = character;
                selectedCharacterLabel.setText(String.valueOf(character)); 
                characterWindow.dispose(); 
            });
            characterWindow.add(charButton);
        }

        characterWindow.pack();
        characterWindow.setLocationRelativeTo(null);
        characterWindow.setVisible(true);
    } 
    
    private void openShopWindow(ActionEvent e) {
        JFrame shopWindow = new JFrame("Shop");
        shopWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        shopWindow.setLayout(new FlowLayout());

        final int[] balance = new int[1];
        balance[0] = readBalance();
        JLabel balanceLabel = new JLabel("Current Balance: $" + balance[0]);
        shopWindow.add(balanceLabel);

        String[] items = {"Speed Potion", "Jump Potion", "Steroids"};
        int[] prices = {50, 40, 30};
        Set<String> purchasedItems = readPurchasedItems();

        for (int i = 0; i < items.length; i++) {
            JButton buyButton = new JButton("Buy " + items[i] + " - $" + prices[i]);
            String itemName = items[i];
            int itemPrice = prices[i];

            buyButton.addActionListener(event -> {
                if (purchasedItems.contains(itemName)) {
                    JOptionPane.showMessageDialog(shopWindow, "This item has already been purchased.", "Purchase Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (balance[0] >= itemPrice) {
                    balance[0] -= itemPrice;
                    updateBalance(balance[0]);
                    purchasedItems.add(itemName);
                    writePurchasedItems(purchasedItems);
                    balanceLabel.setText("Current Balance: $" + balance[0]);
                    JOptionPane.showMessageDialog(shopWindow, "Item purchased successfully!");
                    buyButton.setEnabled(false);  
                } else {
                    JOptionPane.showMessageDialog(shopWindow, "Not enough balance!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            if (purchasedItems.contains(itemName)) {
                buyButton.setEnabled(false);  
            }
            shopWindow.add(buyButton);
        }

        shopWindow.pack();
        shopWindow.setLocationRelativeTo(null);
        shopWindow.setVisible(true);
    }

    private Set<String> readPurchasedItems() {
        File file = new File("purchasedItems.txt");
        Set<String> items = new HashSet<>();
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    items.add(line.trim());
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Failed to read purchased items.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return items;
    }

    private void writePurchasedItems(Set<String> purchasedItems) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("purchasedItems.txt"))) {
            for (String item : purchasedItems) {
                writer.write(item + "\n");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to update purchased items file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private int readBalance() {
        File file = new File("balance.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                return Integer.parseInt(reader.readLine().trim());
            } catch (IOException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Failed to read balance.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return 100; // Default balance if file doesn't exist or there's an error
    }
    
    private void updateBalance(int newBalance) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("balance.txt"))) {
            writer.write(String.valueOf(newBalance));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to update balance.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void startRace(ActionEvent e) {
        try {
            if (trackLengthField.getText().trim().isEmpty() || noTracksField.getText().trim().isEmpty() || horseNameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields, including the horse name, must be filled out.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int trackLength = Integer.parseInt(trackLengthField.getText().trim());
            int noTracks = Integer.parseInt(noTracksField.getText().trim());

            if (trackLength < 10 || trackLength > 50 || noTracks < 0 || noTracks > 8) {
                JOptionPane.showMessageDialog(this, "Track length must be between 10 and 50, and number of tracks between 0 and 8.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (selectedCharacter == '\u0000') {  
                JOptionPane.showMessageDialog(this, "Please select a horse character before starting the race.", "Selection Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            this.dispose(); 
            new RaceWindow(this); 
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid integer values for track settings.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }

    public int getTrackLength() {
        try {
            return Integer.parseInt(trackLengthField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid track length.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return -1;  
        }
    }

    public int getNumberOfTracks() {
        try {
            return Integer.parseInt(noTracksField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number of tracks.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return -1;  
        }
    }

    public String getHorseBreed() {
        return horseBreed.getSelectedItem().toString();
    }

    public String getHorseColor() {
        return horseColor.getSelectedItem().toString();
    }

    public String getHorseName() {
        return horseNameField.getText().trim();
    }

    public char getSelectedCharacter() {
        return selectedCharacter;
    }

    public String getTrackStatus() {
        return (String) trackStatus.getSelectedItem();
    }
}
