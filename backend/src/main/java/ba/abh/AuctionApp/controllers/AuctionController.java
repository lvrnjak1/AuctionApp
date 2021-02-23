package ba.abh.AuctionApp.controllers;

import ba.abh.AuctionApp.domain.Auction;
import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.pagination.PageableEntity;
import ba.abh.AuctionApp.pagination.PaginationDetails;
import ba.abh.AuctionApp.requests.AuctionRequest;
import ba.abh.AuctionApp.responses.AuctionResponse;
import ba.abh.AuctionApp.responses.PageableResponse;
import ba.abh.AuctionApp.services.AuctionService;
import ba.abh.AuctionApp.services.UserService;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<AuctionResponse> createAuction(@Valid @RequestBody AuctionRequest auctionRequest,
                                           Principal principal) {
        Auction auction = auctionService.createAuction(auctionRequest, getUserFromPrincipal(principal));
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuctionResponse(auction));
    }

    @GetMapping
    public ResponseEntity<?> getAllAuctions(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size){
        Slice<Auction> slice = auctionService.getAuctions(page, size);
        PageableResponse response = buildPageableResponse(slice);
        return ResponseEntity.ok(response);
    }

    private User getUserFromPrincipal(final Principal principal){
        return userService.getUserByEmail(principal.getName());
    }

    private PageableResponse buildPageableResponse(Slice<Auction> slice){
        PaginationDetails details = new PaginationDetails(slice.getNumber(), slice.hasNext());
        List<? extends PageableEntity> data = slice
                .getContent()
                .stream()
                .map(AuctionResponse::new)
                .collect(Collectors.toList());
        return new PageableResponse(details, (List<PageableEntity>) data);
    }
}
