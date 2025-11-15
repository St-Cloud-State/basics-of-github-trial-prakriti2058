import java.util.List;
import javax.swing.*;
import java.awt.*;


public class WishlistOperationsState implements State {
    private final WarehouseContext context;
    private JPanel panel;
    private WarehouseGUI parentFrame;

    public WishlistOperationsState(WarehouseContext context) {
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
        panel.setBackground(new Color(37, 47, 63)); // Dark professional background

        // Title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(138, 43, 226)); // Professional blue violet header
        titlePanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));
        
        Client client = context.getActiveClient();
        String title = "Wishlist Management - " + (client != null ? client.getName() : "Unknown Client");
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Product Selection | Wishlist Management | Order Processing", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(243, 229, 245));
        
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);
        panel.add(titlePanel, BorderLayout.NORTH);

        // Main content panel
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(37, 47, 63)); // Match dark background
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 15, 12, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Professional wishlist buttons
        String[] buttonTexts = {
            "Add Item to Wishlist",
            "Display Wishlist",
            "Remove Item from Wishlist",
            "Place Order (Purchase All)",
            "Return to Client Menu"
        };
        
        Color[] buttonColors = {
            new Color(76, 175, 80),   // Material Green
            new Color(33, 150, 243),  // Material Blue
            new Color(244, 67, 54),   // Material Red
            new Color(255, 152, 0),   // Material Orange
            new Color(158, 158, 158)  // Material Gray
        };

        JButton[] buttons = new JButton[buttonTexts.length];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = createStyledButton(buttonTexts[i], buttonColors[i]);
            gbc.gridx = 0;
            gbc.gridy = i;
            contentPanel.add(buttons[i], gbc);
        }

        // Add action listeners
        buttons[0].addActionListener(e -> addItemToWishlist());
        buttons[1].addActionListener(e -> displayWishlist());
        buttons[2].addActionListener(e -> removeItemFromWishlist());
        buttons[3].addActionListener(e -> placeOrder());
        buttons[4].addActionListener(e -> {
            if (parentFrame != null) {
                parentFrame.handleEvent(Event.RETURN);
            }
        });

        panel.add(contentPanel, BorderLayout.CENTER);
    }

    @Override
    public Event run() {
        // For GUI version, this method is called to display the panel
        // The actual event processing is handled by button listeners
        return Event.STAY;
    }

    private void addItemToWishlist() {
        Client client = context.getActiveClient();
        if (client == null) {
            JOptionPane.showMessageDialog(parentFrame, "No active client selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Show product catalog first
        List<Product> products = context.getWarehouse().getProducts();
        if (products.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "No products are currently available.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder catalogText = new StringBuilder("Available Products:\n\n");
        for (Product product : products) {
            catalogText.append(String.format("ID: %s | Name: %s | Price: $%.2f | Quantity: %d\n",
                    product.getId(), product.getName(), product.getPrice(), product.getQuantity()));
        }

        JTextArea catalogArea = new JTextArea(catalogText.toString());
        catalogArea.setEditable(false);
        catalogArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(catalogArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.add(new JLabel("Product ID:"));
        JTextField productIdField = new JTextField();
        inputPanel.add(productIdField);
        inputPanel.add(new JLabel("Quantity:"));
        JTextField quantityField = new JTextField();
        inputPanel.add(quantityField);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.NORTH);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        int result = JOptionPane.showConfirmDialog(parentFrame, mainPanel, "Add Item to Wishlist", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            String productId = productIdField.getText().trim();
            String quantityText = quantityField.getText().trim();

            if (productId.isEmpty() || quantityText.isEmpty()) {
                JOptionPane.showMessageDialog(parentFrame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityText);
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(parentFrame, "Quantity must be a positive number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Product product = context.getWarehouse().findProduct(productId);
                if (product == null) {
                    JOptionPane.showMessageDialog(parentFrame, "Product not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                client.addWishlistItem(product, quantity);
                JOptionPane.showMessageDialog(parentFrame, "Item added to wishlist successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parentFrame, "Invalid quantity format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void displayWishlist() {
        Client client = context.getActiveClient();
        if (client == null) {
            JOptionPane.showMessageDialog(parentFrame, "No active client selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<WishlistItem> wishlist = client.getWishlist();
        if (wishlist.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Wishlist is empty.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder wishlistText = new StringBuilder("Wishlist for " + client.getName() + ":\n\n");
        for (int i = 0; i < wishlist.size(); i++) {
            WishlistItem item = wishlist.get(i);
            wishlistText.append(String.format("%d. %s\n", i + 1, item.toString()));
        }

        JTextArea textArea = new JTextArea(wishlistText.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        JOptionPane.showMessageDialog(parentFrame, scrollPane, "Wishlist", JOptionPane.INFORMATION_MESSAGE);
    }

    private void removeItemFromWishlist() {
        Client client = context.getActiveClient();
        if (client == null) {
            JOptionPane.showMessageDialog(parentFrame, "No active client selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<WishlistItem> wishlist = client.getWishlist();
        if (wishlist.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Wishlist is empty.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] items = new String[wishlist.size()];
        for (int i = 0; i < wishlist.size(); i++) {
            items[i] = (i + 1) + ". " + wishlist.get(i).toString();
        }

        String selectedItem = (String) JOptionPane.showInputDialog(
                parentFrame,
                "Select item to remove:",
                "Remove Item from Wishlist",
                JOptionPane.QUESTION_MESSAGE,
                null,
                items,
                items[0]);

        if (selectedItem != null) {
            int index = Integer.parseInt(selectedItem.substring(0, selectedItem.indexOf("."))) - 1;
            wishlist.remove(index);
            JOptionPane.showMessageDialog(parentFrame, "Item removed from wishlist successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void placeOrder() {
        Client client = context.getActiveClient();
        if (client == null) {
            JOptionPane.showMessageDialog(parentFrame, "No active client selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<WishlistItem> wishlist = client.getWishlist();
        if (wishlist.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Wishlist is empty. Nothing to order.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                parentFrame,
                "Are you sure you want to place an order for all items in the wishlist?",
                "Confirm Order",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean fullyProcessed = context.getWarehouse().processOrder(client);
            if (fullyProcessed) {
                JOptionPane.showMessageDialog(parentFrame, "Order processed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Order partially processed. Some items may remain on the waitlist.", "Partial Success", JOptionPane.WARNING_MESSAGE);
            }
        }
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

    @Override
    public String getName() {
        return "WishlistOperationsState";
    }
}