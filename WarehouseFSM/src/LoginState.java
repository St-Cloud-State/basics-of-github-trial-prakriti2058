import java.util.*;

public class LoginState implements FSMState {
    private static LoginState instance;
    private static final Warehouse warehouse = Warehouse.instance("WH1", "Main Location");
    private static final Scanner input = new Scanner(System.in);
    private static ClientData context;

    private LoginState() { }
    
    public static LoginState instance() {
        if (instance == null) {
            instance = new LoginState();
        }
        return instance;
    }
    
    public static void setContext(ClientData newContext) {
        context = newContext;
    }

    @Override
    public int run() {
        while (true) {
            System.out.println("\nWelcome to Warehouse Management System");
            System.out.println("1. Login as Client");
            System.out.println("2. Login as Clerk");
            System.out.println("3. Login as Manager");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            String choice = input.nextLine();
            
            switch (choice) {
                case "1":
                    if (loginAsClient()) {
                        return CLIENT_STATE;
                    }
                    break;
                case "2": 
                    return CLERK_STATE;
                case "3":
                    return MANAGER_STATE;
                case "0":
                    return EXIT;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private boolean loginAsClient() {
        System.out.print("Enter client ID: ");
        String clientId = input.nextLine();
        
        Client client = findClient(clientId);
        if (client != null) {
            context.setClientId(clientId);
            context.setClerkLogin(false);
            return true;
        }
        
        System.out.println("Client not found!");
        return false;
    }

    @Override
    public void enter() {
        context.clear();
    }

    @Override
    public void exit() {
        // Nothing specific needed when exiting login state
    }

    @Override
    public String getStateName() {
        return "Login State";
    }

    private Client findClient(String clientId) {
        Iterator<Client> clients = warehouse.getClients();
        while (clients.hasNext()) {
            Client client = clients.next();
            if (client.getId().equals(clientId)) {
                return client;
            }
        }
        return null;
    }
}