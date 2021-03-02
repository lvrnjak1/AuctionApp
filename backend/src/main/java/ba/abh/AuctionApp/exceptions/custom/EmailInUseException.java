package ba.abh.AuctionApp.exceptions.custom;

public class EmailInUseException extends RuntimeException {
    public EmailInUseException(final String message) {
        super(message);
    }
}
