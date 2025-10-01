import java.util.*;
import java.io.*;

public class SimpleTester {
    public static void main(String[] args) {
        // Create Warehouse 
        Warehouse warehouse = Warehouse.instance("W1", "New York");

        // Create Products
        Product p1 = new Product("P1", "Laptop", 1200.50);
        Product p2 = new Product("P2", "Phone", 799.99);
        Product p3 = new Product("P3", "Tablet", 450.00);

        // Add Products to Warehouse
        warehouse.addProduct(p1);
        warehouse.addProduct(p2);
        warehouse.addProduct(p3);

        // Create Clients
        Client c1 = new Client("C1", "Alice", "alice@mail.com", "123-456");
        Client c2 = new Client("C2", "Bob", "bob@mail.com", "789-123");

        // Add Clients to Warehouse
        warehouse.addClient(c1);
        warehouse.addClient(c2);

        // Add Wishlist items
        c1.addWishlistItem(p1);
        c1.addWishlistItem(p2);
        c2.addWishlistItem(p3);

        // Print Warehouse Info
        System.out.println(warehouse);

        // Print Products in Warehouse
        System.out.println("Products in warehouse:");
        Iterator products = warehouse.getProductList();
        while (products.hasNext()) {
            System.out.println(products.next());
        }

        // Print Clients
        System.out.println("Clients in warehouse:");
        Iterator clients = warehouse.getClients();
        while (clients.hasNext()) {
            Client client = (Client) clients.next();
            System.out.println(client);

            // Print each client’s wishlist
            System.out.println("Wishlist of " + client.getName() + ":");
            Iterator wishlist = client.getWishlist();
            while (wishlist.hasNext()) {
                System.out.println(wishlist.next());
            }
        }

        // Test removing product
        System.out.println("Removing product P2...");
        boolean removed = warehouse.removeProduct("P2");
        System.out.println("Product P2 removed? " + removed);

        System.out.println("Updated product list:");
        Iterator updatedProducts = warehouse.getProductList();
        while (updatedProducts.hasNext()) {
            System.out.println(updatedProducts.next());
        }

        // Test removing wishlist item
        System.out.println("Removing Laptop from Alice's wishlist...");
        boolean wishlistRemoved = c1.removeWishlistItem("P1");
        System.out.println("Removed from wishlist? " + wishlistRemoved);

        System.out.println("Alice's updated wishlist:");
        Iterator wishlistAfter = c1.getWishlist();
        while (wishlistAfter.hasNext()) {
            System.out.println(wishlistAfter.next());
        }
    }
}
