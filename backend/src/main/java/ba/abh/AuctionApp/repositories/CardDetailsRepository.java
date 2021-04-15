package ba.abh.AuctionApp.repositories;

import ba.abh.AuctionApp.domain.CardDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardDetailsRepository extends JpaRepository<CardDetails, Long> {
    Optional<CardDetails> findByCardNumber(final String cardNumber);
}
