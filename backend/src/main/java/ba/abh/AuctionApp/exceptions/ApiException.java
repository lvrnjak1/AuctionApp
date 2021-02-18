package ba.abh.AuctionApp.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NONE, property = "error", visible = true)
public class ApiException {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;

    private ApiException() {
        timestamp = LocalDateTime.now();
    }

    ApiException(HttpStatus status) {
        this();
        this.status = status;
    }

    ApiException(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
    }

    ApiException(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
    }

    public void setMessageFromMap(Map<String, String> errors){
        this.message = String.join(", ", errors.values());
    }
}
