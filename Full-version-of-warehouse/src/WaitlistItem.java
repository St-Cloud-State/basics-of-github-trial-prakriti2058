import java.io.Serializable;

/**
 * Represents a single item on a waitlist for a product by a client.
 * It contains the product, the client, and the priority of the request.
 */
public class WaitlistItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private Product product;
    private int quantity;

    /**
     * Constructor to create a waitlist item.
     * @param product The product being waitlisted.
     * @param quantity The quantity requested.
     */
    public WaitlistItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
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

    @Override
    public String toString() {
        return "WaitlistItem [Product: " + product.getName() + 
               ", Quantity: " + quantity + "]";
    }
}