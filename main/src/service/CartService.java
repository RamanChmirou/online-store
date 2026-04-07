package service;

import entity.Cart;
import entity.CartItem;
import entity.Product;
import exception.InsufficientStockException;
import exception.ProductDoesNotExistsException;

/**
 * Serwis do zarządzania koszykiem
 * Do konstruktora trzeba przekazać objekt klasy ProductService
 */
public class CartService {
    private final Cart cart = new Cart();
    private final ProductService productService;

    public CartService(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Dodaje do koszyka element koszyka
     * Sprawdza czy taki produkt istnieje i czy jego ilość nie jest mniejsza od tej którą chce klient dodać
     * @param cartItem Element koszyka
     */
    public void addToCart(CartItem cartItem) {
        Product product = productService.findById(cartItem.getProduct().getId())
                .orElseThrow(() -> new ProductDoesNotExistsException("Tego produktu nie istnieje."));
        long productQuantity = product.getQuantity();
        if (productQuantity < cartItem.getQuantity()) {
            throw new InsufficientStockException(String.format("Nie ma takiej ilości tego produktu. Jest tylko %d sztuk.", productQuantity));
        }
        cart.addCartItem(cartItem);
        product.reductionQuantity(cartItem.getQuantity());
    }

    public Cart getCart() {
        return cart;
    }
}
