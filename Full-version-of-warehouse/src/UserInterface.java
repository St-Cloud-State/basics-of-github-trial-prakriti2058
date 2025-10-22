import java.util.*;
import java.io.*;

public class UserInterface {
    private static Warehouse warehouse;
    private static Scanner input;
    
    public UserInterface() {
        warehouse = Warehouse.instance("WH1", "Main Location");
        input = new Scanner(System.in);
    }
    
    private Client findClient(String clientId) {
        List<Client> clients = warehouse.getClients();
        for (Client client : clients) {
            if (client.getId().equals(clientId)) {
                return client;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.run();
    }

    public void run() {
        String choice = "";
        while (true) {
            System.out.println("\nWarehouse Management System");
            System.out.println("1. Add Client");
            System.out.println("2. Display All Clients");
            System.out.println("3. Add Product");
            System.out.println("4. Display All Products");
            System.out.println("5. Add to Wishlist");
            System.out.println("6. Display Client's Wishlist");
            System.out.println("7. Process Order");
            System.out.println("8. Accept Payment");
            System.out.println("9. Receive Shipment");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            
            choice = input.nextLine();
            
            switch (choice) {
                case "1": addClient(); break;
                case "2": displayAllClients(); break;
                case "3": addProduct(); break;
                case "4": displayAllProducts(); break;
                case "5": addToWishlist(); break;
                case "6": displayWishlist(); break;
                case "7": processOrder(); break;
                case "8": acceptPayment(); break;
                case "9": receiveShipment(); break;
                case "0": System.out.println("Goodbye!"); return;
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

    private void displayAllClients() {
        List<Client> clients = warehouse.getClients();
        if (clients.isEmpty()) {
            System.out.println("No clients in the system.");
            return;
        }
        
        for (Client client : clients) {
            System.out.println(client + " Balance: $" + String.format("%.2f", client.getBalance()));
        }
    }

    private void addProduct() {
        System.out.print("Enter product ID: ");
        String id = input.nextLine();
        System.out.print("Enter product name: ");
        String name = input.nextLine();
        System.out.print("Enter product price: ");
        double price = Double.parseDouble(input.nextLine());
        System.out.print("Enter product quantity: ");
        int quantity = Integer.parseInt(input.nextLine());
        
        Product product = new Product(id, name, price, quantity);
        warehouse.addProduct(product);
        System.out.println("Product added successfully!");
    }

    private void displayAllProducts() {
        List<Product> products = warehouse.getProducts();
        if (products.isEmpty()) {
            System.out.println("No products in the system.");
            return;
        }
        
        for (Product product : products) {
            System.out.println(product);
        }
    }

    private void addToWishlist() {
        System.out.print("Enter client ID: ");
        String clientId = input.nextLine();
        System.out.print("Enter product ID: ");
        String productId = input.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = Integer.parseInt(input.nextLine());
        
        Client client = findClient(clientId);
        Product product = warehouse.findProduct(productId);
        
        if (client != null && product != null) {
            client.addWishlistItem(new WishlistItem(product, quantity));
            System.out.println("Added to wishlist successfully!");
        } else {
            System.out.println("Could not add to wishlist. Check client and product IDs.");
        }
    }

    private void displayWishlist() {
        System.out.print("Enter client ID: ");
        String clientId = input.nextLine();
        
        Client client = findClient(clientId);
        if (client == null) {
            System.out.println("Client not found.");
            return;
        }
        
        List<WishlistItem> wishlist = client.getWishlist();
        if (wishlist.isEmpty()) {
            System.out.println("No items in wishlist.");
            return;
        }
        
        System.out.println("Wishlist for client " + client.getName() + ":");
        for (WishlistItem item : wishlist) {
            System.out.println(item);
        }
    }

    private void processOrder() {
        System.out.print("Enter client ID: ");
        String clientId = input.nextLine();
        
        Client client = findClient(clientId);
        if (client == null) {
            System.out.println("Client not found.");
            return;
        }
        
        boolean fullyProcessed = warehouse.processOrder(client);
        if (fullyProcessed) {
            System.out.println("Order fully processed!");
        } else {
            System.out.println("Order partially processed. Some items remained in wishlist due to insufficient stock.");
        }
    }

    private void acceptPayment() {
        System.out.print("Enter client ID: ");
        String clientId = input.nextLine();
        System.out.print("Enter payment amount: ");
        double amount = Double.parseDouble(input.nextLine());
        
        Client client = findClient(clientId);
        if (client == null) {
            System.out.println("Client not found.");
            return;
        }
        
        client.updateBalance(-amount);  // Negative amount reduces the balance
        System.out.println("Payment accepted successfully!");
    }

    private void receiveShipment() {
        System.out.print("Enter product ID: ");
        String productId = input.nextLine();
        System.out.print("Enter quantity received: ");
        int quantity = Integer.parseInt(input.nextLine());
        
        warehouse.addProductQuantity(productId, quantity);
        System.out.println("Shipment recorded successfully!");
    }
}