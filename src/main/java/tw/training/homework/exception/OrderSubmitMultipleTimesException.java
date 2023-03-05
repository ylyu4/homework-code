package tw.training.homework.exception;

public class OrderSubmitMultipleTimesException extends RuntimeException {

    public OrderSubmitMultipleTimesException(String message) {
        super(message);
    }

}
