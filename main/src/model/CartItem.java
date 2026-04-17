package model;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Reprezentuje element koszyka z wybraną konfiguracją
 */
public class CartItem {
    private final Product product;
    private long quantity;
    private final List<Configuration> configurationList;
    private final BigDecimal price;

    public CartItem(Product product, long quantity, List<Configuration> configurationList) {
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

    public long getQuantity() {
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
        return String.format("Element koszyka | nazwa: %s(%s), ilość %d, cena: %s%n",
                product.getName(), configurations, quantity, price);
    }
}
