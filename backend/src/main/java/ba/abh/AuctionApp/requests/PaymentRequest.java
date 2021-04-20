package ba.abh.AuctionApp.requests;

import ba.abh.AuctionApp.utility.stripe.StripeToken;

public class PaymentRequest {
    public enum Currency {
        USD
    }

    private String description;
    private int amount;
    private Currency currency;
    private String stripeEmail;
    private StripeToken token;

    public PaymentRequest() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(final int amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    public String getStripeEmail() {
        return stripeEmail;
    }

    public void setStripeEmail(final String stripeEmail) {
        this.stripeEmail = stripeEmail;
    }

    public StripeToken getToken() {
        return token;
    }

    public void setToken(final StripeToken token) {
        this.token = token;
    }
}
