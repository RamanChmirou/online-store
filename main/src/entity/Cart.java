package entity;

import entity.discount.Discount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Reprezentuje koszyk, przechowuje informacje o zniżce
 */
public class Cart {
    private final List<CartItem> cartItemList = new ArrayList<>();
    private Discount discount = null;

    public Cart(){}
    
    public Cart(Discount discount) {
        this.discount = discount;
    }

    /**
     *  Metoda która wywołuje się na objekcie koszyka dla obliczenia sumy elementów z uwzględnieniem zniżki
     * @return Kwota do zapłaty
     */
    public BigDecimal calculateTotalPrice() {
        BigDecimal totalPrice = cartItemList.stream()
                .map(cartItem -> cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (discount != null) {
            totalPrice = discount.applyDiscount(totalPrice);
        }
        return totalPrice;
    }

    public void addCartItem(CartItem cartItem) {
        cartItemList.add(cartItem);
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void clearCart() {
        cartItemList.clear();
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}
