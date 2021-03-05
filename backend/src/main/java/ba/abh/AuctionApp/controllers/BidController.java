package ba.abh.AuctionApp.controllers;

import ba.abh.AuctionApp.domain.Bid;
import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.requests.BidRequest;
import ba.abh.AuctionApp.responses.BidResponse;
import ba.abh.AuctionApp.services.BidService;
import ba.abh.AuctionApp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

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
}
