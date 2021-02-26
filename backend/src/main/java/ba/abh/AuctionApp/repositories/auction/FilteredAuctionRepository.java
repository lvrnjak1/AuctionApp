package ba.abh.AuctionApp.repositories.auction;

import ba.abh.AuctionApp.domain.Auction;
import ba.abh.AuctionApp.filters.AuctionFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FilteredAuctionRepository {
    Slice<Auction> findAllByFilter(AuctionFilter filter, Pageable pageable);
}
