package ba.abh.AuctionApp.exceptions.custom;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(final String message) {
        super(message);
    }
}
