import java.util.*;
import java.text.*;

public class WishlistTester {
    
    public static void main(String[] args) {
        System.out.println("=== Wishlist System Test ===\n");
        
        // Create sample products
        Product p1 = new Product("P001", "Laptop", 999.99, 10);
        Product p2 = new Product("P002", "Mouse", 29.99, 20);
        Product p3 = new Product("P003", "Keyboard", 79.99, 15);
        Product p4 = new Product("P004", "Monitor", 299.99, 5);
        
        System.out.println("Created products:");
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);
        System.out.println();
        
        // Create sample clients
        Client c1 = new Client("C001", "bill gates", "billgates@email.com", "123-0423");
        Client c2 = new Client("C002", "april johnson", "jane@email.com", "555-5322");
        
        System.out.println("Created clients:");
        System.out.println(c1);
        System.out.println(c2);
        System.out.println();
        
        // Test WishlistItem class
        System.out.println("=== Testing WishlistItem ===");
        WishlistItem item1 = new WishlistItem(p1, 2);
        WishlistItem item2 = new WishlistItem(p2, 3);
        WishlistItem item3 = new WishlistItem(p3, 1);
        
        System.out.println("Created wishlist items:");
        System.out.println(item1);
        System.out.println(item2);
        System.out.println(item3);
        System.out.println();
        
        // Test Client's built-in wishlist functionality
        System.out.println("=== Testing Client's Wishlist ===");
        
        // Add items to client's wishlist
        c1.addWishlistItem(p1, 2);
        c1.addWishlistItem(p2, 3);
        c2.addWishlistItem(p3, 1);
        
        System.out.println("Client 1's wishlist:");
        for (WishlistItem item : c1.getWishlist()) {
            System.out.println("  - " + item);
        }
        
        System.out.println("\nClient 2's wishlist:");
        for (WishlistItem item : c2.getWishlist()) {
            System.out.println("  - " + item);
        }
        
        // Test removing from client's wishlist
        System.out.println("\nRemoving product P002 from Client 1's wishlist...");
        boolean removedFromClient = c1.removeWishlistItem("P002");
        System.out.println("Removed P002 from client's wishlist: " + removedFromClient);
        
        System.out.println("\nClient 1's wishlist after removal:");
        for (WishlistItem item : c1.getWishlist()) {
            System.out.println("  - " + item);
        }
        
        // Test waitlist functionality
        System.out.println("\n=== Testing Waitlist ===");
        c1.addWaitlistItem(p3, 2);
        c2.addWaitlistItem(p1, 3);
        
        System.out.println("Client 1's waitlist:");
        for (WaitlistItem item : c1.getWaitlist()) {
            System.out.println("  - " + item);
        }
        
        System.out.println("=== Test Complete ===");
    }
}
