package ba.abh.AuctionApp.controllers;

import ba.abh.AuctionApp.controllers.utility.SortCriteria;
import ba.abh.AuctionApp.controllers.utility.SortOrder;
import ba.abh.AuctionApp.domain.Auction;
import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.domain.enums.Size;
import ba.abh.AuctionApp.filters.AuctionFilter;
import ba.abh.AuctionApp.filters.ProductFilter;
import ba.abh.AuctionApp.filters.SortSpecification;
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
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auctions")
public class AuctionController {
    private static final String DAY_IN_MIN = "1440";
    private static final String MIN_PAGE = "0";
    private static final String MIN_SIZE = "10";

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
    public ResponseEntity<PageableResponse> getAuctions(@RequestParam(defaultValue = MIN_PAGE) int page,
                                                        @RequestParam(defaultValue = MIN_SIZE) int limit,
                                                        @RequestParam(required = false) Long sellerId,
                                                        @RequestParam(required = false) BigDecimal priceMin,
                                                        @RequestParam(required = false) BigDecimal priceMax,
                                                        @RequestParam(required = false) Size size,
                                                        @RequestParam(required = false) Long categoryId,
                                                        @RequestParam(required = false) String name,
                                                        @RequestParam(required = false) Long minutesLeft,
                                                        @RequestParam(required = false) SortCriteria sort,
                                                        @RequestParam(required = false) SortOrder sortOrder) {
        SortSpecification sortSpecification = new SortSpecification(sort, sortOrder);
        ProductFilter productFilter = new ProductFilter(name, size, categoryId);
        AuctionFilter auctionFilter = new AuctionFilter(sellerId,
                productFilter,
                priceMin,
                priceMax,
                minutesLeft,
                sortSpecification
        );
        Slice<Auction> slice = auctionService.getFilteredAuctions(page, limit, auctionFilter);
        PageableResponse response = buildPageableResponse(slice);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/featured")
    public ResponseEntity<PageableResponse> getFeaturedCategories(@RequestParam(defaultValue = MIN_PAGE) int page,
                                                                  @RequestParam(defaultValue = MIN_SIZE) int limit) {
        Slice<Auction> slice = auctionService.getFeaturedProducts(page, limit);
        PageableResponse response = buildPageableResponse(slice);
        return ResponseEntity.ok(response);
    }

    private User getUserFromPrincipal(final Principal principal) {
        return userService.getUserByEmail(principal.getName());
    }

    private PageableResponse buildPageableResponse(Slice<Auction> slice) {
        boolean hasPrevious = !slice.isFirst();
        boolean hasNext = slice.hasNext();
        int currentPage = slice.getNumber();
        int numberOfItemsOnPage = slice.getNumberOfElements();
        PaginationDetails details = new PaginationDetails(currentPage, hasNext, hasPrevious, numberOfItemsOnPage);
        List<? extends PageableEntity> data = slice
                .getContent()
                .stream()
                .map(AuctionResponse::new)
                .collect(Collectors.toList());
        return new PageableResponse(details, (List<PageableEntity>) data);
    }
}
