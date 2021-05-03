package ba.abh.AuctionApp.repositories;

import ba.abh.AuctionApp.domain.Auction;
import org.springframework.beans.factory.annotation.Value;

public interface WishlistProjection {
    @Value("#{target.auction}")
    Auction getAuction();

    @Value("#{target.highestBid}")
    Double getHighestBid();

    @Value("#{target.yourPrice}")
    Double getYourPrice();
}
