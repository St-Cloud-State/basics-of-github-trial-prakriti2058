import javax.swing.*;
import java.awt.*;

import java.util.List;

public class ClerkMenuStateGUI implements State {
    private final WarehouseContext context;
    private JPanel panel;
    private WarehouseGUI parentFrame;

    public ClerkMenuStateGUI(WarehouseContext context) {
        this.context = context;
        initializeGUI();
    }

    public void setParentFrame(WarehouseGUI frame) {
        this.parentFrame = frame;
    }

    public JPanel getPanel() {
        return panel;
    }

    private void initializeGUI() {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(37, 47, 63)); // Attractive dark background

        // Title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(34, 139, 34)); // Professional forest green header
        titlePanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));
        
        JLabel titleLabel = new JLabel("Clerk Operations Dashboard", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Client Management | Product Catalog | Payment Processing", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(235, 245, 251));
        
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);
        panel.add(titlePanel, BorderLayout.NORTH);

        // Main content panel
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(37, 47, 63)); // Match dark background
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Professional business buttons
        String[] buttonTexts = {
            "Add New Client",
            "View Product Catalog", 
            "Show All Clients",
            "Outstanding Balances",
            "Record Payment",
            "Switch to Client Mode",
            "Logout"
        };

        Color[] buttonColors = {
            new Color(76, 175, 80),   // Material Green
            new Color(33, 150, 243),  // Material Blue
            new Color(103, 58, 183),  // Material Deep Purple
            new Color(255, 152, 0),   // Material Orange
            new Color(0, 150, 136),   // Material Teal
            new Color(158, 158, 158), // Material Gray
            new Color(244, 67, 54)    // Material Red
        };

        JButton[] buttons = new JButton[buttonTexts.length];
        for (int i = 0; i < buttonTexts.length; i++) {
            buttons[i] = createStyledButton(buttonTexts[i], buttonColors[i]);
            gbc.gridx = 0;
            gbc.gridy = i;
            contentPanel.add(buttons[i], gbc);
        }

        // Add action listeners
        buttons[0].addActionListener(e -> addClient());
        buttons[1].addActionListener(e -> displayAllProducts());
        buttons[2].addActionListener(e -> displayAllClients());
        buttons[3].addActionListener(e -> displayClientsWithOutstandingBalance());
        buttons[4].addActionListener(e -> recordPayment());
        buttons[5].addActionListener(e -> becomeClient());
        buttons[6].addActionListener(e -> parentFrame.handleEvent(Event.LOGOUT));

        panel.add(contentPanel, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setPreferredSize(new Dimension(300, 45));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Attractive border
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        
        // Enhanced hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.brighter());
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLoweredBevelBorder(),
                    BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createRaisedBevelBorder(),
                    BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.darker());
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.brighter());
            }
        });
        
        return button;
    }

    private void addClient() {
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.add(new JLabel("Client ID:"));
        JTextField idField = new JTextField();
        inputPanel.add(idField);
        
        inputPanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        inputPanel.add(nameField);
        
        inputPanel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        inputPanel.add(emailField);
        
        inputPanel.add(new JLabel("Phone:"));
        JTextField phoneField = new JTextField();
        inputPanel.add(phoneField);

        int result = JOptionPane.showConfirmDialog(parentFrame, inputPanel, "Add New Client", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();

            if (id.isEmpty() || name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(parentFrame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Client client = new Client(id, name, email, phone);
            context.getWarehouse().addClient(client);
            JOptionPane.showMessageDialog(parentFrame, "Client added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void displayAllClients() {
        List<Client> clients = context.getWarehouse().getClients();
        if (clients.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "There are no clients registered.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder clientsText = new StringBuilder("Registered Clients:\n\n");
        for (Client client : clients) {
            clientsText.append(String.format("%s | Balance: $%.2f\n", client.toString(), client.getBalance()));
        }

        JTextArea textArea = new JTextArea(clientsText.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        JOptionPane.showMessageDialog(parentFrame, scrollPane, "All Clients", JOptionPane.INFORMATION_MESSAGE);
    }

    private void displayAllProducts() {
        List<Product> products = context.getWarehouse().getProducts();
        if (products.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "There are no products in the catalog.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder productsText = new StringBuilder("Product Catalog:\n\n");
        for (Product product : products) {
            productsText.append(product.toString() + "\n");
        }

        JTextArea textArea = new JTextArea(productsText.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        JOptionPane.showMessageDialog(parentFrame, scrollPane, "Product Catalog", JOptionPane.INFORMATION_MESSAGE);
    }

    private void displayClientsWithOutstandingBalance() {
        List<Client> clients = context.getWarehouse().getClients();
        StringBuilder outstandingText = new StringBuilder();
        boolean found = false;

        for (Client client : clients) {
            if (client.getBalance() > 0.0) {
                if (!found) {
                    outstandingText.append("Clients with Outstanding Balance:\n\n");
                    found = true;
                }
                outstandingText.append(String.format("%s | Balance: $%.2f\n", client.toString(), client.getBalance()));
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(parentFrame, "No clients have outstanding balances.", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JTextArea textArea = new JTextArea(outstandingText.toString());
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 300));

            JOptionPane.showMessageDialog(parentFrame, scrollPane, "Outstanding Balances", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void recordPayment() {
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.add(new JLabel("Client ID:"));
        JTextField clientIdField = new JTextField();
        inputPanel.add(clientIdField);
        
        inputPanel.add(new JLabel("Payment Amount:"));
        JTextField amountField = new JTextField();
        inputPanel.add(amountField);

        int result = JOptionPane.showConfirmDialog(parentFrame, inputPanel, "Record Payment", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            String clientId = clientIdField.getText().trim();
            String amountText = amountField.getText().trim();

            if (clientId.isEmpty() || amountText.isEmpty()) {
                JOptionPane.showMessageDialog(parentFrame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Client client = context.findClient(clientId);
            if (client == null) {
                JOptionPane.showMessageDialog(parentFrame, "Client not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double amount = Double.parseDouble(amountText);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(parentFrame, "Payment amount must be positive.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                client.updateBalance(-amount);
                JOptionPane.showMessageDialog(parentFrame, 
                    String.format("Payment recorded. New balance: $%.2f", client.getBalance()), 
                    "Payment Successful", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parentFrame, "Invalid amount format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void becomeClient() {
        String clientId = JOptionPane.showInputDialog(parentFrame, "Enter client ID:", "Become Client", JOptionPane.QUESTION_MESSAGE);
        
        if (clientId != null && !clientId.trim().isEmpty()) {
            if (context.setActiveClient(clientId.trim())) {
                parentFrame.handleEvent(Event.PUSH_CLIENT);
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Client not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public Event run() {
        return Event.STAY;
    }

    @Override
    public String getName() {
        return "ClerkMenuStateGUI";
    }
}