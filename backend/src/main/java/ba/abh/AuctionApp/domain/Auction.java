package ba.abh.AuctionApp.domain;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
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

    @Basic
    private ZonedDateTime startDateTime;

    @Basic
    private ZonedDateTime endDateTime;

    private BigDecimal startPrice;

    public Auction() {
    }

    public Auction(final Long id,
                   final Product product,
                   final User seller,
                   final ZonedDateTime startDateTime,
                   final ZonedDateTime endDateTime,
                   final BigDecimal startPrice) {
        super(id);
        this.product = product;
        this.seller = seller;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.startPrice = startPrice;
    }

    public Auction(final Product product,
                   final User seller,
                   final ZonedDateTime startDateTime,
                   final ZonedDateTime endDateTime,
                   final BigDecimal startPrice) {
        this.product = product;
        this.seller = seller;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.startPrice = startPrice;
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

    public ZonedDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(final ZonedDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public ZonedDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(final ZonedDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(final BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Auction auction = (Auction) o;
        return Objects.equals(product, auction.product) &&
                Objects.equals(seller, auction.seller) &&
                Objects.equals(startDateTime, auction.startDateTime) &&
                Objects.equals(endDateTime, auction.endDateTime) &&
                Objects.equals(startPrice, auction.startPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), product, seller, startDateTime, endDateTime, startPrice);
    }
}
