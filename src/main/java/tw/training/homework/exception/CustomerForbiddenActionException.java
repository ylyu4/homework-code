package tw.training.homework.exception;

public class CustomerForbiddenActionException extends RuntimeException{

    public CustomerForbiddenActionException(String message) {
        super(message);
    }

}
