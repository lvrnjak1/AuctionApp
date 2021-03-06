package ba.abh.AuctionApp.repositories.auction;

import ba.abh.AuctionApp.domain.Auction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long>, FilteredAuctionRepository {
    Page<Auction> findByStartDateTimeBeforeAndEndDateTimeAfter(final Instant dateBefore,
                                                               final Instant dateAfter,
                                                               final Pageable pageable
    );

    default Page<Auction> findActiveAuctions(final Instant date, final Pageable pageable) {
        return findByStartDateTimeBeforeAndEndDateTimeAfter(date, date, pageable);
    }

    Optional<Auction> findByIdAndStartDateTimeBeforeAndEndDateTimeAfter(final Long auctionId,
                                                                        final Instant dateBefore,
                                                                        final Instant dateAfter);

    default Optional<Auction> findActiveById(final Long auctionId, final Instant date){
        return findByIdAndStartDateTimeBeforeAndEndDateTimeAfter(auctionId, date, date);
    }
}
