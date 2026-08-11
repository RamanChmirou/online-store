package service;

import model.*;
import exception.ProductDoesNotExistsException;
import repository.OrderRepository;

import static service.TestDataGenerator.DISCOUNTS;

import java.util.*;

public class StoreService {
    private final ProductService productService;
    private final CartService cartService;
    private final OrderService orderService;

    public StoreService(OrderRepository orderRepository, ProductService productService) {
        this.orderService = new OrderService(orderRepository);
        this.productService = productService;
        this.cartService = new CartService(productService);
    }

    public void addTestData() {
        TestDataGenerator.addTestData(productService);
    }

    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    public Product getProductByName(String name) {
        return productService.findByName(name)
                .orElseThrow(() -> new ProductDoesNotExistsException("Ten produkt nie istnieje."));
    }

    public Configuration resolveConfiguration(Product product, ConfigurationCategory category, String value) {
        return product.getAvailableConfigurationList().get(category).stream()
                .filter(conf -> conf.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono takiej konfiguracji!"));
    }

    public void addProductToCart(Product product, long quantity, List<Configuration> configurations) {
        CartItem cartItem = new CartItem(product, quantity, configurations);
        cartService.addToCart(cartItem);
    }

    public Cart getCart() {
        return cartService.getCart();
    }

    public boolean isCartEmpty() {
        return cartService.getCart().getCartItemList().isEmpty();
    }

    public boolean applyDiscount(String code) {
        if (DISCOUNTS.containsKey(code)) {
            cartService.getCart().setDiscount(DISCOUNTS.get(code));
            return true;
        }
        return false;
    }

    public Order placeOrder(String userName) {
        return orderService.processOrder(cartService.getCart(), userName);
    }

    public void generateInvoice(Order order) {
        orderService.generateInvoice(order);
    }
}