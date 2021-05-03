package ba.abh.AuctionApp.controllers;

import ba.abh.AuctionApp.controllers.utility.RequestParams;
import ba.abh.AuctionApp.domain.Auction;
import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.filters.AuctionFilter;
import ba.abh.AuctionApp.filters.ProductFilter;
import ba.abh.AuctionApp.filters.SortSpecification;
import ba.abh.AuctionApp.pagination.PaginationDetails;
import ba.abh.AuctionApp.repositories.wishlist.WishlistProjection;
import ba.abh.AuctionApp.requests.AuctionRequest;
import ba.abh.AuctionApp.responses.AuctionResponse;
import ba.abh.AuctionApp.responses.AuctionSearchResponse;
import ba.abh.AuctionApp.responses.DetailedWishlistResponse;
import ba.abh.AuctionApp.responses.PageableResponse;
import ba.abh.AuctionApp.responses.PriceChartResponse;
import ba.abh.AuctionApp.services.AuctionService;
import ba.abh.AuctionApp.services.UserService;
import ba.abh.AuctionApp.services.WishlistService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private final WishlistService wishlistService;

    public AuctionController(final AuctionService auctionService, final UserService userService,
                             final WishlistService wishlistService) {
        this.auctionService = auctionService;
        this.userService = userService;
        this.wishlistService = wishlistService;
    }

    @PostMapping
    public ResponseEntity<AuctionResponse> createAuction(@Valid @RequestBody final AuctionRequest auctionRequest,
                                                         final Principal principal) {
        User user = userService.getUserFromPrincipal(principal);
        Auction auction = auctionService.createAuction(auctionRequest, user);
        userService.addRole(user, "ROLE_SELLER");
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuctionResponse(auction));
    }

    @GetMapping
    public ResponseEntity<AuctionSearchResponse> getAuctions(@Valid final RequestParams requestParams, final Principal principal) {
        AuctionFilter auctionFilter = constructAuctionFilter(requestParams);
        Page<Auction> auctionPage = auctionService.getFilteredAuctions(requestParams.getPage() - 1,
                requestParams.getLimit(),
                auctionFilter
        );

        String suggestion = null;
        if(auctionPage.getContent().size() == 0 && requestParams.getName() != null) {
            suggestion = auctionService.suggest(requestParams.getName());
            if(suggestion != null && suggestion.equals(requestParams.getName())) suggestion = null;
        }
        AuctionSearchResponse response = buildPageableResponse(auctionPage, suggestion, principal);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/featured")
    public ResponseEntity<PageableResponse<AuctionResponse>> getFeaturedAuctions(@Valid final RequestParams requestParams,
                                                                                 final Principal principal) {
        Page<Auction> auctionPage = auctionService.getFeaturedProducts(requestParams.getPage() - 1, requestParams.getLimit());
        PageableResponse<AuctionResponse> response = buildPageableResponse(auctionPage, principal);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuctionResponse> getAuctionById(@PathVariable final Long id, Principal principal) {
        Auction auction = auctionService.getAuctionById(id);
        AuctionResponse auctionResponse = getAuctionResponse(auction, principal);
        return ResponseEntity.ok(auctionResponse);
    }

    @GetMapping("/price-chart")
    public ResponseEntity<?> getPriceChartData(@Valid final RequestParams requestParam) {
        AuctionFilter auctionFilter = constructAuctionFilter(requestParam);
        PriceChartResponse priceChartResponse = auctionService.getChartData(auctionFilter);
        return ResponseEntity.ok().body(priceChartResponse);
    }

    @PutMapping("/{id}/wishlist")
    public ResponseEntity<?> toggleWishlist(@PathVariable final Long id, final Principal principal) {
        wishlistService.toggleWishlist(id, userService.getUserByEmail(principal.getName()));
        Auction auction = auctionService.getAuctionById(id);
        return ResponseEntity.ok(getAuctionResponse(auction, principal));
    }

    @GetMapping("/wishlist")
    public ResponseEntity<?> getUserWishlist(final Principal principal, @Valid final RequestParams requestParams) {
        User user = userService.getUserByEmail(principal.getName());
        Page<WishlistProjection> wishlist = wishlistService.getWishlistForUser(user, requestParams.getPage() - 1, requestParams.getLimit());
        return ResponseEntity.ok().body(buildWishlistResponse(wishlist));
    }

    private PageableResponse<AuctionResponse> buildPageableResponse(final Page<Auction> page, final Principal principal) {
        PaginationDetails details = new PaginationDetails(page);
        final List<AuctionResponse> data = page
                .getContent()
                .stream()
                .map((auction) -> getAuctionResponse(auction, principal))
                .collect(Collectors.toUnmodifiableList());
        return new PageableResponse<>(details, data);
    }

    private AuctionSearchResponse buildPageableResponse(final Page<Auction> page, final String suggestion, final Principal principal) {
        PaginationDetails details = new PaginationDetails(page);
        final List<AuctionResponse> data = page
                .getContent()
                .stream()
                .map((auction) -> getAuctionResponse(auction, principal))
                .collect(Collectors.toUnmodifiableList());
        return new AuctionSearchResponse(new PageableResponse<>(details, data), suggestion);
    }

    private PageableResponse<DetailedWishlistResponse> buildWishlistResponse(final Page<WishlistProjection> page) {
        PaginationDetails details = new PaginationDetails(page);
        final List<DetailedWishlistResponse> data = page
                .getContent()
                .stream()
                .map(wishlistProjection -> new DetailedWishlistResponse(wishlistProjection.getAuction(),
                        wishlistProjection.getHighestBid(),
                        wishlistProjection.getYourPrice()))
                .collect(Collectors.toUnmodifiableList());
        return new PageableResponse<>(details, data);
    }

    private AuctionFilter constructAuctionFilter(final RequestParams requestParams) {
        SortSpecification sortSpecification = new SortSpecification(requestParams.getSort(), requestParams.getSortOrder());
        ProductFilter productFilter = new ProductFilter(requestParams.getName(), requestParams.getSize(), requestParams.getCategoryId());

        return new AuctionFilter(requestParams.getSellerId(),
                productFilter,
                requestParams.getPriceMin(),
                requestParams.getPriceMax(),
                requestParams.getMinutesLeft(),
                sortSpecification
        );
    }

    private AuctionResponse getAuctionResponse(final Auction auction, final Principal principal) {
        boolean isInWishlist = false;
        if (principal != null) {
            isInWishlist = wishlistService.isInWishlist(auction, userService.getUserByEmail(principal.getName()));
        }

        AuctionResponse auctionResponse = new AuctionResponse(auction);
        auctionResponse.setWishlist(isInWishlist);
        return auctionResponse;
    }
}
