package ba.abh.AuctionApp.repositories;

import ba.abh.AuctionApp.domain.Auction;
import ba.abh.AuctionApp.domain.Bid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    Page<Bid> findAllByAuction(final Auction auction, final Pageable pageable);
    Optional<Bid> findFirstByAuctionOrderByAmountDesc(final Auction auction);
}
