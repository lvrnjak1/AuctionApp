package ba.abh.AuctionApp.repositories;

import ba.abh.AuctionApp.domain.Auction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    Slice<Auction> findByStartDateTimeBeforeAndEndDateTimeAfter(ZonedDateTime dateBefore,
                                                                ZonedDateTime dateAfter,
                                                                Pageable pageable
    );

    default Slice<Auction> findByStartDateTimeBeforeAndEndDateTimeAfter(ZonedDateTime date, Pageable pageable) {
        return findByStartDateTimeBeforeAndEndDateTimeAfter(date, date, pageable);
    }

    Slice<Auction> findByStartDateTimeBeforeAndEndDateTimeBetween(ZonedDateTime before,
                                                                  ZonedDateTime dateBeforeEnd,
                                                                  ZonedDateTime dateAfterEnd,
                                                                  Pageable pageable
    );
}
