import java.io.Serializable;

/**
 * Represents a single item on a waitlist for a product by a client.
 * It contains the product, the client, and the priority of the request.
 */
public class WaitlistItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private Product product;
    private int quantity;
    private Client client;

    /**
     * Constructor to create a waitlist item.
     * @param product The product being waitlisted.
     * @param quantity The quantity requested.
     * @param client The client requesting the product.
     */
    public WaitlistItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.client = null;
    }

    public WaitlistItem(Product product, int quantity, Client client) {
        this.product = product;
        this.quantity = quantity;
        this.client = client;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Client getClient() {
        return client;
    }

    @Override
    public String toString() {
        return "WaitlistItem [Product: " + product.getName() + 
               ", Quantity: " + quantity + 
               ", Client: " + client.getName() + "]";
    }
}