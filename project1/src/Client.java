import java.util.*;
import java.io.*;

public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    private String clientId;
    private String name;
    private String email;
    private String phone;
    private List wishlist = new LinkedList();
    private List waitlist = new LinkedList();

    public Client(String clientId, String name, String email, String phone) {
        this.clientId = clientId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public void addWishlistItem(Product product) {
        wishlist.add(product);
    }

    public boolean removeWishlistItem(String productId) {
        Iterator products = wishlist.iterator();
        while (products.hasNext()) {
            Product p = (Product) products.next();
            if (p.getId().equals(productId)) {
                products.remove();
                return true;
            }
        }
        return false;
    }

    public void addWaitlistItem(Product product) {
        waitlist.add(product);
    }

    public boolean removeWaitlistItem(String productId) {
        Iterator products = waitlist.iterator();
        while (products.hasNext()) {
            Product p = (Product) products.next();
            if (p.getId().equals(productId)) {
                products.remove();
                return true;
            }
        }
        return false;
    }

    public Iterator getWishlist() {
        return wishlist.iterator();
    }

    public Iterator getWaitlist() {
        return waitlist.iterator();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return clientId;
    }

    public String toString() {
        return "Client id=" + clientId + " name=" + name + " email=" + email + " phone=" + phone;
    }
}
