package ba.abh.AuctionApp.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "auctions")
public class Auction extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant startDateTime;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant endDateTime;

    private Double startPrice;

    @OneToMany(mappedBy = "auction")
    private List<Bid> bids = new ArrayList<>();

    public Auction() {
    }

    public Auction(final Long id,
                   final Product product,
                   final User seller,
                   final Instant startDateTime,
                   final Instant endDateTime,
                   final Double startPrice) {
        super(id);
        this.product = product;
        this.seller = seller;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.startPrice = startPrice;
    }

    public Auction(final Product product,
                   final User seller,
                   final Instant startDateTime,
                   final Instant endDateTime,
                   final Double startPrice) {
        this.product = product;
        this.seller = seller;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.startPrice = startPrice;
    }

    public Auction(final Long id,
                   final Product product,
                   final User seller,
                   final Instant startDateTime,
                   final Instant endDateTime,
                   final Double startPrice,
                   final List<Bid> bids) {
        super(id);
        this.product = product;
        this.seller = seller;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.startPrice = startPrice;
        this.bids = bids;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(final Product product) {
        this.product = product;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(final User seller) {
        this.seller = seller;
    }

    public Instant getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(final Instant startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Instant getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(final Instant endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(final Double startPrice) {
        this.startPrice = startPrice;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(final List<Bid> bids) {
        this.bids = bids;
    }

    public void addBid(final Bid bid) {
        bids.add(bid);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Auction auction = (Auction) o;
        return Objects.equals(product, auction.product) &&
                Objects.equals(seller, auction.seller) &&
                Objects.equals(startDateTime, auction.startDateTime) &&
                Objects.equals(endDateTime, auction.endDateTime) &&
                Objects.equals(startPrice, auction.startPrice) &&
                Objects.equals(bids, auction.bids);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), product, seller, startDateTime, endDateTime, startPrice, bids);
    }
}
