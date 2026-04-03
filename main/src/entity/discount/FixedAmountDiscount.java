package entity.discount;

import java.math.BigDecimal;

public class FixedAmountDiscount implements Discount {
    private final BigDecimal discountAmount;

    public FixedAmountDiscount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public BigDecimal applyDiscount(BigDecimal originalPrice) {
        BigDecimal finalPrice = originalPrice.subtract(discountAmount);
        if (finalPrice.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }
        return finalPrice;
    }
}
