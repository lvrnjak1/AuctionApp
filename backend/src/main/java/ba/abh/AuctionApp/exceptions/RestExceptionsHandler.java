package ba.abh.AuctionApp.exceptions;

import ba.abh.AuctionApp.exceptions.custom.EmailInUseException;
import ba.abh.AuctionApp.exceptions.custom.InvalidCredentialsException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionsHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EmailInUseException.class)
    public ResponseEntity<Object> handleEmailInUse(EmailInUseException ex) {
        ApiException apiException = new ApiException(HttpStatus.UNPROCESSABLE_ENTITY);
        apiException.setMessage(ex.getMessage());
        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> handleInvalidCredentials(EmailInUseException ex) {
        ApiException apiException = new ApiException(HttpStatus.UNPROCESSABLE_ENTITY);
        apiException.setMessage(ex.getMessage());
        return buildResponseEntity(apiException);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiException ex) {
        return new ResponseEntity<>(ex, ex.getStatus());
    }
}
