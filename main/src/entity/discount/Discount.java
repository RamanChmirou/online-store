package entity.discount;

import java.math.BigDecimal;

/**
 * Interfejs który powinna zrealizować każda metoda przedstawiająca zniżkę
 */
public interface Discount {
    BigDecimal applyDiscount(BigDecimal originalPrice);
}
