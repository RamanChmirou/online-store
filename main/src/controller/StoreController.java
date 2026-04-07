package controller;

import entity.*;
import entity.discount.Discount;
import entity.discount.FixedAmountDiscount;
import entity.discount.PercentageDiscount;
import exception.ProductDoesNotExistsException;
import repository.OrderRepository;
import service.CartService;
import service.OrderService;
import service.ProductService;

import java.math.BigDecimal;
import java.util.*;

/**
 * Główny kontroler aplikacji odpowiedzialny za interakcję z użytkownikiem
 * Wyświetla menu, pobiera komendy z konsoli za pomocą klasy Scanner
 * i deleguje zadania biznesowe do odpowiednich serwisów (ProductService, CartService, OrderService)
 */
public class StoreController {
    private static final ProductService productService = new ProductService();
    private static final CartService cartService = new CartService(productService);
    private static final OrderRepository orderRepository = new OrderRepository();
    private static final OrderService orderService = new OrderService(orderRepository);
    private static final Scanner scanner = new Scanner(System.in);

    private static final Map<String, Discount> DISCOUNTS = Map.of(
            "PNEWYEAR30", new PercentageDiscount(new BigDecimal("0.30")),
            "FANEWYEAR100", new FixedAmountDiscount(new BigDecimal("100"))
            );

    /**
     * Inicjalizuje aplikację przykładowymi danymi
     * */
    public void addTestData() {
        List<Configuration> standardRam = List.of(
                new Configuration("RAM", "8GB", new BigDecimal("0.00")),
                new Configuration("RAM", "16GB", new BigDecimal("300.00"))
        );
        List<Configuration> proRam = List.of(
                new Configuration("RAM", "16GB", new BigDecimal("0.00")),
                new Configuration("RAM", "32GB", new BigDecimal("700.00")),
                new Configuration("RAM", "64GB", new BigDecimal("1500.00"))
        );

        List<Configuration> standardCpu = List.of(
                new Configuration("Procesor", "Intel Core i3", new BigDecimal("0.00")),
                new Configuration("Procesor", "Intel Core i5", new BigDecimal("500.00"))
        );
        List<Configuration> proCpu = List.of(
                new Configuration("Procesor", "Intel Core i7", new BigDecimal("0.00")),
                new Configuration("Procesor", "Intel Core i9", new BigDecimal("1200.00"))
        );

        List<Configuration> phoneColors = List.of(
                new Configuration("Kolor", "Czarny", new BigDecimal("0.00")),
                new Configuration("Kolor", "Biały", new BigDecimal("0.00")),
                new Configuration("Kolor", "Złoty (Premium)", new BigDecimal("200.00"))
        );
        List<Configuration> phoneAccessories = List.of(
                new Configuration("Dodatki", "Brak", new BigDecimal("0.00")),
                new Configuration("Dodatki", "Etui + Szkło", new BigDecimal("150.00")),
                new Configuration("Dodatki", "Słuchawki Wireless", new BigDecimal("400.00"))
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

        System.out.println("PRODUCTY DODANE");
    }

    /**
     * Uruchamia główną pętlę cyklu życia aplikacji
     * Metoda wyświetla menu opcji i oczekuje na wybór użytkownika,
     * dopóki nie zostanie wybrana opcja wyjścia z programu (zwrócenie false przez switchAnswer).
     * Przechwytuje niespodziewane wyjątki (try-catch),
     * aby zapobiec nagłemu zamknięciu programu
     */
    public void start() {
        boolean continueProgram = true;
        sayHello();
        while (continueProgram) {
            showOptions();
            String answer = scanner.nextLine();
            try{
                continueProgram = switchAnswer(answer);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void sayHello() {
        System.out.println("""
                =======================
                WITAMY W NASZYM SKLEPIE
                =======================
                """);
    }

    private static void sayBay() {
        System.out.println("Do zabaczenia.");
    }

    private static void showOptions() {
        System.out.println("""
                      Wybierz opcje:
                1) Pokaż wszystkie produkty
                2) Dodaj produkt do koszyka
                3) Zobacz koszyk
                4) Złóż zamówienie
                5) Koniec działania
                """);
    }

    /**
     * Przetwarza odpowiedż klienta i woła odpowiednią metodę
     * @param answer Odpowiedź klienta
     * @return false jesli klient wybrał opcje końcu działania i true w przeciwnym przypadku
     */
    private static boolean switchAnswer(String answer) {
        switch (answer) {
            case "1" -> {
                showAllProducts();
                return true;
            }
            case "2" -> {
                addProductToCart();
                return true;
            }
            case "3" -> {
                showCart();
                return true;
            }
            case "4" -> {
                placeOrder();
                return true;
            }
            case "5" -> {
                sayBay();
                return false;
            }
            default -> {
                System.out.println("Nie ma takiej opcji.");
                return true;
            }
        }
    }

    private static void showAllProducts() {
        productService.findAll()
                .forEach(System.out::println);
    }

    /**
     * Dodaje element do koszyka
     * Na początku sprawdza czy produkt, nazwę którego podał klient istnieje
     * Pyta klienta o konfiguracje i klienta ich wybiera
     * Pyta o ilość elementów
     * Tworzy element i dodaje do koszyka
     */
    private static void addProductToCart() {
        System.out.println("Podaj nazwe productu");
        String productName = scanner.nextLine();
        Product product = productService.findByName(productName)
                .orElseThrow(() -> new ProductDoesNotExistsException("Tego produktu nie istnieje."));

        List<Configuration> configurations = new ArrayList<>();
        for (ConfigurationCategory category : product.getAvailableConfigurationList().keySet()) {
            System.out.printf("Wybierz %s i podaj wartość:%n", category.getName());
            product.getAvailableConfigurationList().get(category)
                    .forEach(System.out::print);
            String configurationValue = scanner.nextLine();
            Configuration configuration = product.getAvailableConfigurationList().get(category).stream()
                            .filter(conf -> conf.getValue().equalsIgnoreCase(configurationValue))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono takiej konfiguracji!"));
            configurations.add(configuration);
        }

        System.out.println();
        System.out.println("Ile chcesz kupić tego produktu?");
        long quantity = Long.parseLong(scanner.nextLine());

        CartItem cartItem = new CartItem(product, quantity, configurations);
        cartService.addToCart(cartItem);
    }

    private static void showCart() {
        List<CartItem> cartItems = cartService.getCart().getCartItemList();
        if (cartItems.isEmpty()) {
            System.out.println("Twój koszyk jest obecnie pusty.");
            return;
        }

        System.out.println("\n=== TWÓJ KOSZYK ===");
        int index = 1;
        for (CartItem item : cartItems) {
            System.out.printf("%d. %s (Ilość: %d)%n", index, item.getProduct().getName(), item.getQuantity());

            if (item.getConfigurationList() != null && !item.getConfigurationList().isEmpty()) {
                System.out.print("   [Opcje: ");
                item.getConfigurationList().forEach(conf ->
                        System.out.print(conf.getName() + ": " + conf.getValue() + " | ")
                );
                System.out.println("]");
            }

            System.out.printf("   Cena: %s PLN%n%n", item.getPrice());
            index++;
        }

        System.out.printf("DO ZAPŁATY: %s PLN%n%n", cartService.getCart().calculateTotalPrice());
    }

    /**
     * Obsługuje interaktywny proces finalizacji zakupów
     * Weryfikuje, czy koszyk nie jest pusty, pobiera dane klienta oraz opcjonalny kod rabatowy.
     * Przypisuje rabat do koszyka, a następnie zleca serwisowi OrderService wygenerowanie
     * obiektu zamówienia i asynchroniczny zapis do pliku
     * Na koniec oferuje klientowi możliwość wygenerowania imiennej faktury
     */
    private static void placeOrder() {
        if (cartService.getCart().getCartItemList().isEmpty()) {
            System.out.println("Koszyk jest pusty.");
            return;
        }

        System.out.println("Podaj swoje imię.");
        String userName = scanner.nextLine();
        System.out.println("Jeśli chcesz użyć zniżki, wpisz kod rabatowy.");
        String code = scanner.nextLine().trim().toUpperCase();
        if (!code.isEmpty()) {
            if (DISCOUNTS.containsKey(code)) {
                cartService.getCart().setDiscount(DISCOUNTS.get(code));
                System.out.println("Kod rabatowy zaakceptowany!");
            } else {
                System.out.println("Niepoprawny kod rabatowy. Kontynuuję bez zniżki.");
            }
        }

        Order order = orderService.processOrder(cartService.getCart(), userName);
        System.out.println("Złożono zamówienie! ID zamówienia: " + order.getId());
        System.out.println("Czy wysłać fakturę? (Tak/Nie)");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("Tak")) {
            orderService.generateInvoice(order);
            System.out.println("Faktura została wygenerowana.");
        }
        System.out.printf("Dziękujemy za zakupy, %s!%n", userName);
    }
}
