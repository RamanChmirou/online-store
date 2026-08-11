package exception;

public class ProductDoesNotExistsException extends RuntimeException {
    public ProductDoesNotExistsException(String message) {
        super(message);
    }
}
