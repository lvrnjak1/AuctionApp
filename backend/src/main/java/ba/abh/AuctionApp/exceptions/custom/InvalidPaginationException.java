package ba.abh.AuctionApp.exceptions.custom;

public class InvalidPaginationException extends RuntimeException {
    public InvalidPaginationException(final String message) {
        super(message);
    }
}
