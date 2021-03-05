package ba.abh.AuctionApp.repositories;

import ba.abh.AuctionApp.domain.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
}
