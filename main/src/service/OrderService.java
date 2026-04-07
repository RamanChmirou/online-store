package service;

import entity.Cart;
import entity.CartItem;
import entity.Order;
import exception.EmptyCartException;
import repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Serwis do zarządzania zamówieniami
 * Do konstruktora należy przekazać object OrderRepository
 */
public class OrderService {
    private final List<Order> orderList = new ArrayList<>();
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Tworzy zamówienie, dodaje do listy i czysci koszyk asynchrinicznie
     * Zapisuje zamówienie do pliku synchronicznie
     * @param cart Koszyk klienta
     * @param userName Imię klienta
     * @return Stworzone zomówienie
     */
    public Order processOrder(Cart cart, String userName) {
        if (cart.getCartItemList().isEmpty()) {
            throw new EmptyCartException("Pusty koszyk.");
        }
        Order order = new Order(userName, cart.getCartItemList());
        orderList.add(order);
        CompletableFuture.runAsync(() -> orderRepository.save(order));
        cart.clearCart();
        return order;
    }

    /**
     * Generuje i pokazuje fakturę klientowi
     * @param order Zamówinie
     */
    public void generateInvoice(Order order) {
        StringBuilder invoice = new StringBuilder();
        invoice.append("Factura").append("\n");
        invoice.append("Nr zamówienia: ").append(order.getId()).append("\n");
        invoice.append("Data: ").append(order.getOrderDate()).append("\n");
        invoice.append("Imie klienta: ").append(order.getUserName()).append("\n");
        for (CartItem  cartItem : order.getOrderedItems()) {
            invoice.append(cartItem.getProduct().getName())
                    .append(" x ").append(cartItem.getQuantity())
                    .append(" - ").append(cartItem.getPrice()).append(" PLN\n");
        }
        invoice.append("DO ZAPŁATY: ").append(order.getTotalPrice()).append(" PLN\n");
        System.out.println((invoice));
    }
}
