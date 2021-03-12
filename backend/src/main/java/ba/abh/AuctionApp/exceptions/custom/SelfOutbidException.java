package ba.abh.AuctionApp.exceptions.custom;

public class SelfOutbidException extends RuntimeException {
    public SelfOutbidException(final String message) {
        super(message);
    }
}
