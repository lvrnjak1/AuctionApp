package ba.abh.AuctionApp.exceptions.custom;

public class EmailInUseException extends RuntimeException {
    public EmailInUseException(String message) {
        super(message);
    }
}
