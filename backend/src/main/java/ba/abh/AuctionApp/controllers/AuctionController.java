package ba.abh.AuctionApp.controllers;

import ba.abh.AuctionApp.controllers.utility.SortCriteria;
import ba.abh.AuctionApp.controllers.utility.SortOrder;
import ba.abh.AuctionApp.domain.Auction;
import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.domain.enums.Size;
import ba.abh.AuctionApp.exceptions.custom.InvalidPaginationException;
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
import org.springframework.data.domain.Page;
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
    private static final String MIN_PAGE = "1";
    private static final String MIN_LIMIT = "10";

    private final AuctionService auctionService;
    private final UserService userService;

    public AuctionController(final AuctionService auctionService, final UserService userService) {
        this.auctionService = auctionService;
        this.userService = userService;
    }

    @PostMapping
    @Secured("ROLE_SELLER")
    public ResponseEntity<AuctionResponse> createAuction(@Valid @RequestBody final AuctionRequest auctionRequest,
                                                         final Principal principal) {
        Auction auction = auctionService.createAuction(auctionRequest, getUserFromPrincipal(principal));
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuctionResponse(auction));
    }

    @GetMapping
    public ResponseEntity<PageableResponse> getAuctions(@RequestParam(defaultValue = MIN_PAGE) final int page,
                                                        @RequestParam(defaultValue = MIN_LIMIT) final int limit,
                                                        @RequestParam(required = false) final Long sellerId,
                                                        @RequestParam(required = false) final Double priceMin,
                                                        @RequestParam(required = false) final Double priceMax,
                                                        @RequestParam(required = false) final Size size,
                                                        @RequestParam(required = false) final Long categoryId,
                                                        @RequestParam(required = false) final String name,
                                                        @RequestParam(required = false) final Long minutesLeft,
                                                        @RequestParam(required = false) final SortCriteria sort,
                                                        @RequestParam(required = false) final SortOrder sortOrder) {
        checkPagination(page, limit);
        SortSpecification sortSpecification = new SortSpecification(sort, sortOrder);
        ProductFilter productFilter = new ProductFilter(name, size, categoryId);
        AuctionFilter auctionFilter = new AuctionFilter(sellerId,
                productFilter,
                priceMin,
                priceMax,
                minutesLeft,
                sortSpecification
        );
        Page<Auction> auctionPage = auctionService.getFilteredAuctions(page - 1, limit, auctionFilter);
        PageableResponse response = buildPageableResponse(auctionPage);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/featured")
    public ResponseEntity<PageableResponse> getFeaturedCategories(@RequestParam(defaultValue = MIN_PAGE) final int page,
                                                                  @RequestParam(defaultValue = MIN_LIMIT) final int limit) {
        checkPagination(page, limit);
        Page<Auction> auctionPage = auctionService.getFeaturedProducts(page - 1, limit);
        PageableResponse response = buildPageableResponse(auctionPage);
        return ResponseEntity.ok(response);
    }

    private User getUserFromPrincipal(final Principal principal) {
        return userService.getUserByEmail(principal.getName());
    }

    private PageableResponse buildPageableResponse(final Page<Auction> page) {
        boolean hasPrevious = !page.isFirst();
        boolean hasNext = page.hasNext();
        int currentPage = page.getNumber() + 1;
        int numberOfItemsOnPage = page.getNumberOfElements();
        long available = page.getTotalElements();
        PaginationDetails details = new PaginationDetails(currentPage, hasNext, hasPrevious, numberOfItemsOnPage, available);
        List<? extends PageableEntity> data = page
                .getContent()
                .stream()
                .map(AuctionResponse::new)
                .collect(Collectors.toList());
        return new PageableResponse(details, (List<PageableEntity>) data);
    }

    private void checkPagination(final int page, final int limit){
        if(page < 1 || limit < 1){
            throw new InvalidPaginationException("Page index should start at 1, and limit should be at least 1");
        }
    }
}
