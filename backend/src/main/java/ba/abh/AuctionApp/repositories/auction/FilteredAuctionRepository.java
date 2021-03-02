package ba.abh.AuctionApp.repositories.auction;

import ba.abh.AuctionApp.domain.Auction;
import ba.abh.AuctionApp.filters.AuctionFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilteredAuctionRepository {
    Page<Auction> findAllByFilter(final AuctionFilter filter, final Pageable pageable);
}
