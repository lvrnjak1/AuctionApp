package ba.abh.AuctionApp.requests;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class AuctionRequest {
    @NotNull(message = "Start date and time must be present")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date startDateTime;

    @NotNull(message = "End date and time must be present")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date endDateTime;

    @NotNull(message = "Start price must be present")
    private BigDecimal startPrice;

    @NotNull(message = "Product details must be present")
    private ProductRequest product;

    public AuctionRequest(final Date startDateTime,
                          final Date endDateTime,
                          final BigDecimal startPrice,
                          final ProductRequest product) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.startPrice = startPrice;
        this.product = product;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(final Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(final Date endDateTime) {
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
