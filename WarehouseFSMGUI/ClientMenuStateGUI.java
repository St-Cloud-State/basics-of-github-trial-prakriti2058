import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ClientMenuStateGUI implements State {
    private final WarehouseContext context;
    private JPanel panel;
    private WarehouseGUI parentFrame;

    public ClientMenuStateGUI(WarehouseContext context) {
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
        titlePanel.setBackground(new Color(30, 144, 255)); // Professional dodger blue header
        titlePanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));
        
        Client client = context.getActiveClient();
        String title = "Client Portal - " + (client != null ? client.getName() : "Unknown Client");
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Product Catalog | Account Management | Order Processing", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(237, 247, 237));
        
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

        // Professional client buttons
        String[] buttonTexts = {
            "View Account Details",
            "Browse Product Catalog", 
            "Transaction History",
            "Wishlist Management",
            "Logout"
        };

        Color[] buttonColors = {
            new Color(33, 150, 243),  // Material Blue
            new Color(103, 58, 183),  // Material Deep Purple
            new Color(255, 152, 0),   // Material Orange
            new Color(156, 39, 176),  // Material Purple
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
        buttons[0].addActionListener(e -> showClientDetails());
        buttons[1].addActionListener(e -> showProductCatalog());
        buttons[2].addActionListener(e -> showClientTransactions());
        buttons[3].addActionListener(e -> parentFrame.handleEvent(Event.TO_WISHLIST));
        buttons[4].addActionListener(e -> parentFrame.handleEvent(Event.RETURN));

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

    private void showClientDetails() {
        Client client = context.getActiveClient();
        if (client == null) {
            JOptionPane.showMessageDialog(parentFrame, "No active client selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder detailsText = new StringBuilder("Client Details:\n\n");
        detailsText.append(client.toString()).append("\n");
        detailsText.append(String.format("Balance: $%.2f", client.getBalance()));

        JTextArea textArea = new JTextArea(detailsText.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 150));

        JOptionPane.showMessageDialog(parentFrame, scrollPane, "Client Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showProductCatalog() {
        List<Product> products = context.getWarehouse().getProducts();
        if (products.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "No products are currently available.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder catalogText = new StringBuilder("Product Catalog:\n\n");
        for (Product product : products) {
            catalogText.append(product.toString()).append("\n");
        }

        JTextArea textArea = new JTextArea(catalogText.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        JOptionPane.showMessageDialog(parentFrame, scrollPane, "Product Catalog", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showClientTransactions() {
        Client client = context.getActiveClient();
        if (client == null) {
            JOptionPane.showMessageDialog(parentFrame, "No active client selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String> invoices = context.getWarehouse().getInvoices(client.getId());
        if (invoices.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "No transactions found for this client.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder transactionsText = new StringBuilder("Transaction History:\n\n");
        for (String invoice : invoices) {
            transactionsText.append(invoice).append("\n\n");
        }

        JTextArea textArea = new JTextArea(transactionsText.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        JOptionPane.showMessageDialog(parentFrame, scrollPane, "Transaction History", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public Event run() {
        return Event.STAY;
    }

    @Override
    public String getName() {
        return "ClientMenuStateGUI";
    }
}