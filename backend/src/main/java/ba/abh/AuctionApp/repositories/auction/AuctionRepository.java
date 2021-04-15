package ba.abh.AuctionApp.repositories.auction;

import ba.abh.AuctionApp.domain.Auction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Clock;
import java.time.Instant;
import java.util.Optional;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long>, FilteredAuctionRepository {
    Optional<Auction> findByIdAndStartDateTimeBeforeAndEndDateTimeAfterAndSeller_Active(final Long auctionId,
                                                                                        final Instant dateBefore,
                                                                                        final Instant dateAfter,
                                                                                        final boolean active);

    default Optional<Auction> findActiveById(final Long auctionId, final Instant date) {
        return findByIdAndStartDateTimeBeforeAndEndDateTimeAfterAndSeller_Active(auctionId, date, date, true);
    }

    @Query("select a from Auction a " +
            "left join Bid b on b.auction = a " +
            "left join User u on u = a.seller " +
            "where a.startDateTime <= ?1 and a.endDateTime >= ?1 and u.active = true " +
            "group by a " +
            "order by count(b.id) desc")
    Page<Auction> findAllActiveSortedByNumberOfBids(final Instant date, final Pageable pageable);

    @Query(value = "select a " +
            "from Auction a " +
            "where a.id = ?1 and ((a.seller.active = true and a.endDateTime >= ?2) or (a.endDateTime < ?2)) ")
    Optional<Auction> findAuctionById(final Long id, final Instant today);

    default Optional<Auction> findAuctionById(final Long id) {
        Instant today = Instant.now(Clock.systemUTC());
        return findAuctionById(id, today);
    }
}
