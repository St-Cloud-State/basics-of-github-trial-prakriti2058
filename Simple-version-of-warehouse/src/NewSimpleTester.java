import java.util.*;

public class NewSimpleTester {
    public static void main(String[] args) {
        // Initialize warehouse
        Warehouse warehouse = Warehouse.instance("W1", "Main Location");
        
        // Create five clients: C1 through C5
        Client c1 = new Client("C1", "Client 1", "c1@email.com", "111-111-1111");
        Client c2 = new Client("C2", "Client 2", "c2@email.com", "222-222-2222");
        Client c3 = new Client("C3", "Client 3", "c3@email.com", "333-333-3333");
        Client c4 = new Client("C4", "Client 4", "c4@email.com", "444-444-4444");
        Client c5 = new Client("C5", "Client 5", "c5@email.com", "555-555-5555");

        warehouse.addClient(c1);
        warehouse.addClient(c2);
        warehouse.addClient(c3);
        warehouse.addClient(c4);
        warehouse.addClient(c5);

        // Print all clients
        System.out.println("All Clients (Initial):");
        for (Client c : warehouse.getClients()) {
            System.out.println(c + " Balance: $" + c.getBalance());
        }

        // Create five products: P1 through P5
        Product p1 = new Product("P1", "Product 1", 1.0, 10);
        Product p2 = new Product("P2", "Product 2", 2.0, 20);
        Product p3 = new Product("P3", "Product 3", 3.0, 30);
        Product p4 = new Product("P4", "Product 4", 4.0, 40);
        Product p5 = new Product("P5", "Product 5", 5.0, 50);

        warehouse.addProduct(p1);
        warehouse.addProduct(p2);
        warehouse.addProduct(p3);
        warehouse.addProduct(p4);
        warehouse.addProduct(p5);

        // Print all products
        System.out.println("\nAll Products:");
        for (Product p : warehouse.getProducts()) {
            System.out.println(p);
        }

        // Add to C1's wishlist: 5 each of P1, P3 and P5
        c1.addWishlistItem(p1, 5);
        c1.addWishlistItem(p3, 5);
        c1.addWishlistItem(p5, 5);

        // Print C1's wishlist
        System.out.println("\nC1's Wishlist:");
        for (WishlistItem item : c1.getWishlist()) {
            System.out.println(item);
        }

        // Add to C2's wishlist: 7 each of P1, P2 and P4
        c2.addWishlistItem(p1, 7);
        c2.addWishlistItem(p2, 7);
        c2.addWishlistItem(p4, 7);

        // Print C2's wishlist
        System.out.println("\nC2's Wishlist:");
        for (WishlistItem item : c2.getWishlist()) {
            System.out.println(item);
        }

        // Add to C3's wishlist: 6 each of P1, P2 and P5
        c3.addWishlistItem(p1, 6);
        c3.addWishlistItem(p2, 6);
        c3.addWishlistItem(p5, 6);

        // Print C3's wishlist
        System.out.println("\nC3's Wishlist:");
        for (WishlistItem item : c3.getWishlist()) {
            System.out.println(item);
        }

        // Process C2's order
        System.out.println("\nProcessing C2's order...");
        warehouse.processOrder(c2);

        // Print all clients to show updated balances
        System.out.println("\nAll Clients (After C2's order):");
        for (Client c : warehouse.getClients()) {
            System.out.println(c + " Balance: $" + c.getBalance());
        }

        // Process C3's order
        System.out.println("\nProcessing C3's order...");
        warehouse.processOrder(c3);

        // Print all clients to show updated balances
        System.out.println("\nAll Clients (After C3's order):");
        for (Client c : warehouse.getClients()) {
            System.out.println(c + " Balance: $" + c.getBalance());
        }

        // Print C2's wishlist
        System.out.println("\nC2's Wishlist (After order):");
        for (WishlistItem item : c2.getWishlist()) {
            System.out.println(item);
        }

        // Print C3's wishlist
        System.out.println("\nC3's Wishlist (After order):");
        for (WishlistItem item : c3.getWishlist()) {
            System.out.println(item);
        }

        // Print final product quantities
        System.out.println("\nFinal Product Quantities:");
        for (Product p : warehouse.getProducts()) {
            System.out.println(p);
        }
    }
}