package ba.abh.AuctionApp.exceptions.custom;

public class LowBidException extends RuntimeException {
    public LowBidException(final String message) {
        super(message);
    }
}
