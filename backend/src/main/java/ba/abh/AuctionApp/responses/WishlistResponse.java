package ba.abh.AuctionApp.responses;

import ba.abh.AuctionApp.domain.Wishlist;
import ba.abh.AuctionApp.pagination.PageableEntity;

public class WishlistResponse implements PageableEntity {
    private Long auctionId;

    public WishlistResponse(final Wishlist wishlist) {
        this.auctionId = wishlist.getAuction().getId();
    }

    public WishlistResponse() {
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(final Long auctionId) {
        this.auctionId = auctionId;
    }
}
