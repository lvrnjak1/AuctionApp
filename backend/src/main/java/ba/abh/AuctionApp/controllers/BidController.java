package ba.abh.AuctionApp.controllers;

import ba.abh.AuctionApp.controllers.utility.RequestParams;
import ba.abh.AuctionApp.domain.Bid;
import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.pagination.PageableEntity;
import ba.abh.AuctionApp.pagination.PaginationDetails;
import ba.abh.AuctionApp.requests.BidRequest;
import ba.abh.AuctionApp.responses.BidResponse;
import ba.abh.AuctionApp.responses.PageableResponse;
import ba.abh.AuctionApp.services.BidService;
import ba.abh.AuctionApp.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class BidController {
    private final BidService bidService;
    private final UserService userService;

    public BidController(final BidService bidService, final UserService userService) {
        this.bidService = bidService;
        this.userService = userService;
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
    public ResponseEntity<PageableResponse> getAllBidsForAuction(@PathVariable final Long auctionId,
                                                                 @Valid final RequestParams requestParams) {
        Page<Bid> bidPage = bidService.findBidsForAuction(auctionId, requestParams.getPage() - 1, requestParams.getLimit());
        return ResponseEntity.ok(buildPageableResponse(bidPage));
    }

    private PageableResponse buildPageableResponse(final Page<Bid> page) {
        PaginationDetails details = new PaginationDetails(page);
        List<? extends PageableEntity> data = page
                .getContent()
                .stream()
                .map(BidResponse::new)
                .collect(Collectors.toList());
        return new PageableResponse(details, (List<PageableEntity>) data);
    }
}
