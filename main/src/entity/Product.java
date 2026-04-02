package entity;

import java.math.BigDecimal;
import java.util.List;

public class Product {
    private final long id;
    private final String name;
    private final BigDecimal standardPrice;
    private long quantity;
    private final ProductType productType;
    private final List<Configuration> standardConfigurationList;

    public Product(long id, String name, BigDecimal standardPrice, long quantity, ProductType productType, List<Configuration> standardConfigurationList) {
        this.id = id;
        this.name = name;
        this.standardPrice = standardPrice;
        this.quantity = quantity;
        this.productType = productType;
        this.standardConfigurationList = standardConfigurationList;
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

    public List<Configuration> getStandardConfigurationList() {
        return standardConfigurationList;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}