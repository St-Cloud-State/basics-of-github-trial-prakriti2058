/*
This is a test script that checks Warehouse, Product, Client,
ProductList, WaitlistItem, WaitlistItemOnly, WishlistItem, and WishlistItemOnly classes.
*/

import java.util.*;
import java.io.*;

public class Test {

    public static String getToken(String prompt) {
        do {
            try {
                System.out.println(prompt);
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String line = reader.readLine();
                StringTokenizer tokenizer = new StringTokenizer(line, "\n\r\f");
                if (tokenizer.hasMoreTokens()) {
                    return tokenizer.nextToken();
                }
            } catch (IOException ioe) {
                System.exit(0);
            }
        } while (true);
    }

    private static boolean yesOrNo(String prompt) {
        String more = getToken(prompt + " (Y|y)[es] or anything else for no");
        if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Warehouse warehouse = null;

        if (yesOrNo("Would you like to load the warehouse from disk")) {
            try {
                FileInputStream file = new FileInputStream("WarehouseData");
                ObjectInputStream input = new ObjectInputStream(file);
                warehouse = (Warehouse) input.readObject();
                System.out.println("Warehouse loaded successfully.");
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (ClassNotFoundException cnfe) {
                cnfe.printStackTrace();
            }
        }

        if (warehouse == null) {
            warehouse = Warehouse.instance("W1", "New York");
        }

        // ====== Create some Products ======
        Product prod1 = new Product("P1", "Laptop", 1200.50, 10);
        Product prod2 = new Product("P2", "Phone", 799.99, 15);
        Product prod3 = new Product("P3", "Tablet", 450.00, 20);

        warehouse.addProduct(prod1);
        warehouse.addProduct(prod2);
        warehouse.addProduct(prod3);

        // ====== Create Clients ======
        Client c1 = new Client("C1", "Alice", "alice@mail.com", "123-456");
        Client c2 = new Client("C2", "Bob", "bob@mail.com", "789-123");

        warehouse.addClient(c1);
        warehouse.addClient(c2);

                // Test adding items to wishlist
        c1.addWishlistItem(prod1, 2);
        c1.addWishlistItem(prod2, 3);
        c2.addWishlistItem(prod3, 1);

        // Test adding items to waitlist
        c1.addWaitlistItem(prod3, 2);

        // ====== Print Warehouse Info ======
        System.out.println("==== Warehouse Info ====");
        System.out.println(warehouse);

        System.out.println("==== Product List ====");
        System.out.println("==== Product List ====");
        for (Product p : warehouse.getProducts()) {
            System.out.println(p);
        }

        // Print Clients and their wishlists/waitlists
        System.out.println("\nClients in warehouse:");
        for (Client client : warehouse.getClients()) {
            System.out.println(client);

            // Print each client's wishlist
            System.out.println("Wishlist of " + client.getName() + ":");
            for (WishlistItem item : client.getWishlist()) {
                System.out.println(item);
            }

            // Print each client's waitlist
            System.out.println("Waitlist of " + client.getName() + ":");
            for (WaitlistItem item : client.getWaitlist()) {
                System.out.println(item);
            }
        }

        // ====== Test ProductList & WishlistItem ======
        ProductList pl1 = new ProductList("PL1", "Gaming Laptop", 1500.0, "High performance laptop");
        ProductList pl2 = new ProductList("PL2", "Headphones", 99.99, "Noise-cancelling headphones");

        WishlistItem wi1 = new WishlistItem(prod1, 2);
        WishlistItem wi2 = new WishlistItem(prod2, 3);

        System.out.println("==== WishlistItem Objects ====");
        System.out.println(wi1);
        System.out.println(wi2);

        // ====== Test WaitlistItem & WaitlistItemOnly ======
        WaitlistItem waitItem = new WaitlistItem(prod1, 2);
        System.out.println("==== WaitlistItem ====");
        System.out.println(waitItem);

        WaitlistItemOnly waitlistAlice = new WaitlistItemOnly(c1);
        waitlistAlice.addProduct(prod1, 2);
        System.out.println("==== WaitlistItemOnly for Alice ====");
        System.out.println(waitlistAlice);

        waitlistAlice.removeProduct("P1");
        System.out.println("After removing P1: " + waitlistAlice);

        // ====== Toggle ProductList availability ======
        System.out.println("==== ProductList Availability Check ====");
        System.out.println(pl1);
        pl1.setAvailable(false);
        System.out.println("After setting unavailable: " + pl1);

        // ====== Save warehouse if asked ======
        if (yesOrNo("Would you like to save the warehouse to disk")) {
            try {
                FileOutputStream file = new FileOutputStream("WarehouseData");
                ObjectOutputStream output = new ObjectOutputStream(file);
                output.writeObject(warehouse);
                output.close();
                System.out.println("Warehouse saved successfully.");
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
