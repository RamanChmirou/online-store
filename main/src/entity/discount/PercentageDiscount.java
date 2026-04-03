package entity.discount;

import java.math.BigDecimal;

public class PercentageDiscount implements Discount {
    private final BigDecimal discountPercentage;

    public PercentageDiscount(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public BigDecimal applyDiscount(BigDecimal originalPrice) {
        return originalPrice.subtract(originalPrice.multiply(discountPercentage));
    }
}
