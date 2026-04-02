package entity;

import java.math.BigDecimal;

public class Configuration {
    private final String name;
    private final int value;
    private final BigDecimal price;

    public Configuration(String name, int value, BigDecimal price) {
        this.name = name;
        this.value = value;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getValue() {
        return value;
    }
}