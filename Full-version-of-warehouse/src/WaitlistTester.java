import java.util.*;

public class WaitlistTester {
    public static void main(String[] args) {
        // 1. Setup: Create some sample products and a client
        Product p1 = new Product("P101", "Gaming Mouse", 75.00, 10);
        Product p2 = new Product("P102", "Mechanical Keyboard", 150.00, 15);
        Client c1 = new Client("C001", "John Doe", "john.doe@example.com", "555-1234");

        System.out.println("--- Testing WaitlistItem Class ---");

        // 2. Test the WaitlistItem class
        WaitlistItem wlItem = new WaitlistItem(p1, 2);

        System.out.println("Created WaitlistItem: " + wlItem);

        System.out.println("  - Verifying Getters:");
        System.out.println("    - Get Product Name: " + wlItem.getProduct().getName());
        System.out.println("    - Get Quantity: " + wlItem.getQuantity());

        System.out.println("\n--- Testing WaitlistItemOnly Class ---");

        // Test the WaitlistItemOnly class
        WaitlistItemOnly clientWaitlist = new WaitlistItemOnly(c1);
        System.out.println("Created WaitlistItemOnly for client: " + clientWaitlist.getClient().getName());

        // Add products to this client's waitlist
        clientWaitlist.addProduct(p1, 2);
        clientWaitlist.addProduct(p2, 3);
        System.out.println("Added two products to the client's waitlist.");

        // View the products on the waitlist
        System.out.println("Current items on waitlist:");
        for (WaitlistItem item : clientWaitlist.getWaitlist()) {
            System.out.println("  - " + item);
        }

        // Remove a product from the waitlist
        System.out.println("\nRemoving product P101...");
        boolean removed = clientWaitlist.removeProduct("P101");
        System.out.println("Product removed successfully? " + removed);

        // View the waitlist again to confirm removal
        System.out.println("Current items on waitlist after removal:");
        List<WaitlistItem> currentWaitlist = clientWaitlist.getWaitlist();
        if (currentWaitlist.isEmpty()) {
            System.out.println("  - The waitlist is now empty.");
        } else {
            for (WaitlistItem item : currentWaitlist) {
                System.out.println("  - " + item);
            }
        }
        
        // Try to remove a product that is not on the list
        System.out.println("\nTrying to remove a non-existent product (P999)...");
        boolean notRemoved = clientWaitlist.removeProduct("P999");
        System.out.println("Product removed successfully? " + notRemoved);

        System.out.println("\n--- Test Complete ---");
    }
}
