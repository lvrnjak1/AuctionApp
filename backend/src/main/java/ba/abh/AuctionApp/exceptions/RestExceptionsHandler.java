package ba.abh.AuctionApp.exceptions;

import ba.abh.AuctionApp.exceptions.custom.EmailInUseException;
import ba.abh.AuctionApp.exceptions.custom.EmailNotFoundException;
import ba.abh.AuctionApp.exceptions.custom.InvalidBidException;
import ba.abh.AuctionApp.exceptions.custom.InvalidCredentialsException;
import ba.abh.AuctionApp.exceptions.custom.InvalidCreditCardInfoException;
import ba.abh.AuctionApp.exceptions.custom.InvalidDateException;
import ba.abh.AuctionApp.exceptions.custom.InvalidPaginationException;
import ba.abh.AuctionApp.exceptions.custom.LowBidException;
import ba.abh.AuctionApp.exceptions.custom.ResourceNotFoundException;
import ba.abh.AuctionApp.exceptions.custom.SelfOutbidException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
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
    public ResponseEntity<Object> handleEmailInUse(final EmailInUseException ex) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> handleInvalidCredentials(final InvalidCredentialsException ex) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(final ResourceNotFoundException ex) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<Object> handleInvalidDate(final InvalidDateException ex) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(InvalidPaginationException.class)
    public ResponseEntity<Object> handleInvalidPagination(final InvalidPaginationException ex) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LowBidException.class)
    public ResponseEntity<Object> handleLowBid(final LowBidException ex) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, "LOW_BID");
    }

    @ExceptionHandler(SelfOutbidException.class)
    public ResponseEntity<Object> handleSelfOutbid(final SelfOutbidException ex) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, "SELF_OUTBID");
    }

    @ExceptionHandler(InvalidBidException.class)
    public ResponseEntity<Object> handleInvalidBid(final InvalidBidException ex) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, "OWNER_BIDDER");
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<Object> handleEmailNotFound(final EmailNotFoundException ex) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCreditCardInfoException.class)
    public ResponseEntity<Object> handleInvalidCreditCardInfo(final InvalidCreditCardInfoException ex) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex,
                                                         final HttpHeaders headers,
                                                         final HttpStatus status,
                                                         final WebRequest request) {
        return getObjectResponseEntity(ex, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {
        return getObjectResponseEntity(ex, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private ResponseEntity<Object> getObjectResponseEntity(final BindException ex, final HttpStatus status) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ApiException apiException = new ApiException(status);
        apiException.setMessageFromMap(errors);
        return buildResponseEntity(apiException);
    }

    private ResponseEntity<Object> buildResponseEntity(final String message, final HttpStatus status) {
        ApiException apiException = new ApiException(status);
        apiException.setMessage(message);
        return buildResponseEntity(apiException);
    }

    private ResponseEntity<Object> buildResponseEntity(final String message, final HttpStatus status, String type) {
        ApiException apiException = new ApiException(status);
        apiException.setMessage(message);
        apiException.setType(type);
        return buildResponseEntity(apiException);
    }

    private ResponseEntity<Object> buildResponseEntity(final ApiException apiException) {
        return new ResponseEntity<>(apiException, apiException.getStatus());
    }
}
