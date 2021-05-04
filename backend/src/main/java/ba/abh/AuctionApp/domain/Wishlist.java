package ba.abh.AuctionApp.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "wishlists")
public class Wishlist extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Wishlist() {
    }

    public Wishlist(final Auction auction, final User user) {
        this.auction = auction;
        this.user = user;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(final Auction auction) {
        this.auction = auction;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Wishlist wishlist = (Wishlist) o;
        return Objects.equals(auction, wishlist.auction) &&
                Objects.equals(user, wishlist.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), auction, user);
    }
}
