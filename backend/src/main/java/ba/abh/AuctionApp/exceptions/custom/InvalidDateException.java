package ba.abh.AuctionApp.exceptions.custom;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException(String message) {
        super(message);
    }
}
