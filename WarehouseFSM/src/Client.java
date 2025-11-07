import java.util.*;
import java.io.*;

public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    private String clientId;
    private String name;
    private String email;
    private String phone;
    private double balance;
    private List<WishlistItem> wishlist = new LinkedList<>();
    private List<WaitlistItem> waitlist = new LinkedList<>();

    public Client(String clientId, String name, String email, String phone) {
        this.clientId = clientId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.balance = 0.0;
    }

    public void addWishlistItem(WishlistItem item) {
        wishlist.add(item);
    }
    
    public void addWishlistItem(Product product, int quantity) {
        wishlist.add(new WishlistItem(product, quantity));
    }
    
    public void clearWishlist() {
        wishlist.clear();
    }

    public boolean removeWishlistItem(String productId) {
        List<WishlistItem> updatedWishlist = new ArrayList<>();
        Iterator<WishlistItem> iterator = getWishlist();
        boolean removed = false;
        
        while (iterator.hasNext()) {
            WishlistItem item = iterator.next();
            if (!item.getProduct().getId().equals(productId)) {
                updatedWishlist.add(item);
            } else {
                removed = true;
            }
        }
        
        wishlist.clear();
        wishlist.addAll(updatedWishlist);
        return removed;
    }

    public void updateBalance(double amount) {
        this.balance += amount;
    }

    public double getBalance() {
        return balance;
    }

    public Iterator<WishlistItem> getWishlist() {
        return new ArrayList<>(wishlist).iterator();
    }

    public void addWaitlistItem(Product product, int quantity) {
        waitlist.add(new WaitlistItem(product, quantity, this));
    }

    public Iterator<WaitlistItem> getWaitlist() {
        return new ArrayList<>(waitlist).iterator();
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
