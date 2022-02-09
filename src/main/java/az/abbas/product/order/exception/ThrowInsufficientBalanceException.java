package az.abbas.product.order.exception;

public class ThrowInsufficientBalanceException extends RuntimeException{
    public ThrowInsufficientBalanceException(String message) {
        super(message);
    }
}
