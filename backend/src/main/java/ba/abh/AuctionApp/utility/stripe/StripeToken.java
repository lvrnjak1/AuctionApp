package ba.abh.AuctionApp.utility.stripe;

public class StripeToken {
    private String id;

    public StripeToken(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }
}
