package ba.abh.AuctionApp.repositories;

import ba.abh.AuctionApp.domain.CardDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardDetailsRepository extends JpaRepository<CardDetails, Long> {
}
