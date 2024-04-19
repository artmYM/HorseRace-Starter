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
    private JButton startButton;
    private JButton gambleButton;
    private JTextField horseNameField;
    private String selectedCharacter = "";
    private JLabel selectedCharacterLabel;

    public GUI() {
        createUI();
    }

    private void createUI() {
        setTitle("Race Configurator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        setupMenuBar();
        setupTrackPanel(gbc);
        setupHorsePanel(gbc);
        setupButtons(gbc);

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
    }

    private void setupTrackPanel(GridBagConstraints gbc) {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 50));
        
        leftPanel.add(new JLabel("Track Length (10-50):"));
        trackLengthField = new JTextField(10);
        leftPanel.add(trackLengthField);
        
        leftPanel.add(new JLabel("Number of Tracks (2-8):"));
        noTracksField = new JTextField(10);
        leftPanel.add(noTracksField);
        
        leftPanel.add(new JLabel("Track Status:"));
        JComboBox<String> trackStatus = new JComboBox<>(new String[]{"Sunny", "Rainy", "Random"});
        leftPanel.add(trackStatus);
    
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(leftPanel, gbc);
    }
    
    private void setupHorsePanel(GridBagConstraints gbc) {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 10));
    
        rightPanel.add(new JLabel("Horse Breed:"));
        horseBreed = new JComboBox<>(new String[]{"Thoroughbred", "Arabian", "Quarter Horse"});
        rightPanel.add(horseBreed);
    
        rightPanel.add(new JLabel("Horse Color:"));
        horseColor = new JComboBox<>(new String[]{"Black", "White", "Brown"});
        rightPanel.add(horseColor);
    
        rightPanel.add(new JLabel("Horse Name:"));
        horseNameField = new JTextField(10);
        rightPanel.add(horseNameField);
    
        rightPanel.add(new JLabel("Selected Horse Character:"));
        selectedCharacterLabel = new JLabel("None selected");
        rightPanel.add(selectedCharacterLabel);
    
        JButton characterButton = new JButton("Select Horse Character");
        characterButton.addActionListener(this::openCharacterSelectionWindow);
        rightPanel.add(characterButton);
    
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(rightPanel, gbc);
    }
    
    private void openCharacterSelectionWindow(ActionEvent e) {
        JFrame characterWindow = new JFrame("Select Horse Character");
        characterWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        characterWindow.setLayout(new FlowLayout());
    
        String[] characters = {"🐎", "🎠", "🐴"};
        for (String character : characters) {
            JButton charButton = new JButton(character);
            charButton.addActionListener(event -> {
                selectedCharacter = character;
                selectedCharacterLabel.setText(character); 
                characterWindow.dispose(); 
            });
            characterWindow.add(charButton);
        }
    
        characterWindow.pack();
        characterWindow.setLocationRelativeTo(null);
        characterWindow.setVisible(true);
    }
       
    private void setupButtons(GridBagConstraints gbc) {
        startButton = new JButton("Start Race");
        startButton.addActionListener(this::startRace);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(startButton, gbc);
    
        JButton shopButton = new JButton("Shop");
        shopButton.addActionListener(this::openShopWindow);
        gbc.gridx = 1; 
        add(shopButton, gbc);
    
        gambleButton = new JButton("Gamble!");
        gambleButton.addActionListener(this::openGambleWindow);
        gambleButton.setBackground(Color.GREEN);
        gambleButton.setOpaque(true);
        gambleButton.setBorderPainted(false);
        gbc.gridx = 2; 
        add(gambleButton, gbc);
    }
    
    private void openShopWindow(ActionEvent e) {
        JFrame shopWindow = new JFrame("Shop");
        shopWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        shopWindow.setLayout(new FlowLayout());

        final int[] balance = new int[1];
        balance[0] = readBalance();
        JLabel balanceLabel = new JLabel("Current Balance: $" + balance[0]);
        shopWindow.add(balanceLabel);

        String[] items = {"Saddle", "Bridle", "Boots"};
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

            if (selectedCharacter.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a horse character before starting the race.", "Selection Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            String horseName = horseNameField.getText().trim(); // Get the horse's name
            System.out.println("Track Length: " + trackLength + ", Number of Tracks: " + noTracks);
            System.out.println("Horse Name: " + horseName + ", Breed: " + horseBreed.getSelectedItem() + ", Color: " + horseColor.getSelectedItem());
        
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid integer values for track settings.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void openGambleWindow(ActionEvent e) {
        JFrame gambleWindow = new JFrame("Gamble");
        gambleWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gambleWindow.setLayout(new FlowLayout());

        File file = new File("balance.txt");
        int balance = 100; 

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                balance = Integer.parseInt(reader.readLine().trim());
            } catch (IOException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Failed to read balance.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(String.valueOf(balance));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Failed to initialize balance.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        JLabel balanceLabel = new JLabel("Current Balance: $" + balance);
        gambleWindow.add(balanceLabel);

        // TODO: add more components to the gamble window

        gambleWindow.setSize(300, 200);
        gambleWindow.setLocationRelativeTo(null);
        gambleWindow.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}
