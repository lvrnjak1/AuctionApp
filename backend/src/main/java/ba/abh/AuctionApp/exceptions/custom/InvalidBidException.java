package ba.abh.AuctionApp.exceptions.custom;

public class InvalidBidException extends RuntimeException {
    public InvalidBidException(final String message) {
        super(message);
    }
}
