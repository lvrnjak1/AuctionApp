package ba.abh.AuctionApp.responses;

import ba.abh.AuctionApp.domain.Auction;
import ba.abh.AuctionApp.pagination.PageableEntity;

public class DetailedWishlistResponse implements PageableEntity {
    private Auction auction;
    private Double highestBid;

    public DetailedWishlistResponse() {
    }

    public DetailedWishlistResponse(final Auction auction, final double highestBid) {
        this.auction = auction;
        this.highestBid = highestBid;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(final Auction auction) {
        this.auction = auction;
    }

    public double getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(final double highestBid) {
        this.highestBid = highestBid;
    }
}
