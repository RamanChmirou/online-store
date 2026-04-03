package repository;

import entity.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class OrderRepository {
    private final static String FILE_NAME = "orders_history.txt";

    public void save(Order order) {
        String line = String.format("Zamówienie: %s | Data: %s | Imie klienta: %s | Suma: %s PLN\n",
                order.getId(),
                order.getOrderDate(),
                order.getUserName(),
                order.getTotalPrice()
        );

        String userHome = System.getProperty("user.home");
        Path fullPathToDesktop = Path.of(userHome, "Desktop", FILE_NAME);
        try {
            Files.writeString(fullPathToDesktop, line, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Nie udało się zapisać zamówienia: " + e.getMessage());
        }
    }
}
