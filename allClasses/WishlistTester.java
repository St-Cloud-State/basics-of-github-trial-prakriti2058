import java.util.*;
import java.text.*;

public class WishlistTester {
    
    public static void main(String[] args) {
        System.out.println("=== Wishlist System Test ===\n");
        
        // Create sample products
        ProductList p1 = new ProductList("P001", "Laptop", 999.99, "Super duper laptop");
        ProductList p2 = new ProductList("P002", "Mouse", 29.99, "Wireless optical mouse");
        ProductList p3 = new ProductList("P003", "Keyboard", 79.99, "Mechanical gaming keyboard");
        ProductList p4 = new ProductList("P004", "Monitor", 299.99, "27-inch 4K monitor");
        
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
        WishlistItem item1 = new WishlistItem("WI001", p1, c1);
        WishlistItem item2 = new WishlistItem("WI002", p2, c1);
        WishlistItem item3 = new WishlistItem("WI003", p3, c2);
        
        System.out.println("Created wishlist items:");
        System.out.println(item1);
        System.out.println(item2);
        System.out.println(item3);
        System.out.println();
        
        // Test WishlistItemOnly class
        System.out.println("=== Testing WishlistItemOnly ===");
        WishlistItemOnly wishlist1 = new WishlistItemOnly(c1);
        WishlistItemOnly wishlist2 = new WishlistItemOnly(c2);
        
        System.out.println("Created wishlists:");
        System.out.println(wishlist1);
        System.out.println(wishlist2);
        System.out.println();
        
        // Add products to wishlists
        System.out.println("Adding products to wishlists...");
        wishlist1.addProduct(p1);
        wishlist1.addProduct(p2);
        wishlist1.addProduct(p4);
        
        wishlist2.addProduct(p3);
        wishlist2.addProduct(p4);
        
        System.out.println("Wishlist 1 size: " + wishlist1.getWishlistSize());
        System.out.println("Wishlist 2 size: " + wishlist2.getWishlistSize());
        System.out.println();
        
        // Display wishlist contents
        System.out.println("Wishlist 1 contents:");
        Iterator<ProductList> iterator1 = wishlist1.getWishlist();
        while (iterator1.hasNext()) {
            System.out.println("  - " + iterator1.next());
        }
        System.out.println();
        
        System.out.println("Wishlist 2 contents:");
        Iterator<ProductList> iterator2 = wishlist2.getWishlist();
        while (iterator2.hasNext()) {
            System.out.println("  - " + iterator2.next());
        }
        System.out.println();
        
        // Test removing products
        System.out.println("Testing product removal...");
        boolean removed1 = wishlist1.removeProduct("P002");
        boolean removed2 = wishlist1.removeProduct("P999"); // Non-existent product
        
        System.out.println("Removed P002 from wishlist1: " + removed1);
        System.out.println("Removed P999 from wishlist1: " + removed2);
        System.out.println("Wishlist 1 size after removal: " + wishlist1.getWishlistSize());
        System.out.println();
        
        // Test duplicate addition
        System.out.println("Testing duplicate product addition...");
        wishlist1.addProduct(p1); // Try to add same product again
        System.out.println("Wishlist 1 size after duplicate addition: " + wishlist1.getWishlistSize());
        System.out.println();
        
        // Final wishlist contents
        System.out.println("Final wishlist 1 contents:");
        Iterator<ProductList> finalIterator = wishlist1.getWishlist();
        while (finalIterator.hasNext()) {
            System.out.println("  - " + finalIterator.next());
        }
        System.out.println();
        
        // Test product availability
        System.out.println("Testing product availability...");
        System.out.println("P1 available: " + p1.isAvailable());
        p1.setAvailable(false);
        System.out.println("P1 available after setting to false: " + p1.isAvailable());
        System.out.println();
        
        // Test removing from client's wishlist
        boolean removedFromClient = c1.removeWishlistItem("P002");
        System.out.println("Removed P002 from client's wishlist: " + removedFromClient);
        
        System.out.println("Client 1's wishlist after removal:");
        Iterator<ProductList> clientWishlistAfter = c1.getWishlist();
        while (clientWishlistAfter.hasNext()) {
            System.out.println("  - " + clientWishlistAfter.next());
        }
        System.out.println();
        
        System.out.println("=== Test Complete ===");
    }
}
