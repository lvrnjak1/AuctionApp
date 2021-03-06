package ba.abh.AuctionApp.services;

import ba.abh.AuctionApp.domain.Auction;
import ba.abh.AuctionApp.domain.Bid;
import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.repositories.BidRepository;
import ba.abh.AuctionApp.requests.BidRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BidService {
    private final BidRepository bidRepository;
    private final AuctionService auctionService;
    private final UserService userService;

    public BidService(final BidRepository bidRepository,
                      final AuctionService auctionService,
                      final UserService userService) {
        this.bidRepository = bidRepository;
        this.auctionService = auctionService;
        this.userService = userService;
    }

    public Bid saveBidForAuction(final Long auctionId, final User user, final BidRequest bidRequest) {
        Auction auction = auctionService.getByIdIfExists(auctionId);
        Bid bid = getBidFromRequest(bidRequest, auction, user);
        auction.addBid(bid);
        bidRepository.save(bid);
        return bid;
    }

    private Bid getBidFromRequest(final BidRequest bidRequest, final Auction auction, final User user) {
        return new Bid(auction, user, bidRequest.getInstantDateTime(), bidRequest.getAmount());
    }

    public Page<Bid> findBidsForAuction(final Long auctionId, final int page, final int limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("amount").descending());
        return bidRepository.findAllByAuction_Id(auctionId, pageable);
    }
}
