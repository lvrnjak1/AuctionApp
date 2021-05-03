package ba.abh.AuctionApp.responses;

import ba.abh.AuctionApp.domain.Auction;
import ba.abh.AuctionApp.pagination.PageableEntity;

public class DetailedWishlistResponse implements PageableEntity {
    private Auction auction;
    private Double highestBid;
    private Double yourPrice;

    public DetailedWishlistResponse() {
    }

    public DetailedWishlistResponse(final Auction auction, final Double highestBid, final Double yourPrice) {
        this.auction = auction;
        this.highestBid = highestBid;
        this.yourPrice = yourPrice;
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

    public void setHighestBid(final Double highestBid) {
        this.highestBid = highestBid;
    }

    public Double getYourPrice() {
        return yourPrice;
    }

    public void setYourPrice(final Double yourPrice) {
        this.yourPrice = yourPrice;
    }
}
