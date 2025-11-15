import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.EnumMap;
import java.util.Map;
import java.util.ArrayDeque;
import java.util.Deque;

public class WarehouseGUI extends JFrame {
    private final WarehouseContext context;
    private final Map<StateId, State> states = new EnumMap<>(StateId.class);
    private final Deque<StateId> stateHistory = new ArrayDeque<>();
    
    // Transition matrix for GUI version
    private final StateId[][] transitionMatrix = {
        // Events: STAY,       TO_MANAGER,    TO_CLERK,      PUSH_CLIENT,  TO_WISHLIST,  LOGOUT,      RETURN,      EXIT
        {StateId.LOGIN, StateId.MANAGER, StateId.CLERK, StateId.CLIENT, null,         StateId.LOGIN, StateId.LOGIN, StateId.TERMINAL}, // LOGIN
        {StateId.MANAGER, StateId.MANAGER, StateId.CLERK, null,          null,         StateId.LOGIN, StateId.LOGIN, StateId.TERMINAL}, // MANAGER
        {StateId.CLERK, StateId.MANAGER, StateId.CLERK, StateId.CLIENT, null,         StateId.LOGIN, StateId.LOGIN, StateId.TERMINAL}, // CLERK
        {StateId.CLIENT, StateId.CLIENT,  null,          null,          StateId.WISHLIST, StateId.LOGIN, StateId.LOGIN, StateId.TERMINAL}, // CLIENT
        {StateId.WISHLIST, StateId.WISHLIST, null,       null,          StateId.WISHLIST, StateId.LOGIN, StateId.CLIENT, StateId.TERMINAL}, // WISHLIST
        {StateId.TERMINAL, StateId.TERMINAL, StateId.TERMINAL, StateId.TERMINAL, StateId.TERMINAL, StateId.TERMINAL, StateId.TERMINAL, StateId.TERMINAL} // TERMINAL
    };

    private StateId currentStateId;
    private JPanel contentPanel;


    public WarehouseGUI() {
        this.context = new WarehouseContext();
        initializeGUI();
        initializeStates();
        this.currentStateId = StateId.LOGIN;
        showCurrentState();
    }

    private void initializeGUI() {
        setTitle("Warehouse Management System - GUI");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Add window listener for proper cleanup
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(
                    WarehouseGUI.this,
                    "Are you sure you want to exit?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION
                );
                if (option == JOptionPane.YES_OPTION) {
                    handleEvent(Event.EXIT);
                }
            }
        });

        // Main content panel with CardLayout for state switching
        contentPanel = new JPanel(new CardLayout());
        add(contentPanel, BorderLayout.CENTER);

        // Status bar
        JLabel statusBar = new JLabel("Welcome to Warehouse Management System", JLabel.LEFT);
        statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
        add(statusBar, BorderLayout.SOUTH);
    }

    private void initializeStates() {
        // Create GUI versions of all states
        LoginStateGUI loginState = new LoginStateGUI(context);
        ClerkMenuStateGUI clerkState = new ClerkMenuStateGUI(context);
        ManagerMenuStateGUI managerState = new ManagerMenuStateGUI(context);
        ClientMenuStateGUI clientState = new ClientMenuStateGUI(context);
        WishlistOperationsState wishlistState = new WishlistOperationsState(context);

        // Set parent frame for all states
        loginState.setParentFrame(this);
        clerkState.setParentFrame(this);
        managerState.setParentFrame(this);
        clientState.setParentFrame(this);
        wishlistState.setParentFrame(this);

        // Add states to maps
        states.put(StateId.LOGIN, loginState);
        states.put(StateId.CLERK, clerkState);
        states.put(StateId.MANAGER, managerState);
        states.put(StateId.CLIENT, clientState);
        states.put(StateId.WISHLIST, wishlistState);

        // Add panels to content panel
        contentPanel.add(loginState.getPanel(), StateId.LOGIN.name());
        contentPanel.add(clerkState.getPanel(), StateId.CLERK.name());
        contentPanel.add(managerState.getPanel(), StateId.MANAGER.name());
        contentPanel.add(clientState.getPanel(), StateId.CLIENT.name());
        contentPanel.add(wishlistState.getPanel(), StateId.WISHLIST.name());
    }

    public void handleEvent(Event event) {
        if (event == null) {
            event = Event.STAY;
        }

        switch (event) {
            case EXIT:
                dispose();
                System.exit(0);
                return;
            case PUSH_CLIENT:
                pushCurrentState();
                transitionTo(event);
                return;
            case RETURN:
                StateId previous = popPreviousState();
                if (previous != null) {
                    currentStateId = previous;
                } else {
                    transitionTo(event);
                }
                context.clearActiveClient();
                showCurrentState();
                return;
            case STAY:
                // No state change needed
                return;
            default:
                transitionTo(event);
        }
    }

    private void transitionTo(Event event) {
        int eventIndex = event.ordinal();
        StateId next = transitionMatrix[currentStateId.ordinal()][eventIndex];
        if (next == null) {
            JOptionPane.showMessageDialog(this, 
                "Invalid operation for current state.", 
                "Operation Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        currentStateId = next;
        if (currentStateId == StateId.LOGIN) {
            context.clearActiveClient();
        }
        showCurrentState();
    }

    private void showCurrentState() {
        if (currentStateId == StateId.TERMINAL) {
            dispose();
            return;
        }

        CardLayout layout = (CardLayout) contentPanel.getLayout();
        layout.show(contentPanel, currentStateId.name());
        
        // Update title based on current state
        String stateTitle = getCurrentStateTitle();
        setTitle("Warehouse Management System - " + stateTitle);
    }

    private String getCurrentStateTitle() {
        switch (currentStateId) {
            case LOGIN: return "Login";
            case MANAGER: return "Manager Menu";
            case CLERK: return "Clerk Menu";
            case CLIENT: return "Client Menu";
            case WISHLIST: return "Wishlist Operations";
            default: return "Unknown State";
        }
    }

    private void pushCurrentState() {
        stateHistory.push(currentStateId);
    }

    private StateId popPreviousState() {
        return stateHistory.isEmpty() ? null : stateHistory.pop();
    }

    public WarehouseContext getContext() {
        return context;
    }

    public static void main(String[] args) {
        // Use system default look and feel
        /*
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

        SwingUtilities.invokeLater(() -> {
            new WarehouseGUI().setVisible(true);
        });
    }
}