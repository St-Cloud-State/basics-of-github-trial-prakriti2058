import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a waitlist exclusively for a single client.
 * This class holds a list of products that the client is waiting for.
 */
public class WaitlistItemOnly implements Serializable {
    private static final long serialVersionUID = 1L;
    private Client client;
    private List products = new LinkedList();

    //Constructor to create a waitlist for a specific client.
    public WaitlistItemOnly(Client client) {
        this.client = client;
    }

    //Adds a product to the client's waitlist
    public void addProduct(Product product) {
        products.add(product);
    }

    //Removes a product from the client's waitlist using its ID.
    public boolean removeProduct(String productId) {
        Iterator iterator = products.iterator();
        while (iterator.hasNext()) {
            Product product = (Product) iterator.next();
            if (product.getId().equals(productId)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    //Returns an iterator for the list of products on the waitlist.
    public Iterator getWaitlist() {
        return products.iterator();
    }

    
    //Gets the client associated with this waitlist.
    public Client getClient() {
        return client;
    }

    @Override
    public String toString() {
        return "Waitlist for client: " + client.getName() + " with " + products.size() + " item(s).";
    }
}