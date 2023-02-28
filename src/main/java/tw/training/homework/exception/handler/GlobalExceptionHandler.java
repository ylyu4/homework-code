package tw.training.homework.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tw.training.homework.exception.CredentialsIncorrectException;
import tw.training.homework.exception.ErrorBody;
import tw.training.homework.exception.JwtTokenValidationException;
import tw.training.homework.exception.CustomerNotFoundException;
import tw.training.homework.exception.UsernameExistedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorBody> handle(JwtTokenValidationException exception) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorBody body = new ErrorBody(status.value(), exception.getMessage());
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler({UsernameExistedException.class, CredentialsIncorrectException.class})
    public ResponseEntity<ErrorBody> handle(Exception exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorBody body = new ErrorBody(status.value(), exception.getMessage());
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorBody> handle(CustomerNotFoundException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorBody body = new ErrorBody(status.value(), exception.getMessage());
        return ResponseEntity.status(status).body(body);
    }


}

