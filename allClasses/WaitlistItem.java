import java.io.Serializable;

/**
 * Represents a single item on a waitlist for a product by a client.
 * It contains the product, the client, and the priority of the request.
 */
public class WaitlistItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private String itemId;
    private Product product;
    private Client client;
    private int priority;

    /**
     * Constructor to create a waitlist item.
     * @param itemId The unique ID for this waitlist entry.
     * @param product The product being waitlisted.
     * @param client The client who is waitlisting the product.
     * @param priority The priority of the waitlist entry.
     */
    public WaitlistItem(String itemId, Product product, Client client, int priority) {
        this.itemId = itemId;
        this.product = product;
        this.client = client;
        this.priority = priority;
    }

    //Gets the product associated with this waitlist item.
    public Product getProduct() {
        return product;
    }

    //Gets the client associated with this waitlist item.
    public Client getClient() {
        return client;
    }

    //Gets the priority of this waitlist item.
    public int getPriority() {
        return priority;
    }
    
    //Gets the ID of this waitlist item.
    public String getItemId() {
        return itemId;
    }

    @Override
    public String toString() {
        return "WaitlistItem ID: " + itemId + 
               ", Product: " + product.getName() + 
               ", Client: " + client.getName() + 
               ", Priority: " + priority;
    }
}