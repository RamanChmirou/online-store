package entity;

import java.math.BigDecimal;

public class Configuration {
    private final String name;
    private final String value;
    private final BigDecimal price;

    public Configuration(String name, String value, BigDecimal price) {
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

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s %s", name, value);
    }
}