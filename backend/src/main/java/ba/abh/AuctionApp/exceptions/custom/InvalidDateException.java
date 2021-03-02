package ba.abh.AuctionApp.exceptions.custom;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException(final String message) {
        super(message);
    }
}
