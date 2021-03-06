package ba.abh.AuctionApp.responses;

import ba.abh.AuctionApp.domain.Bid;
import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.pagination.PageableEntity;

import java.time.Instant;

public class BidResponse implements PageableEntity {
    private Long auctionId;
    private User bidder;
    private Instant dateTime;
    private Double amount;

    public BidResponse() {
    }

    public BidResponse(final Long auctionId, final User bidder, final Instant dateTime, final Double amount) {
        this.auctionId = auctionId;
        this.bidder = bidder;
        this.dateTime = dateTime;
        this.amount = amount;
    }

    public BidResponse(final Bid bid) {
        this.auctionId = bid.getAuction().getId();
        this.bidder = bid.getBidder();
        this.dateTime = bid.getDateTime();
        this.amount = bid.getAmount();
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(final Long auctionId) {
        this.auctionId = auctionId;
    }

    public User getBidder() {
        return bidder;
    }

    public void setBidder(final User bidder) {
        this.bidder = bidder;
    }

    public Instant getDateTime() {
        return dateTime;
    }

    public void setDateTime(final Instant dateTime) {
        this.dateTime = dateTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }
}
