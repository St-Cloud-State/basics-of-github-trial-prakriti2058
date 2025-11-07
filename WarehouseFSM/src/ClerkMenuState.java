import java.util.*;

public class ClerkMenuState implements FSMState {
    private static ClerkMenuState instance;
    private static final Warehouse warehouse = Warehouse.instance("WH1", "Main Location");
    private static final Scanner input = new Scanner(System.in);
    private static ClientData context;
    
    private ClerkMenuState() { }
    
    public static ClerkMenuState instance() {
        if (instance == null) {
            instance = new ClerkMenuState();
        }
        return instance;
    }
    
    public static void setContext(ClientData newContext) {
        context = newContext;
    }

    @Override
    public int run() {
        while (true) {
            System.out.println("\nClerk Menu");
            System.out.println("1. Add Client");
            System.out.println("2. Show Product List");
            System.out.println("3. Show Client List");
            System.out.println("4. Show Clients with Outstanding Balance");
            System.out.println("5. Record Payment");
            System.out.println("6. Become Client");
            System.out.println("7. Logout");
            System.out.print("Enter your choice: ");

            String choice = input.nextLine();
            
            switch (choice) {
                case "1": addClient(); break;
                case "2": showProducts(); break;
                case "3": showClients(); break;
                case "4": showOutstandingBalances(); break;
                case "5": recordPayment(); break;
                case "6": 
                    if (becomeClient()) {
                        return CLIENT_STATE;
                    }
                    break;
                case "7": return LOGIN_STATE;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private void addClient() {
        System.out.print("Enter client ID: ");
        String id = input.nextLine();
        System.out.print("Enter client name: ");
        String name = input.nextLine();
        System.out.print("Enter client email: ");
        String email = input.nextLine();
        System.out.print("Enter client phone: ");
        String phone = input.nextLine();
        
        Client client = new Client(id, name, email, phone);
        warehouse.addClient(client);
        System.out.println("Client added successfully!");
    }

    private void showProducts() {
        Iterator<Product> products = warehouse.getProducts();
        boolean hasProducts = false;
        
        while (products.hasNext()) {
            System.out.println(products.next());
            hasProducts = true;
        }
        
        if (!hasProducts) {
            System.out.println("No products available.");
        }
    }

    private void showClients() {
        Iterator<Client> clients = warehouse.getClients();
        boolean hasClients = false;
        
        while (clients.hasNext()) {
            Client client = clients.next();
            System.out.println(client + " Balance: $" + String.format("%.2f", client.getBalance()));
            hasClients = true;
        }
        
        if (!hasClients) {
            System.out.println("No clients in the system.");
        }
    }

    private void showOutstandingBalances() {
        Iterator<Client> clients = warehouse.getClients();
        boolean found = false;
        
        while (clients.hasNext()) {
            Client client = clients.next();
            if (client.getBalance() > 0) {
                System.out.println(client + " Balance: $" + String.format("%.2f", client.getBalance()));
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No clients with outstanding balances.");
        }
    }

    private void recordPayment() {
        System.out.print("Enter client ID: ");
        String clientId = input.nextLine();
        
        Client client = findClient(clientId);
        if (client == null) {
            System.out.println("Client not found.");
            return;
        }
        
        System.out.print("Enter payment amount: $");
        double amount = Double.parseDouble(input.nextLine());
        
        client.updateBalance(-amount);  // Negative amount reduces the balance
        System.out.println("Payment recorded successfully!");
    }

    private boolean becomeClient() {
        System.out.print("Enter client ID: ");
        String clientId = input.nextLine();
        
        Client client = findClient(clientId);
        if (client != null) {
            context.setClientId(clientId);
            context.setClerkLogin(true);
            return true;
        } else {
            System.out.println("Client not found.");
            return false;
        }
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

    @Override
    public void enter() {
        // Nothing specific needed when entering clerk state
    }

    @Override
    public void exit() {
        // Nothing specific needed when exiting clerk state
    }

    @Override
    public String getStateName() {
        return "Clerk Menu State";
    }
}