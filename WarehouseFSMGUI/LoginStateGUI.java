import javax.swing.*;
import java.awt.*;


public class LoginStateGUI implements State {
    private final WarehouseContext context;
    private JPanel panel;
    private WarehouseGUI parentFrame;

    public LoginStateGUI(WarehouseContext context) {
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
        panel.setBackground(new Color(37, 47, 63)); // Attractive dark blue-gray background

        // Title panel with gradient effect
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(25, 35, 51)); // Darker header
        titlePanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 30, 0));
        
        JLabel titleLabel = new JLabel("Warehouse Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(255, 255, 255)); // Pure white for visibility
        
        JLabel subtitleLabel = new JLabel("Enterprise Logistics Solution", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(189, 195, 199)); // Light gray
        
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);
        panel.add(titlePanel, BorderLayout.NORTH);

        // Login options panel
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(37, 47, 63)); // Match main background
        loginPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 30, 20, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Welcome message with proper contrast
        JLabel welcomeLabel = new JLabel("Please select your access level:", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        welcomeLabel.setForeground(new Color(255, 255, 255)); // White text on dark background
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(welcomeLabel, gbc);

        // Attractive professional login buttons
        JButton managerButton = createStyledButton("Manager Access", new Color(142, 68, 173)); // Purple
        JButton clerkButton = createStyledButton("Clerk Access", new Color(52, 152, 219)); // Blue
        JButton clientButton = createStyledButton("Client Access", new Color(46, 204, 113)); // Green
        JButton exitButton = createStyledButton("Exit System", new Color(231, 76, 60)); // Red

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        loginPanel.add(managerButton, gbc);
        gbc.gridy = 2;
        loginPanel.add(clerkButton, gbc);
        gbc.gridy = 3;
        loginPanel.add(clientButton, gbc);
        gbc.gridy = 4;
        loginPanel.add(exitButton, gbc);

        // Add action listeners
        managerButton.addActionListener(e -> parentFrame.handleEvent(Event.TO_MANAGER));
        clerkButton.addActionListener(e -> parentFrame.handleEvent(Event.TO_CLERK));
        clientButton.addActionListener(e -> handleClientLogin());
        exitButton.addActionListener(e -> parentFrame.handleEvent(Event.EXIT));

        panel.add(loginPanel, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(25, 35, 51)); // Dark footer to match header
        footerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JLabel footerLabel = new JLabel("© 2025 Enterprise Warehouse Management - Version 2.0", JLabel.CENTER);
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        footerLabel.setForeground(new Color(149, 165, 166)); // Light gray text on dark
        footerPanel.add(footerLabel, BorderLayout.CENTER);
        
        panel.add(footerPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(250, 50));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Attractive rounded-style border
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(12, 25, 12, 25)
        ));
        
        // Professional hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // Subtle gold highlight on hover
                button.setBackground(new Color(255, 215, 0)); // Gold
                button.setForeground(Color.BLACK);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 193, 7), 2),
                    BorderFactory.createEmptyBorder(12, 25, 12, 25)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
                button.setForeground(Color.WHITE);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createRaisedBevelBorder(),
                    BorderFactory.createEmptyBorder(12, 25, 12, 25)
                ));
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(218, 165, 32)); // Dark goldenrod
                button.setForeground(Color.WHITE);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 215, 0)); // Return to gold
                button.setForeground(Color.BLACK);
            }
        });
        
        return button;
    }

    private void handleClientLogin() {
        // Create a dialog for client ID input
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.add(new JLabel("Client ID:"));
        JTextField clientIdField = new JTextField(15);
        inputPanel.add(clientIdField);

        int result = JOptionPane.showConfirmDialog(
            parentFrame,
            inputPanel,
            "Client Login",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String clientId = clientIdField.getText().trim();
            if (!clientId.isEmpty()) {
                if (context.setActiveClient(clientId)) {
                    parentFrame.handleEvent(Event.PUSH_CLIENT);
                } else {
                    JOptionPane.showMessageDialog(
                        parentFrame,
                        "Client not found. Please check the client identifier.",
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(
                    parentFrame,
                    "Please enter a client ID.",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        }
    }

    @Override
    public Event run() {
        // For GUI version, this method is not used for main logic
        // The actual event processing is handled by button listeners
        return Event.STAY;
    }

    @Override
    public String getName() {
        return "LoginStateGUI";
    }
}