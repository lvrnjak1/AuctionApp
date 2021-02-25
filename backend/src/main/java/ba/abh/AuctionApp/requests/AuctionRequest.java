package ba.abh.AuctionApp.requests;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

public class AuctionRequest {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull(message = "Start date and time must be present")
    private Instant startDateTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull(message = "End date and time must be present")
    private Instant endDateTime;

    @NotNull(message = "Start price must be present")
    @DecimalMin(value = "0.00", message = "Start price can't be lower than zero")
    private BigDecimal startPrice;

    @NotNull(message = "Product details must be present")
    @Valid
    private ProductRequest product;

    public AuctionRequest(final Instant startDateTime,
                          final Instant endDateTime,
                          final BigDecimal startPrice,
                          final ProductRequest product) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.startPrice = startPrice;
        this.product = product;
    }

    public Instant getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(final Instant startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Instant getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(final Instant endDateTime) {
        this.endDateTime = endDateTime;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(final BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public ProductRequest getProduct() {
        return product;
    }

    public void setProduct(final ProductRequest product) {
        this.product = product;
    }
}
