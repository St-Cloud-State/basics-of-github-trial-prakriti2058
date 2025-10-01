import java.util.*;
import java.io.*;

public class Warehouse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String warehouseId;
    private String location;
    private List products = new LinkedList();
    private List clients = new LinkedList();
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

    public void addProduct(Product product) {
        products.add(product);
    }

    public boolean removeProduct(String productId) {
        Iterator iter = products.iterator();
        while (iter.hasNext()) {
            Product p = (Product) iter.next();
            if (p.getId().equals(productId)) {
                iter.remove();
                return true;
            }
        }
        return false;
    }

    public Iterator getProductList() {
        return products.iterator();
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public Iterator getClients() {
        return clients.iterator();
    }

    public Product findProduct(String productId) {
        Iterator iter = products.iterator();
        while (iter.hasNext()) {
            Product p = (Product) iter.next();
            if (p.getId().equals(productId)) {
                return p;
            }
        }
        return null;
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
