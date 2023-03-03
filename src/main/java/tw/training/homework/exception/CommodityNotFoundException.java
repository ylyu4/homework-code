package tw.training.homework.exception;

public class CommodityNotFoundException extends RuntimeException{

    public CommodityNotFoundException(String message) {
        super(message);
    }

}