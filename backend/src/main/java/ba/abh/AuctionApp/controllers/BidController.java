package ba.abh.AuctionApp.controllers;

import ba.abh.AuctionApp.controllers.utility.AuctionStatus;
import ba.abh.AuctionApp.controllers.utility.RequestParams;
import ba.abh.AuctionApp.domain.Bid;
import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.pagination.PaginationDetails;
import ba.abh.AuctionApp.repositories.bid.BidProjection;
import ba.abh.AuctionApp.requests.BidRequest;
import ba.abh.AuctionApp.responses.AuctionResponse;
import ba.abh.AuctionApp.responses.BidMetadata;
import ba.abh.AuctionApp.responses.BidResponse;
import ba.abh.AuctionApp.responses.DetailedAuctionResponse;
import ba.abh.AuctionApp.responses.PageableResponse;
import ba.abh.AuctionApp.services.BidService;
import ba.abh.AuctionApp.services.UserService;
import ba.abh.AuctionApp.services.WishlistService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class BidController {
    private final BidService bidService;
    private final UserService userService;
    private final WishlistService wishlistService;

    public BidController(final BidService bidService, final UserService userService,
                         final WishlistService wishlistService) {
        this.bidService = bidService;
        this.userService = userService;
        this.wishlistService = wishlistService;
    }

    @PostMapping("/auctions/{auctionId}/bids")
    @Secured("ROLE_BUYER")
    public ResponseEntity<?> addBidForAuction(@PathVariable final Long auctionId,
                                              @Valid @RequestBody final BidRequest bidRequest,
                                              final Principal principal) {
        User user = userService.getUserFromPrincipal(principal);
        Bid bid = bidService.saveBidForAuction(auctionId, user, bidRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BidResponse(bid));
    }

    @GetMapping("/auctions/{auctionId}/bids")
    public ResponseEntity<PageableResponse<BidResponse>> getAllBidsForAuction(@PathVariable final Long auctionId,
                                                                 @Valid final RequestParams requestParams) {
        Page<Bid> bidPage = bidService.findBidsForAuction(auctionId, requestParams.getPage() - 1, requestParams.getLimit());
        return ResponseEntity.ok(buildPageableResponse(bidPage));
    }

    @GetMapping("/bids/detailed")
    public ResponseEntity<PageableResponse<DetailedAuctionResponse>> getDetailedBidsForAuction(final Principal principal,
                                                                      @Valid final RequestParams requestParams) {
        Page<BidProjection> bids = bidService.getBidsForBidder(principal.getName(),
                requestParams.getPage() - 1,
                requestParams.getLimit()
        );
        return ResponseEntity.ok().body(buildDetailedBidsResponse(bids, userService.getUserByEmail(principal.getName())));
    }

    @GetMapping("/seller/bids")
    @Secured("ROLE_SELLER")
    public ResponseEntity<PageableResponse<DetailedAuctionResponse>> getSoldBidsForSeller(final Principal principal,
                                                                 @Pattern(regexp = "^(active|closed|scheduled|all)$",
                                                                         message = "Invalid bid status"
                                                                 ) @RequestParam(defaultValue = "active") String status,
                                                                 @Valid final RequestParams requestParams) {
        Page<BidProjection> bids = bidService.getBidsForSeller(principal.getName(),
                AuctionStatus.valueOf(status.toUpperCase()),
                requestParams.getPage() - 1,
                requestParams.getLimit()
        );
        return ResponseEntity.ok().body(buildDetailedBidsResponse(bids, userService.getUserByEmail(principal.getName())));
    }

    private PageableResponse<BidResponse> buildPageableResponse(final Page<Bid> page) {
        PaginationDetails details = new PaginationDetails(page);
        final  List<BidResponse> data = page
                .getContent()
                .stream()
                .map(BidResponse::new)
                .collect(Collectors.toUnmodifiableList());
        return new PageableResponse<>(details, data);
    }

    private PageableResponse<DetailedAuctionResponse> buildDetailedBidsResponse(final Page<BidProjection> bidsPage, final User user) {
        PaginationDetails details = new PaginationDetails(bidsPage);
        final List<DetailedAuctionResponse> data = bidsPage
                .getContent()
                .stream()
                .map(bidProjection -> {
                    AuctionResponse auctionResponse = new AuctionResponse(bidProjection.getAuction());
                    boolean isInWishlist = wishlistService.isInWishlist(bidProjection.getAuction(), user);
                    auctionResponse.setWishlist(isInWishlist);
                    BidMetadata bidMetadata = new BidMetadata(bidProjection);
                    return new DetailedAuctionResponse(auctionResponse, bidMetadata);
                })
                .collect(Collectors.toUnmodifiableList());
        return new PageableResponse<>(details, data);
    }
}
