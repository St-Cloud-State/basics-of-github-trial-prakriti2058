import java.util.*;

public class ClientMenuState implements FSMState {
    private static ClientMenuState instance;
    private static final Warehouse warehouse = Warehouse.instance("WH1", "Main Location");
    private static final Scanner input = new Scanner(System.in);
    private static ClientData context;
    
    private ClientMenuState() { }
    
    public static ClientMenuState instance() {
        if (instance == null) {
            instance = new ClientMenuState();
        }
        return instance;
    }
    
    public static void setContext(ClientData newContext) {
        context = newContext;
    }

    @Override
    public int run() {
        while (true) {
            System.out.println("\nClient Menu - " + getClientName());
            System.out.println("1. Show My Details");
            System.out.println("2. Show Product List");
            System.out.println("3. Show My Transactions");
            System.out.println("4. Add Item to Wishlist");
            System.out.println("5. Display My Wishlist");
            System.out.println("6. Place Order");
            System.out.println("7. Logout");
            System.out.print("Enter your choice: ");

            String choice = input.nextLine();
            
            switch (choice) {
                case "1": showClientDetails(); break;
                case "2": showProducts(); break;
                case "3": showTransactions(); break;
                case "4": addToWishlist(); break;
                case "5": showWishlist(); break;
                case "6": placeOrder(); break;
                case "7": return context.isClerkLogin() ? CLERK_STATE : LOGIN_STATE;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private void showClientDetails() {
        Client client = findClient();
        if (client != null) {
            System.out.println(client + " Balance: $" + String.format("%.2f", client.getBalance()));
        }
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

    private void showTransactions() {
        Iterator<String> invoices = warehouse.getInvoices(context.getClientId());
        boolean hasInvoices = false;
        
        System.out.println("Transaction History:");
        while (invoices.hasNext()) {
            System.out.println(invoices.next());
            hasInvoices = true;
        }
        
        if (!hasInvoices) {
            System.out.println("No transactions found.");
        }
    }

    private void addToWishlist() {
        showProducts();
        System.out.print("Enter product ID: ");
        String productId = input.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = Integer.parseInt(input.nextLine());
        
        Client client = findClient();
        Product product = warehouse.findProduct(productId);
        
        if (client != null && product != null) {
            client.addWishlistItem(new WishlistItem(product, quantity));
            System.out.println("Added to wishlist successfully!");
        } else {
            System.out.println("Could not add to wishlist. Check product ID.");
        }
    }

    private void showWishlist() {
        Client client = findClient();
        if (client == null) return;
        
        Iterator<WishlistItem> wishlist = client.getWishlist();
        boolean hasItems = false;
        
        System.out.println("Your Wishlist:");
        while (wishlist.hasNext()) {
            System.out.println(wishlist.next());
            hasItems = true;
        }
        
        if (!hasItems) {
            System.out.println("Your wishlist is empty.");
        }
    }

    private void placeOrder() {
        Client client = findClient();
        if (client == null) return;
        
        boolean fullyProcessed = warehouse.processOrder(client);
        if (fullyProcessed) {
            System.out.println("Order fully processed!");
        } else {
            System.out.println("Order partially processed. Some items remained unfulfilled.");
        }
    }

    private Client findClient() {
        Iterator<Client> clients = warehouse.getClients();
        while (clients.hasNext()) {
            Client client = clients.next();
            if (client.getId().equals(context.getClientId())) {
                return client;
            }
        }
        System.out.println("Client not found.");
        return null;
    }

    private String getClientName() {
        Client client = findClient();
        return client != null ? client.getName() : "Unknown Client";
    }

    @Override
    public void enter() {
        if (context.getClientId() == null) {
            System.out.println("Error: No client ID set!");
        }
    }

    @Override
    public void exit() {
        if (!context.isClerkLogin()) {
            context.clear();
        }
    }

    @Override
    public String getStateName() {
        return "Client Menu State";
    }
}