package ba.abh.AuctionApp.requests;

import ba.abh.AuctionApp.utility.card.ValidCreditCard;
import ba.abh.AuctionApp.utility.card.ValidExpirationYear;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CardDetailsRequest {
    @NotBlank(message = "Name on card can't be blank")
    @Pattern(regexp = "^[a-z|A-Z]+(?: [a-z|A-Z]+)+$", message = "Name on card must have at least two words")
    private String nameOnCard;

    @NotNull(message = "You must provide the credit card number")
    @ValidCreditCard(message = "You must provide a valid credit card number")
    private String cardNumber;

    @NotNull(message = "You must provide the expiration month")
    @Min(value = 1, message = "Expiration month must range from 1 to 12")
    @Max(value = 12, message = "Expiration month must range from 1 to 12")
    private Integer expirationMonth;

    @NotNull(message = "You must provide an expiration year")
    @ValidExpirationYear(message = "You must provide a valid expiration year")
    private Integer expirationYear;

    @NotNull(message = "You must provide the cvc number")
    @Min(value = 100, message = "Valid cvc/cw number must have 3 or 4 digits")
    @Max(value = 9999, message = "Valid cvc/cw number must have 3 or 4 digits")
    private Integer cvc;

    public CardDetailsRequest() {
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(final String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(final String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(final int expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public int getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(final int expirationYear) {
        this.expirationYear = expirationYear;
    }

    public int getCvc() {
        return cvc;
    }

    public void setCvc(final int cvc) {
        this.cvc = cvc;
    }
}
