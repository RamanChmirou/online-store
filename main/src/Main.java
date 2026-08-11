import controller.StoreController;
import repository.OrderRepository;
import service.ProductService;

public class Main {
    public static void main(String[] args) {
        ProductService productService = new ProductService();
        OrderRepository orderRepository = new OrderRepository();
        StoreController storeController = new StoreController(orderRepository, productService);
        storeController.start();
    }
}
