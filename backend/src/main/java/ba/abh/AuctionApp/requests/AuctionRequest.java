package ba.abh.AuctionApp.requests;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class AuctionRequest {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime startDateTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime endDateTime;

    @NotNull(message = "Start price must be present")
    private BigDecimal startPrice;

    @NotNull(message = "Product details must be present")
    private ProductRequest product;

    public AuctionRequest(final ZonedDateTime startDateTime,
                          final ZonedDateTime endDateTime,
                          final BigDecimal startPrice,
                          final ProductRequest product) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.startPrice = startPrice;
        this.product = product;
    }

    public ZonedDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(final ZonedDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public ZonedDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(final ZonedDateTime endDateTime) {
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
