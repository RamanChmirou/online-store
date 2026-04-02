package entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class CartItem {
    private final Product product;
    private int quantity;
    private final List<Configuration> configurationList;
    private final BigDecimal price;

    public CartItem(Product product, int quantity, List<Configuration> configurationList) {
        this.product = product;
        this.quantity = quantity;
        this.configurationList = configurationList;
        this.price = product.getStandardPrice().add(configurationList.stream()
                .map(Configuration::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public List<Configuration> getConfigurationList() {
        return configurationList;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    @Override
    public String toString() {
        String configurations = configurationList.stream()
                .map(Configuration::toString)
                .collect(Collectors.joining(","));
        return String.format("CartItem | product name: %s(%s), quantity %d, price: %s",
                product.getName(), configurations, quantity, price);
    }
}
