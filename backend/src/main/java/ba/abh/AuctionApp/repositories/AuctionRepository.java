package ba.abh.AuctionApp.repositories;

import ba.abh.AuctionApp.domain.Auction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    Slice<Auction> findByStartDateTimeBeforeAndEndDateTimeAfter(Instant dateBefore, Instant dateAfter, Pageable pageable);

    default Slice<Auction> findActiveAuctions(Instant date, Pageable pageable) {
        return findByStartDateTimeBeforeAndEndDateTimeAfter(date, date, pageable);
    }

    Slice<Auction> findByStartDateTimeBeforeAndEndDateTimeBetween(Instant before,
                                                                  Instant dateBeforeEnd,
                                                                  Instant dateAfterEnd,
                                                                  Pageable pageable
    );

    Slice<Auction> findByStartDateTimeBeforeAndEndDateTimeAfterAndProduct_Category_IdOrProduct_Category_ParentCategory_Id(
            Instant dateBefore,
            Instant dateAfter,
            Long categoryId,
            Long parentCategoryId,
            Pageable pageable
    );

    default Slice<Auction> findActiveAuctionsByCategoryId(Instant date, Long categoryId, Pageable pageable) {
        return findByStartDateTimeBeforeAndEndDateTimeAfterAndProduct_Category_IdOrProduct_Category_ParentCategory_Id(
                date,
                date,
                categoryId,
                categoryId,
                pageable
        );
    }
}
