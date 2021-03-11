package ba.abh.AuctionApp.requests;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.Instant;

public class AuctionRequest {
    @NotNull(message = "Start date and time must be present")
    private Long startDateTime;

    @NotNull(message = "End date and time must be present")
    private Long endDateTime;

    @NotNull(message = "Start price must be present")
    @DecimalMin(value = "0.00", message = "Start price can't be lower than zero")
    @DecimalMax(value = "1000000", message = "Maximum start price is 1000000")
    private Double startPrice;

    @NotNull(message = "Product details must be present")
    @Valid
    private ProductRequest product;

    public AuctionRequest(final Long startDateTime,
                          final Long endDateTime,
                          final Double startPrice,
                          final ProductRequest product) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.startPrice = startPrice;
        this.product = product;
    }

    public Long getStartDateTime() {
        return startDateTime;
    }

    public Instant getInstantStartDateTime() {
        return Instant.ofEpochMilli(startDateTime);
    }

    public void setStartDateTime(final Long startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Long getEndDateTime() {
        return endDateTime;
    }

    public Instant getInstantEndDateTime() {
        return Instant.ofEpochMilli(endDateTime);
    }

    public void setEndDateTime(final Long endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(final Double startPrice) {
        this.startPrice = startPrice;
    }

    public ProductRequest getProduct() {
        return product;
    }

    public void setProduct(final ProductRequest product) {
        this.product = product;
    }
}
