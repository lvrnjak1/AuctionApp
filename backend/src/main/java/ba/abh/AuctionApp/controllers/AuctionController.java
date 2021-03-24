package ba.abh.AuctionApp.controllers;

import ba.abh.AuctionApp.controllers.utility.RequestParams;
import ba.abh.AuctionApp.domain.Auction;
import ba.abh.AuctionApp.filters.AuctionFilter;
import ba.abh.AuctionApp.filters.ProductFilter;
import ba.abh.AuctionApp.filters.SortSpecification;
import ba.abh.AuctionApp.pagination.PageableEntity;
import ba.abh.AuctionApp.pagination.PaginationDetails;
import ba.abh.AuctionApp.requests.AuctionRequest;
import ba.abh.AuctionApp.responses.AuctionResponse;
import ba.abh.AuctionApp.responses.PageableResponse;
import ba.abh.AuctionApp.responses.PriceChartResponse;
import ba.abh.AuctionApp.services.AuctionService;
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
    public ResponseEntity<AuctionResponse> createAuction(@Valid @RequestBody final AuctionRequest auctionRequest,
                                                         final Principal principal) {
        Auction auction = auctionService.createAuction(auctionRequest, userService.getUserFromPrincipal(principal));
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuctionResponse(auction));
    }

    @GetMapping
    public ResponseEntity<PageableResponse> getAuctions(@Valid final RequestParams requestParams) {
        AuctionFilter auctionFilter = constructAuctionFilter(requestParams);
        Page<Auction> auctionPage = auctionService.getFilteredAuctions(requestParams.getPage() - 1,
                requestParams.getLimit(),
                auctionFilter
        );
        PageableResponse response = buildPageableResponse(auctionPage);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/featured")
    public ResponseEntity<PageableResponse> getFeaturedCategories(@Valid final RequestParams requestParams) {
        Page<Auction> auctionPage = auctionService.getFeaturedProducts(requestParams.getPage() - 1, requestParams.getLimit());
        PageableResponse response = buildPageableResponse(auctionPage);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuctionResponse> getAuctionById(@PathVariable final Long id) {
        Auction auction = auctionService.getActiveByIdIfExists(id);
        return ResponseEntity.ok(new AuctionResponse(auction));
    }

    @GetMapping("/price-chart")
    public ResponseEntity<?> getPriceChartData(@Valid RequestParams requestParam) {
        AuctionFilter auctionFilter = constructAuctionFilter(requestParam);
        PriceChartResponse priceChartResponse = auctionService.getChartData(auctionFilter);
        return ResponseEntity.ok().body(priceChartResponse);
    }

    private PageableResponse buildPageableResponse(final Page<Auction> page) {
        PaginationDetails details = new PaginationDetails(page);
        List<? extends PageableEntity> data = page
                .getContent()
                .stream()
                .map(AuctionResponse::new)
                .collect(Collectors.toList());
        return new PageableResponse(details, (List<PageableEntity>) data);
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
}
