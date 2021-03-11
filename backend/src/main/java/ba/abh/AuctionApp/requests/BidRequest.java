package ba.abh.AuctionApp.requests;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.Instant;

public class BidRequest {
    @NotNull(message = "Bid date and time must be present")
    private Long dateTime;

    @NotNull(message = "Bid amount must be present")
    @DecimalMin(value = "0.00", message = "Bid amount can't be lower than zero")
    @DecimalMax(value = "1000000", message = "Maximum bid amount is 1000000")
    private Double amount;

    public BidRequest() {
    }

    public BidRequest(final Long dateTime, final Double amount) {
        this.dateTime = dateTime;
        this.amount = amount;
    }

    public Long getDateTime() {
        return dateTime;
    }

    public Instant getInstantDateTime() {
        return Instant.ofEpochMilli(dateTime);
    }

    public void setDateTime(final Long dateTime) {
        this.dateTime = dateTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }
}
