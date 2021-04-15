package ba.abh.AuctionApp.exceptions.custom;

public class InvalidCreditCardInfoException extends RuntimeException {
    public InvalidCreditCardInfoException(final String message) {
        super(message);
    }
}
