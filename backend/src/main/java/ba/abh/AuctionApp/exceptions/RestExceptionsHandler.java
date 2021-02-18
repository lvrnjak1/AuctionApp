package ba.abh.AuctionApp.exceptions;

import ba.abh.AuctionApp.exceptions.custom.EmailInUseException;
import ba.abh.AuctionApp.exceptions.custom.InvalidCredentialsException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<Object> handleInvalidCredentials(InvalidCredentialsException ex) {
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST);
        apiException.setMessage(ex.getMessage());
        return buildResponseEntity(apiException);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        ApiException apiException = new ApiException(HttpStatus.UNPROCESSABLE_ENTITY);
        apiException.setMessageFromMap(errors);
        return buildResponseEntity(apiException);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiException ex) {
        return new ResponseEntity<>(ex, ex.getStatus());
    }
}
