package model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Reprezentuje produkt, przechuwuje informacje o dostępnych konfiguracjach
 */
public class Product {
    private final long id;
    private final String name;
    private BigDecimal standardPrice;
    private long quantity;
    private final ProductType productType;
    private Map<ConfigurationCategory, List<Configuration>> availableConfigurationList;

    public Product(long id, String name, BigDecimal standardPrice, long quantity, ProductType productType, Map<ConfigurationCategory, List<Configuration>> availableConfigurationList) {
        this.id = id;
        this.name = name;
        this.standardPrice = standardPrice;
        this.quantity = quantity;
        this.productType = productType;
        this.availableConfigurationList = availableConfigurationList;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getStandardPrice() {
        return standardPrice;
    }

    public long getQuantity() {
        return quantity;
    }

    public ProductType getProductType() {
        return productType;
    }

    public Map<ConfigurationCategory, List<Configuration>> getAvailableConfigurationList() {
        return availableConfigurationList;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public synchronized void reductionQuantity(long quantity) {
        this.quantity -= quantity;
    }

    public void setStandardPrice(BigDecimal standardPrice) {
        this.standardPrice = standardPrice;
    }

    public void setAvailableConfigurationList(Map<ConfigurationCategory, List<Configuration>> availableConfigurationList) {
        this.availableConfigurationList = availableConfigurationList;
    }

    @Override
    public String toString() {
        return String.format("Produkt | id: %d, nazwa: %s, standardowa cena: %s, ilość: %d, typ produktu: %s%n",
                id, name, standardPrice, quantity, productType.getName());
    }
}