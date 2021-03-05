package ba.abh.AuctionApp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "bids")
public class Bid extends BaseEntity {
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "bidder_id")
    private User bidder;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant dateTime;

    private Double amount;

    public Bid() {
    }

    public Bid(final Long id, final Auction auction, final User bidder, final Instant dateTime, final Double amount) {
        super(id);
        this.auction = auction;
        this.bidder = bidder;
        this.dateTime = dateTime;
        this.amount = amount;
    }

    public Bid(final Auction auction, final User bidder, final Instant dateTime, final Double amount) {
        super();
        this.auction = auction;
        this.bidder = bidder;
        this.dateTime = dateTime;
        this.amount = amount;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(final Auction auction) {
        this.auction = auction;
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
