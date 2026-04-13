package controller;

import entity.Configuration;
import entity.ConfigurationCategory;
import entity.Order;
import entity.Product;
import service.DisplayService;
import service.StoreService;

import java.util.ArrayList;
import java.util.List;

/**
 * Główny kontroler aplikacji odpowiedzialny za koordynację
 * między interfejsem użytkownika (DisplayService) a logiką biznesową (StoreService).
 */
public class StoreController {
    private final StoreService storeService = new StoreService();
    private final DisplayService displayService = new DisplayService();

    public void start() {
        storeService.addTestData();
        displayService.printMessage("PRODUKTY DODANE");

        boolean continueProgram = true;
        displayService.sayHello();

        while (continueProgram) {
            displayService.showOptions();
            String answer = displayService.getInput();
            try {
                continueProgram = performAnswer(answer);
            } catch (Exception e) {
                displayService.printMessage("[BŁĄD]: " + e.getMessage());
            }
        }
    }

    private boolean performAnswer(String answer) {
        switch (answer) {
            case "1" -> {
                displayService.showAllProducts(storeService.getAllProducts());
                return true;
            }
            case "2" -> {
                addProductToCartFlow();
                return true;
            }
            case "3" -> {
                showCartFlow();
                return true;
            }
            case "4" -> {
                placeOrderFlow();
                return true;
            }
            case "5" -> {
                displayService.sayBye();
                return false;
            }
            default -> {
                displayService.printMessage("Nie ma takiej opcji.");
                return true;
            }
        }
    }

    private void addProductToCartFlow() {
        String productName = displayService.askForProductName();
        Product product = storeService.getProductByName(productName);

        List<Configuration> configurations = new ArrayList<>();
        for (ConfigurationCategory category : product.getAvailableConfigurationList().keySet()) {
            List<Configuration> availableConfigs = product.getAvailableConfigurationList().get(category);
            String chosenValue = displayService.askForConfigurationValue(category, availableConfigs);

            Configuration resolvedConfig = storeService.resolveConfiguration(product, category, chosenValue);
            configurations.add(resolvedConfig);
        }

        long quantity = displayService.askForQuantity();
        storeService.addProductToCart(product, quantity, configurations);
        displayService.printMessage("Pomyślnie dodano do koszyka.");
    }

    private void showCartFlow() {
        if (storeService.isCartEmpty()) {
            displayService.printMessage("Twój koszyk jest obecnie pusty.");
            return;
        }
        displayService.showCart(storeService.getCart());
    }

    private void placeOrderFlow() {
        if (storeService.isCartEmpty()) {
            displayService.printMessage("Koszyk jest pusty. Złożenie zamówienia niemożliwe.");
            return;
        }

        String userName = displayService.askForUserName();
        String code = displayService.askForDiscountCode();

        if (!code.isEmpty()) {
            boolean accepted = storeService.applyDiscount(code);
            if (accepted) {
                displayService.printMessage("Kod rabatowy zaakceptowany!");
            } else {
                displayService.printMessage("Niepoprawny kod rabatowy. Kontynuuję bez zniżki.");
            }
        }

        Order order = storeService.placeOrder(userName);
        displayService.printMessage("Złożono zamówienie! ID zamówienia: " + order.getId());

        if (displayService.askForInvoice()) {
            storeService.generateInvoice(order);
            displayService.printMessage("Faktura została wygenerowana.");
        }

        displayService.printMessage("Dziękujemy za zakupy, " + userName + "!");
    }
}