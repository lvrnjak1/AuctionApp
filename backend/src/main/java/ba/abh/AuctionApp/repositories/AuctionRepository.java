package ba.abh.AuctionApp.repositories;

import ba.abh.AuctionApp.domain.Auction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    Slice<Auction> findByStartDateTimeBeforeAndEndDateTimeAfter(Instant dateBefore,
                                                                Instant dateAfter,
                                                                Pageable pageable
    );

    default Slice<Auction> findByStartDateTimeBeforeAndEndDateTimeAfter(Instant date, Pageable pageable) {
        return findByStartDateTimeBeforeAndEndDateTimeAfter(date, date, pageable);
    }

    Slice<Auction> findByStartDateTimeBeforeAndEndDateTimeBetween(Instant before,
                                                                  Instant dateBeforeEnd,
                                                                  Instant dateAfterEnd,
                                                                  Pageable pageable
    );
}
