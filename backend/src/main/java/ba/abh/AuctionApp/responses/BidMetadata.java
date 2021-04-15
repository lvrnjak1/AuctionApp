package ba.abh.AuctionApp.responses;

import ba.abh.AuctionApp.repositories.bid.BidProjection;

public class BidMetadata {
    private Long numberOfBids;
    private Double yourPrice;
    private Double highestBid;

    public BidMetadata() {
    }

    public BidMetadata(final Long numberOfBids, final Double yourPrice, final Double highestBid) {
        this.numberOfBids = numberOfBids;
        this.yourPrice = yourPrice;
        this.highestBid = highestBid;
    }

    public BidMetadata(final BidProjection bidProjection) {
        this.numberOfBids = bidProjection.getNumberOfBids();
        this.yourPrice = bidProjection.getYourPrice();
        this.highestBid = bidProjection.getHighestBid();
    }

    public Long getNumberOfBids() {
        return numberOfBids;
    }

    public void setNumberOfBids(final Long numberOfBids) {
        this.numberOfBids = numberOfBids;
    }

    public Double getYourPrice() {
        return yourPrice;
    }

    public void setYourPrice(final Double yourPrice) {
        this.yourPrice = yourPrice;
    }

    public Double getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(final Double highestBid) {
        this.highestBid = highestBid;
    }
}
