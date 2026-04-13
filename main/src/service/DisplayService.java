package service;

import entity.Cart;
import entity.CartItem;
import entity.Configuration;
import entity.ConfigurationCategory;
import entity.Product;

import java.util.List;
import java.util.Scanner;

public class DisplayService {
    private final Scanner scanner = new Scanner(System.in);

    public void sayHello() {
        System.out.println("""
                =======================
                WITAMY W NASZYM SKLEPIE
                =======================
                """);
    }

    public void sayBye() {
        System.out.println("Do zobaczenia.");
    }

    public void showOptions() {
        System.out.println("""
                      Wybierz opcje:
                1) Pokaż wszystkie produkty
                2) Dodaj produkt do koszyka
                3) Zobacz koszyk
                4) Złóż zamówienie
                5) Koniec działania
                """);
    }

    public String getInput() {
        return scanner.nextLine();
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void showAllProducts(List<Product> products) {
        products.forEach(System.out::println);
    }

    public String askForProductName() {
        System.out.println("Podaj nazwe productu:");
        return scanner.nextLine();
    }

    public String askForConfigurationValue(ConfigurationCategory category, List<Configuration> availableConfigs) {
        System.out.printf("Wybierz %s i podaj wartość:%n", category.getName());
        availableConfigs.forEach(System.out::print);
        return scanner.nextLine();
    }

    public long askForQuantity() {
        System.out.println("\nIle chcesz kupić tego produktu?");
        return Long.parseLong(scanner.nextLine());
    }

    public void showCart(Cart cart) {
        System.out.println("\n=== TWÓJ KOSZYK ===");
        int index = 1;
        for (CartItem item : cart.getCartItemList()) {
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
        System.out.printf("DO ZAPŁATY: %s PLN%n%n", cart.calculateTotalPrice());
    }

    public String askForUserName() {
        System.out.println("Podaj swoje imię:");
        return scanner.nextLine();
    }

    public String askForDiscountCode() {
        System.out.println("Jeśli chcesz użyć zniżki, wpisz kod rabatowy (lub wciśnij Enter, aby pominąć):");
        return scanner.nextLine().trim().toUpperCase();
    }

    public boolean askForInvoice() {
        System.out.println("Czy wysłać fakturę? (Tak/Nie)");
        String answer = scanner.nextLine();
        return answer.equalsIgnoreCase("Tak");
    }
}