package entity.discount;

import java.math.BigDecimal;

public interface Discount {
    BigDecimal applyDiscount(BigDecimal originalPrice);
}
