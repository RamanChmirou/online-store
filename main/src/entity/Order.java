package entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private final long id;
    private final String userName;
    private final LocalDateTime orderDate;
    private final List<CartItem> orderedItems;
    private final BigDecimal totalPrice;

    public Order(long id, String userName, List<CartItem> orderedItems) {
        this.id = id;
        this.userName = userName;
        this.orderDate = LocalDateTime.from(Instant.now());
        this.orderedItems = orderedItems;
        this.totalPrice = calculateTotalPrice();
    }

    public BigDecimal calculateTotalPrice() {
        return orderedItems.stream()
                .map(cartItem -> cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public List<CartItem> getOrderedItems() {
        return orderedItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
