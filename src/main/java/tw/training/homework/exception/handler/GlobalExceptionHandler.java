package tw.training.homework.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tw.training.homework.exception.CommodityDuplicateException;
import tw.training.homework.exception.CommodityNotFoundException;
import tw.training.homework.exception.CredentialsIncorrectException;
import tw.training.homework.exception.ErrorBody;
import tw.training.homework.exception.CustomerNotFoundException;
import tw.training.homework.exception.UsernameExistedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UsernameExistedException.class,
                       CredentialsIncorrectException.class,
                       CommodityDuplicateException.class})
    public ResponseEntity<ErrorBody> handle(Exception exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorBody body = new ErrorBody(status.value(), exception.getMessage());
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler({CustomerNotFoundException.class,
                       CommodityNotFoundException.class})
    public ResponseEntity<ErrorBody> handleNotFoundException(Exception exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorBody body = new ErrorBody(status.value(), exception.getMessage());
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorBody> handle(MethodArgumentNotValidException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorBody body = new ErrorBody(status.value(),
                exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return ResponseEntity.status(status).body(body);
    }


}

