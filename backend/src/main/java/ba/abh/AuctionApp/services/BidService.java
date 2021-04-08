package ba.abh.AuctionApp.services;

import ba.abh.AuctionApp.domain.Auction;
import ba.abh.AuctionApp.domain.Bid;
import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.exceptions.custom.InvalidBidException;
import ba.abh.AuctionApp.exceptions.custom.InvalidDateException;
import ba.abh.AuctionApp.exceptions.custom.LowBidException;
import ba.abh.AuctionApp.exceptions.custom.SelfOutbidException;
import ba.abh.AuctionApp.repositories.bid.BidProjection;
import ba.abh.AuctionApp.repositories.bid.BidRepository;
import ba.abh.AuctionApp.requests.BidRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

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
        Auction auction = auctionService.getActiveByIdIfExists(auctionId);
        if (auction.getSeller().equals(user)) {
            throw new InvalidBidException("You can't place a bid on a product that you are selling");
        }

        if (bidRequest.getInstantDateTime().isAfter(LocalDateTime.now().toInstant(ZoneOffset.UTC)) ||
                bidRequest.getInstantDateTime().isBefore(auction.getStartDateTime())) {
            throw new InvalidDateException("Invalid datetime");
        }

        Optional<Bid> highestBid = getHighestBidForAuction(auction);
        if (highestBid.isPresent() && bidRequest.getAmount() <= highestBid.get().getAmount()) {
            throw new LowBidException(String.format("You must place a bid higher than %.2f", highestBid.get().getAmount()));
        } else if (highestBid.isEmpty() && bidRequest.getAmount() < auction.getStartPrice()) {
            throw new LowBidException(String.format("You must place a bid at least as high as %.2f", auction.getStartPrice()));
        }

        if (highestBid.isPresent() && highestBid.get().getBidder().equals(user)) {
            throw new SelfOutbidException("You are already the highest bidder");
        }

        Bid bid = getBidFromRequest(bidRequest, auction, user);
        auction.addBid(bid);
        bidRepository.save(bid);
        return bid;
    }

    private Bid getBidFromRequest(final BidRequest bidRequest, final Auction auction, final User user) {
        return new Bid(auction, user, bidRequest.getInstantDateTime(), bidRequest.getAmount());
    }

    private Optional<Bid> getHighestBidForAuction(final Auction auction) {
        return bidRepository.findFirstByAuctionOrderByAmountDesc(auction);
    }

    public Page<Bid> findBidsForAuction(final Long auctionId, final int page, final int limit) {
        Auction auction = auctionService.getActiveByIdIfExists(auctionId);
        Pageable pageable = PageRequest.of(page, limit, Sort.by("amount").descending());
        return bidRepository.findAllByAuction(auction, pageable);
    }

    public Page<BidProjection> getBidsForBidder(final String email, final int page, final int limit) {
        userService.getUserByEmail(email);
        Pageable pageable = PageRequest.of(page, limit);
        return bidRepository.getDetailedAuctionsByBidderEmail(email, pageable);
    }
}
