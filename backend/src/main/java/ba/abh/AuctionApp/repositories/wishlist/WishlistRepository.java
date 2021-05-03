package ba.abh.AuctionApp.repositories.wishlist;

import ba.abh.AuctionApp.domain.Auction;
import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.domain.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Optional<Wishlist> findByAuctionAndUser(final Auction auction, final User user);

    @Query(value = "select a as auction, coalesce(max(b.amount), 0) as highestBid, coalesce(max(b1.amount), 0) as yourPrice " +
            "from Wishlist w " +
            "left join Bid b1 on b1.auction = w.auction and b1.bidder = ?1 " +
            "join Auction a on a = w.auction and w.user = ?1 left join Bid b on a = b.auction " +
            "group by a")
    Page<WishlistProjection> findWishlistByUserDetailed(final User user, final Pageable pageable);
}
