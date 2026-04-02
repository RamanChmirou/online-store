package service;

import entity.Cart;
import entity.CartItem;
import entity.Product;
import exception.InsufficientStockException;
import exception.ProductDoesNotExistsException;


public class CartService {
    private final Cart cart = new Cart();
    private final ProductService productService;

    public CartService(ProductService productService) {
        this.productService = productService;
    }

    public void addToCart(CartItem cartItem) {
        Product optionalProduct = productService.findById(cartItem.getProduct().getId())
                .orElseThrow(() -> new ProductDoesNotExistsException("Tego produktu nie istnieje."));
        long productQuantity = optionalProduct.getQuantity();
        if (productQuantity < cartItem.getQuantity()) {
            throw new InsufficientStockException(String.format("Nie ma takiej ilości tego produktu. Jest tylko %d sztuk.", productQuantity));
        }
        cart.addCartItem(cartItem);
        optionalProduct.reductionQuantity(cartItem.getQuantity());
    }
}
