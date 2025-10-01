import java.util.*;
import java.io.*;

public class WishlistItem implements Serializable {
    private String itemId;
    private ProductList product;
    private Client client;
    private Date dateAdded;

    public WishlistItem(String itemId, ProductList product, Client client) {
        this.itemId = itemId;
        this.product = product;
        this.client = client;
        this.dateAdded = new Date();
    }

    public String getItemId() {
        return itemId;
    }

    public ProductList getProduct() {
        return product;
    }

    public Client getClient() {
        return client;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public String toString() {
        return "WishlistItem [itemId=" + itemId + ", product=" + product.getName() + 
               ", client=" + client.getName() + ", dateAdded=" + dateAdded + "]";
    }
}
