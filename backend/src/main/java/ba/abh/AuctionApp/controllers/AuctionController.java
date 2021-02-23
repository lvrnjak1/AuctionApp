package ba.abh.AuctionApp.controllers;

import ba.abh.AuctionApp.domain.Auction;
import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.requests.AuctionRequest;
import ba.abh.AuctionApp.responses.AuctionResponse;
import ba.abh.AuctionApp.services.AuctionService;
import ba.abh.AuctionApp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/auctions")
public class AuctionController {

    private final AuctionService auctionService;
    private final UserService userService;

    public AuctionController(final AuctionService auctionService, final UserService userService) {
        this.auctionService = auctionService;
        this.userService = userService;
    }

    @PostMapping
    @Secured("ROLE_SELLER")
    public ResponseEntity<?> createAuction(@Valid @RequestBody AuctionRequest auctionRequest,
                                           Principal principal) {
        Auction auction = auctionService.createAuction(auctionRequest, getUserFromPrincipal(principal));
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuctionResponse(auction));
    }

    private User getUserFromPrincipal(final Principal principal){
        return userService.getUserByEmail(principal.getName());
    }
}
