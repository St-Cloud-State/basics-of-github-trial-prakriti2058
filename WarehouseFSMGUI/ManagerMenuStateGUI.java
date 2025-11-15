import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ManagerMenuStateGUI implements State {
    private final WarehouseContext context;
    private JPanel panel;
    private WarehouseGUI parentFrame;

    public ManagerMenuStateGUI(WarehouseContext context) {
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
        titlePanel.setBackground(new Color(220, 20, 60)); // Professional crimson red header
        titlePanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));
        
        JLabel titleLabel = new JLabel("Manager Control Center", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Inventory Management | Product Operations | System Administration", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(245, 240, 250));
        
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

        // Professional manager buttons
        String[] buttonTexts = {
            "Add New Product",
            "Display Product Waitlist", 
            "Receive Shipment",
            "Switch to Clerk Mode",
            "Logout"
        };

        Color[] buttonColors = {
            new Color(76, 175, 80),   // Material Green
            new Color(33, 150, 243),  // Material Blue
            new Color(103, 58, 183),  // Material Deep Purple
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
        buttons[0].addActionListener(e -> addProduct());
        buttons[1].addActionListener(e -> displayProductWaitlist());
        buttons[2].addActionListener(e -> receiveShipment());
        buttons[3].addActionListener(e -> parentFrame.handleEvent(Event.TO_CLERK));
        buttons[4].addActionListener(e -> parentFrame.handleEvent(Event.LOGOUT));

        panel.add(contentPanel, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(280, 40));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Professional border
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(backgroundColor.darker(), 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        
        // Professional hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.darker());
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(backgroundColor.darker().darker(), 2),
                    BorderFactory.createEmptyBorder(8, 15, 8, 15)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(backgroundColor.darker(), 1),
                    BorderFactory.createEmptyBorder(8, 15, 8, 15)
                ));
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.darker().darker());
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.darker());
            }
        });
        
        return button;
    }

    // Helper method to create custom dialogs without icons
    private int showCustomDialog(JPanel content, String title) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parentFrame), title, true);
        dialog.setLayout(new BorderLayout());
        
        // Content panel with padding
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.add(content, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        
        // Style buttons
        okButton.setBackground(new Color(76, 175, 80));
        okButton.setForeground(Color.WHITE);
        okButton.setFocusPainted(false);
        
        cancelButton.setBackground(new Color(244, 67, 54));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        
        final int[] result = {JOptionPane.CANCEL_OPTION};
        
        okButton.addActionListener(e -> {
            result[0] = JOptionPane.OK_OPTION;
            dialog.dispose();
        });
        
        cancelButton.addActionListener(e -> {
            result[0] = JOptionPane.CANCEL_OPTION;
            dialog.dispose();
        });
        
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
        
        return result[0];
    }

    // Helper method to show messages without icons
    private void showCustomMessage(String message, String title, boolean isError) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parentFrame), title, true);
        dialog.setLayout(new BorderLayout());
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(messageLabel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton okButton = new JButton("OK");
        okButton.setBackground(isError ? new Color(244, 67, 54) : new Color(76, 175, 80));
        okButton.setForeground(Color.WHITE);
        okButton.setFocusPainted(false);
        okButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(okButton);
        
        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }

    private void addProduct() {
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.add(new JLabel("Product ID:"));
        JTextField idField = new JTextField();
        inputPanel.add(idField);
        
        inputPanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        inputPanel.add(nameField);
        
        inputPanel.add(new JLabel("Price:"));
        JTextField priceField = new JTextField();
        inputPanel.add(priceField);
        
        inputPanel.add(new JLabel("Quantity:"));
        JTextField quantityField = new JTextField();
        inputPanel.add(quantityField);

        int result = showCustomDialog(inputPanel, "Add New Product");
        
        if (result == JOptionPane.OK_OPTION) {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String priceText = priceField.getText().trim();
            String quantityText = quantityField.getText().trim();

            if (id.isEmpty() || name.isEmpty() || priceText.isEmpty() || quantityText.isEmpty()) {
                showCustomMessage("Please fill in all fields.", "Error", true);
                return;
            }

            try {
                double price = Double.parseDouble(priceText);
                int quantity = Integer.parseInt(quantityText);
                
                if (price <= 0) {
                    showCustomMessage("Price must be positive.", "Error", true);
                    return;
                }
                
                if (quantity < 0) {
                    showCustomMessage("Quantity cannot be negative.", "Error", true);
                    return;
                }

                Product product = new Product(id, name, price, quantity);
                context.getWarehouse().addProduct(product);
                showCustomMessage("Product added successfully!", "Success", false);
            } catch (NumberFormatException e) {
                showCustomMessage("Invalid price or quantity format.", "Error", true);
            }
        }
    }

    // Helper method for custom input dialog without icons
    private String showCustomInput(String message, String title) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parentFrame), title, true);
        dialog.setLayout(new BorderLayout());
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField inputField = new JTextField(15);
        
        contentPanel.add(messageLabel, BorderLayout.NORTH);
        contentPanel.add(Box.createVerticalStrut(10), BorderLayout.CENTER);
        contentPanel.add(inputField, BorderLayout.SOUTH);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        
        okButton.setBackground(new Color(76, 175, 80));
        okButton.setForeground(Color.WHITE);
        okButton.setFocusPainted(false);
        
        cancelButton.setBackground(new Color(244, 67, 54));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        
        final String[] result = {null};
        
        okButton.addActionListener(e -> {
            result[0] = inputField.getText().trim();
            dialog.dispose();
        });
        
        cancelButton.addActionListener(e -> {
            result[0] = null;
            dialog.dispose();
        });
        
        inputField.addActionListener(e -> {
            result[0] = inputField.getText().trim();
            dialog.dispose();
        });
        
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setSize(350, 150);
        dialog.setLocationRelativeTo(parentFrame);
        inputField.requestFocus();
        dialog.setVisible(true);
        
        return result[0];
    }

    private void displayProductWaitlist() {
        String productId = showCustomInput("Enter product ID:", "Display Waitlist");
        
        if (productId != null && !productId.trim().isEmpty()) {
            Product product = context.getWarehouse().findProduct(productId.trim());
            if (product == null) {
                showCustomMessage("Product not found.", "Error", true);
                return;
            }

            List<WaitlistItem> waitlist = product.getWaitlist();
            if (waitlist.isEmpty()) {
                showCustomMessage("No waitlist entries for this product.", "Information", false);
                return;
            }

            StringBuilder waitlistText = new StringBuilder("Waitlist for product " + product.getName() + ":\n\n");
            for (int i = 0; i < waitlist.size(); i++) {
                WaitlistItem item = waitlist.get(i);
                waitlistText.append(String.format("%d. %s\n", i + 1, item.toString()));
            }

            JTextArea textArea = new JTextArea(waitlistText.toString());
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 300));

            showCustomContent(scrollPane, "Product Waitlist");
        }
    }

    // Helper method to display content in a custom dialog without icons
    private void showCustomContent(JComponent content, String title) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parentFrame), title, true);
        dialog.setLayout(new BorderLayout());
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        contentPanel.add(content, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton closeButton = new JButton("Close");
        closeButton.setBackground(new Color(76, 175, 80));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(closeButton);
        
        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }

    private void receiveShipment() {
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.add(new JLabel("Product ID:"));
        JTextField productIdField = new JTextField();
        inputPanel.add(productIdField);
        
        inputPanel.add(new JLabel("Quantity Received:"));
        JTextField quantityField = new JTextField();
        inputPanel.add(quantityField);

        int result = showCustomDialog(inputPanel, "Receive Shipment");
        
        if (result == JOptionPane.OK_OPTION) {
            String productId = productIdField.getText().trim();
            String quantityText = quantityField.getText().trim();

            if (productId.isEmpty() || quantityText.isEmpty()) {
                showCustomMessage("Please fill in all fields.", "Error", true);
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityText);
                if (quantity <= 0) {
                    showCustomMessage("Quantity must be positive.", "Error", true);
                    return;
                }

                context.getWarehouse().addProductQuantity(productId, quantity);
                showCustomMessage("Shipment processed successfully!", "Success", false);
            } catch (NumberFormatException e) {
                showCustomMessage("Invalid quantity format.", "Error", true);
            }
        }
    }

    @Override
    public Event run() {
        return Event.STAY;
    }

    @Override
    public String getName() {
        return "ManagerMenuStateGUI";
    }
}