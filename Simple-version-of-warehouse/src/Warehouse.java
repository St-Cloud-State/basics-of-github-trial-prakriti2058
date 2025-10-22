import java.util.*;
import java.io.*;

public class Warehouse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String warehouseId;
    private String location;
    private List<Product> products = new LinkedList<>();
    private List<Client> clients = new LinkedList<>();
    private static Warehouse warehouse;

    private Warehouse(String warehouseId, String location) {
        this.warehouseId = warehouseId;
        this.location = location;
    }

    public static Warehouse instance(String warehouseId, String location) {
        if (warehouse == null) {
            warehouse = new Warehouse(warehouseId, location);
        }
        return warehouse;
    }

    public boolean processOrder(Client client) {
        List<WishlistItem> wishlist = client.getWishlist();
        List<WishlistItem> unfulfilledItems = new ArrayList<>();
        double totalCost = 0.0;

        for (WishlistItem item : wishlist) {
            Product product = item.getProduct();
            int quantityWanted = item.getQuantity();
            int available = product.getQuantity();

            if (available >= quantityWanted) {
                // Fulfill order completely
                product.removeQuantity(quantityWanted);
                totalCost += quantityWanted * product.getPrice();
            } else if (available > 0) {
                // Partially fulfill order
                product.removeQuantity(available);
                totalCost += available * product.getPrice();
                // Add unfulfilled quantity back to wishlist
                unfulfilledItems.add(new WishlistItem(product, quantityWanted - available));
            } else {
                // Cannot fulfill any part of this item
                unfulfilledItems.add(new WishlistItem(product, quantityWanted));
            }
        }

        // Update client's balance
        client.updateBalance(totalCost);
        
        // Clear current wishlist and add unfulfilled items back
        client.clearWishlist();
        for (WishlistItem item : unfulfilledItems) {
            client.addWishlistItem(item.getProduct(), item.getQuantity());
        }

        return unfulfilledItems.isEmpty();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public boolean removeProduct(String productId) {
        Iterator<Product> iter = products.iterator();
        while (iter.hasNext()) {
            Product p = iter.next();
            if (p.getId().equals(productId)) {
                iter.remove();
                return true;
            }
        }
        return false;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public List<Client> getClients() {
        return new ArrayList<>(clients);
    }

    public Product findProduct(String productId) {
        for (Product p : products) {
            if (p.getId().equals(productId)) {
                return p;
            }
        }
        return null;
    }

    public void addProductQuantity(String productId, int quantity) {
        Product product = findProduct(productId);
        if (product != null) {
            product.addQuantity(quantity);
        }
    }

    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(warehouse);
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    private void readObject(java.io.ObjectInputStream input) {
        try {
            if (warehouse != null) {
                return;
            } else {
                input.defaultReadObject();
                if (warehouse == null) {
                    warehouse = (Warehouse) input.readObject();
                } else {
                    input.readObject();
                }
            }
        } catch (IOException ioe) {
            System.out.println("in Warehouse readObject \n" + ioe);
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }

    public String toString() {
        return "Warehouse id=" + warehouseId + " location=" + location;
    }
}
