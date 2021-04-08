package ba.abh.AuctionApp.responses;

import ba.abh.AuctionApp.pagination.PageableEntity;

public class DetailedAuctionResponse implements PageableEntity {
    private AuctionResponse auction;
    private BidMetadata bidMetadata;

    public DetailedAuctionResponse() {
    }

    public DetailedAuctionResponse(final AuctionResponse auction, final BidMetadata bidMetadata) {
        this.auction = auction;
        this.bidMetadata = bidMetadata;
    }

    public AuctionResponse getAuction() {
        return auction;
    }

    public void setAuction(final AuctionResponse auction) {
        this.auction = auction;
    }

    public BidMetadata getBidMetadata() {
        return bidMetadata;
    }

    public void setBidMetadata(final BidMetadata bidMetadata) {
        this.bidMetadata = bidMetadata;
    }
}
