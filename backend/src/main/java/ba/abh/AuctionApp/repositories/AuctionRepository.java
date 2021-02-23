package ba.abh.AuctionApp.repositories;

import ba.abh.AuctionApp.domain.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
}
