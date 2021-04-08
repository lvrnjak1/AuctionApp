package ba.abh.AuctionApp.repositories.bid;

import ba.abh.AuctionApp.domain.Auction;
import org.springframework.beans.factory.annotation.Value;

public interface BidProjection {
    @Value("#{target.auction}")
    Auction getAuction();
    @Value("#{target.numberOfBids}")
    Long getNumberOfBids();
    @Value("#{target.yourPrice}")
    Double getYourPrice();
    @Value("#{target.highestBid}")
    Double getHighestBid();
}
