package tw.training.homework.exception;

public class UsernameExistedException extends RuntimeException {

    public UsernameExistedException(String message) {
        super(message);
    }

}
