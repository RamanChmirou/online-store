package service;

import entity.*;
import entity.discount.Discount;
import entity.discount.FixedAmountDiscount;
import entity.discount.PercentageDiscount;
import exception.ProductDoesNotExistsException;
import repository.OrderRepository;

import java.math.BigDecimal;
import java.util.*;

public class StoreService {
    private final ProductService productService = new ProductService();
    private final CartService cartService = new CartService(productService);
    private final OrderRepository orderRepository = new OrderRepository();
    private final OrderService orderService = new OrderService(orderRepository);

    private final Map<String, Discount> DISCOUNTS = Map.of(
            "PNEWYEAR30", new PercentageDiscount(new BigDecimal("0.30")),
            "FANEWYEAR100", new FixedAmountDiscount(new BigDecimal("100"))
    );

    public void addTestData() {
        List<Configuration> standardRam = List.of(
                new Configuration(ConfigurationCategory.RAM.getName(), "8GB", new BigDecimal("0.00")),
                new Configuration(ConfigurationCategory.RAM.getName(), "16GB", new BigDecimal("300.00"))
        );
        List<Configuration> proRam = List.of(
                new Configuration(ConfigurationCategory.RAM.getName(), "16GB", new BigDecimal("0.00")),
                new Configuration(ConfigurationCategory.RAM.getName(), "32GB", new BigDecimal("700.00")),
                new Configuration(ConfigurationCategory.RAM.getName(), "64GB", new BigDecimal("1500.00"))
        );

        List<Configuration> standardCpu = List.of(
                new Configuration(ConfigurationCategory.PROCESSOR.getName(), "Intel Core i3", new BigDecimal("0.00")),
                new Configuration(ConfigurationCategory.PROCESSOR.getName(), "Intel Core i5", new BigDecimal("500.00"))
        );
        List<Configuration> proCpu = List.of(
                new Configuration(ConfigurationCategory.PROCESSOR.getName(), "Intel Core i7", new BigDecimal("0.00")),
                new Configuration(ConfigurationCategory.PROCESSOR.getName(), "Intel Core i9", new BigDecimal("1200.00"))
        );

        List<Configuration> phoneColors = List.of(
                new Configuration(ConfigurationCategory.COLOR.getName(), "Czarny", new BigDecimal("0.00")),
                new Configuration(ConfigurationCategory.COLOR.getName(), "Biały", new BigDecimal("0.00")),
                new Configuration(ConfigurationCategory.COLOR.getName(), "Złoty (Premium)", new BigDecimal("200.00"))
        );
        List<Configuration> phoneAccessories = List.of(
                new Configuration(ConfigurationCategory.ACCESSORIES.getName(), "Brak", new BigDecimal("0.00")),
                new Configuration(ConfigurationCategory.ACCESSORIES.getName(), "Etui + Szkło", new BigDecimal("150.00")),
                new Configuration(ConfigurationCategory.ACCESSORIES.getName(), "Słuchawki Wireless", new BigDecimal("400.00"))
        );

        Map<ConfigurationCategory, List<Configuration>> basicPcConfig = new HashMap<>();
        basicPcConfig.put(ConfigurationCategory.RAM, standardRam);
        basicPcConfig.put(ConfigurationCategory.PROCESSOR, standardCpu);

        Map<ConfigurationCategory, List<Configuration>> proPcConfig = new HashMap<>();
        proPcConfig.put(ConfigurationCategory.RAM, proRam);
        proPcConfig.put(ConfigurationCategory.PROCESSOR, proCpu);

        Map<ConfigurationCategory, List<Configuration>> smartphoneConfig = new HashMap<>();
        smartphoneConfig.put(ConfigurationCategory.COLOR, phoneColors);
        smartphoneConfig.put(ConfigurationCategory.ACCESSORIES, phoneAccessories);


        productService.create(new Product(1L, "Kabel HDMI 2.0 (2m)", new BigDecimal("49.99"), 100L,
                ProductType.ELECTRONICS, Collections.emptyMap()));
        productService.create(new Product(2L, "Dysk zewnętrzny SSD 1TB", new BigDecimal("350.00"), 40L,
                ProductType.ELECTRONICS, Collections.emptyMap()));

        productService.create(new Product(3L, "Laptop Biurowy Dell Vostro", new BigDecimal("2500.00"), 20L,
                ProductType.COMPUTER, basicPcConfig));
        productService.create(new Product(4L, "Komputer Stacjonarny HP (Biuro)", new BigDecimal("1800.00"), 15L,
                ProductType.COMPUTER, basicPcConfig));
        productService.create(new Product(5L, "Laptop Gamingowy ASUS ROG", new BigDecimal("6500.00"), 8L,
                ProductType.COMPUTER, proPcConfig));
        productService.create(new Product(6L, "Stacja Robocza Mac Studio", new BigDecimal("10999.00"), 3L,
                ProductType.COMPUTER, proPcConfig));
        productService.create(new Product(7L, "Ultrabook Lenovo ThinkPad", new BigDecimal("5500.00"), 12L,
                ProductType.COMPUTER, proPcConfig));

        productService.create(new Product(8L, "Samsung Galaxy S23", new BigDecimal("4200.00"), 25L,
                ProductType.SMARTPHONE, smartphoneConfig));
        productService.create(new Product(9L, "Apple iPhone 15 Pro", new BigDecimal("5300.00"), 30L,
                ProductType.SMARTPHONE, smartphoneConfig));
        productService.create(new Product(10L, "Xiaomi Redmi Note 12", new BigDecimal("1100.00"), 50L,
                ProductType.SMARTPHONE, smartphoneConfig));
        productService.create(new Product(11L, "Google Pixel 8", new BigDecimal("3800.00"), 15L,
                ProductType.SMARTPHONE, smartphoneConfig));
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