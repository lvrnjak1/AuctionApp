package ba.abh.AuctionApp.responses;

import ba.abh.AuctionApp.domain.Auction;
import ba.abh.AuctionApp.domain.Product;
import ba.abh.AuctionApp.pagination.PageableEntity;

import java.time.Instant;

public class AuctionResponse implements PageableEntity {
    private Long id;
    private Instant startDateTime;
    private Instant endDateTime;
    private Double startPrice;
    private Product product;
    private Long sellerId;
    private boolean isWishlist;

    public AuctionResponse(final Auction auction) {
        this.id = auction.getId();
        this.startDateTime = auction.getStartDateTime();
        this.endDateTime = auction.getEndDateTime();
        this.startPrice = auction.getStartPrice();
        this.product = auction.getProduct();
        this.sellerId = auction.getSeller().getId();
        this.isWishlist = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
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

    public boolean isWishlist() {
        return isWishlist;
    }

    public void setWishlist(final boolean wishlist) {
        isWishlist = wishlist;
    }
}
