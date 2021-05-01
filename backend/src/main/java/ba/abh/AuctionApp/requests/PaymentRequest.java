package ba.abh.AuctionApp.requests;

import ba.abh.AuctionApp.utility.stripe.StripeToken;

import javax.validation.constraints.NotNull;

public class PaymentRequest {
    @NotNull(message = "Auction id can't be null")
    private Long auctionId;

    @NotNull(message = "Token shouldn't be null")
    private StripeToken token;

    public PaymentRequest() {
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(final Long auctionId) {
        this.auctionId = auctionId;
    }

    public StripeToken getToken() {
        return token;
    }

    public void setToken(final StripeToken token) {
        this.token = token;
    }
}
