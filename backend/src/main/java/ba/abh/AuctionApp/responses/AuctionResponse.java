package ba.abh.AuctionApp.responses;

import ba.abh.AuctionApp.domain.Auction;
import ba.abh.AuctionApp.domain.Product;
import ba.abh.AuctionApp.pagination.PageableEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class AuctionResponse implements PageableEntity {
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime startDateTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime endDateTime;

    private BigDecimal startPrice;
    private Product product;
    private Long sellerId;

    public AuctionResponse(final Auction auction) {
        this.id = auction.getId();
        this.startDateTime = auction.getStartDateTime();
        this.endDateTime = auction.getEndDateTime();
        this.startPrice = auction.getStartPrice();
        this.product = auction.getProduct();
        this.sellerId = auction.getSeller().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(final Product product) {
        this.product = product;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(final Long sellerId) {
        this.sellerId = sellerId;
    }
}
