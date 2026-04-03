package entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class Order {
    private final String id;
    private final String userName;
    private final Instant orderDate;
    private final List<CartItem> orderedItems;
    private final BigDecimal totalPrice;

    public Order(String userName, List<CartItem> orderedItems) {
        this.id = UUID.randomUUID().toString();
        this.userName = userName;
        this.orderDate = Instant.now();
        this.orderedItems = List.copyOf(orderedItems);
        this.totalPrice = calculateTotalPrice();
    }

    public BigDecimal calculateTotalPrice() {
        return orderedItems.stream()
                .map(cartItem -> cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public List<CartItem> getOrderedItems() {
        return orderedItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
