/*
This is a test script that checks the Warehouse, Product, and Client classes.
*/
import java.util.*;
import java.io.*;

public class Tester {

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

        // Create some products
        Product p1 = new Product("P1", "Laptop", 1200.50);
        Product p2 = new Product("P2", "Phone", 799.99);
        Product p3 = new Product("P3", "Tablet", 450.00);

        // Add products
        warehouse.addProduct(p1);
        warehouse.addProduct(p2);
        warehouse.addProduct(p3);

        // Create clients
        Client c1 = new Client("C1", "Alice", "alice@mail.com", "123-456");
        Client c2 = new Client("C2", "Bob", "bob@mail.com", "789-123");

        // Add clients
        warehouse.addClient(c1);
        warehouse.addClient(c2);

        // Add wishlist items
        c1.addWishlistItem(p1);
        c1.addWishlistItem(p2);
        c2.addWishlistItem(p3);

        // Print warehouse info
        System.out.println("==== Warehouse Info ====");
        System.out.println(warehouse);

        // Print products
        System.out.println("==== Product List ====");
        Iterator products = warehouse.getProductList();
        while (products.hasNext()) {
            System.out.println(products.next());
        }

        // Print clients and their wishlists
        System.out.println("==== Clients ====");
        Iterator clients = warehouse.getClients();
        while (clients.hasNext()) {
            Client client = (Client) clients.next();
            System.out.println(client);

            System.out.println("Wishlist of " + client.getName() + ":");
            Iterator wishlist = client.getWishlist();
            while (wishlist.hasNext()) {
                System.out.println(wishlist.next());
            }
        }

        // Save if asked
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
