package ba.abh.AuctionApp.repositories.bid;

import ba.abh.AuctionApp.domain.Auction;
import ba.abh.AuctionApp.domain.Bid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    Page<Bid> findAllByAuction(final Auction auction, final Pageable pageable);

    Optional<Bid> findFirstByAuctionOrderByAmountDesc(final Auction auction);

    @Query(value = "select a as auction, count(b2.id) as numberOfBids, " +
            "b1.amount as yourPrice, max(b2.amount) as highestBid " +
            "from Auction a, Bid  b1, Bid b2 " +
            "where a = b2.auction and a = b1.auction and  b1.bidder.email = ?1 " +
            "and b1.amount = (select max(b3.amount) from Bid b3 where b3.auction = a and b3.bidder.email = ?1) " +
            "group by a, b1.amount")
    Page<BidProjection> getDetailedAuctionsByBidderEmail(final String email, final Pageable pageable);

    @Query(value = "select a as auction, count(b2.id) as numberOfBids, " +
            "a.startPrice as yourPrice, max(b2.amount) as highestBid " +
            "from Auction a, Bid  b1, Bid b2 " +
            "where a = b2.auction and a = b1.auction and  a.seller.email = ?1 " +
            "and b1.amount = (select max(b3.amount) from Bid b3 where b3.auction = a and a.seller.email = ?1) " +
            "group by a, a.startPrice")
    Page<BidProjection> getAuctionsBySellerEmail(final String email, final Pageable pageable);

    @Query(value = "select a as auction, count(b2.id) as numberOfBids, " +
            "a.startPrice as yourPrice, max(b2.amount) as highestBid " +
            "from Auction a, Bid  b1, Bid b2 " +
            "where a = b2.auction and a = b1.auction and  a.seller.email = ?1 " +
            "and b1.amount = (select max(b3.amount) from Bid b3 where b3.auction = a and a.seller.email = ?1) " +
            "and a.startDateTime <= ?2 and a.endDateTime >= ?2 " +
            "group by a, a.startPrice " +
            "order by a.endDateTime asc")
    Page<BidProjection> getActiveAuctionsBySellerEmail(final String email, final Instant today, final Pageable pageable);

    @Query(value = "select a as auction, count(b2.id) as numberOfBids, " +
            "a.startPrice as yourPrice, max(b2.amount) as highestBid " +
            "from Auction a, Bid  b1, Bid b2 " +
            "where a = b2.auction and a = b1.auction and  a.seller.email = ?1 " +
            "and b1.amount = (select max(b3.amount) from Bid b3 where b3.auction = a and a.seller.email = ?1) " +
            "and a.endDateTime < ?2 " +
            "group by a, a.startPrice " +
            "order by a.endDateTime desc")
    Page<BidProjection> getClosedAuctionsBySellerEmail(final String email, final Instant today, final Pageable pageable);

    @Query(value = "select a as auction, 0 as numberOfBids, " +
            "a.startPrice as yourPrice, a.startPrice as highestBid " +
            "from Auction a " +
            "where a.seller.email = ?1 and a.startDateTime > ?2 " +
            "group by a, a.startPrice " +
            "order by a.startDateTime asc")
    Page<BidProjection> getScheduledAuctionsBySellerEmail(final String email, final Instant today, final Pageable pageable);
}
