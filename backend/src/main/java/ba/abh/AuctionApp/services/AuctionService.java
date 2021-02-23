package ba.abh.AuctionApp.services;

import ba.abh.AuctionApp.domain.Auction;
import ba.abh.AuctionApp.domain.Category;
import ba.abh.AuctionApp.domain.Color;
import ba.abh.AuctionApp.domain.Product;
import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.domain.enums.Size;
import ba.abh.AuctionApp.exceptions.custom.InvalidDateException;
import ba.abh.AuctionApp.repositories.AuctionRepository;
import ba.abh.AuctionApp.repositories.ColorRepository;
import ba.abh.AuctionApp.requests.AuctionRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@Service
public class AuctionService {
    private final ColorRepository colorRepository;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final AuctionRepository auctionRepository;

    public AuctionService(final ColorRepository colorRepository,
                          final CategoryService categoryService,
                          final ProductService productService,
                          final AuctionRepository auctionRepository) {
        this.colorRepository = colorRepository;
        this.categoryService = categoryService;
        this.productService = productService;
        this.auctionRepository = auctionRepository;
    }

    public Auction createAuction(AuctionRequest auctionRequest, User seller) {
        if (auctionRequest.getStartDateTime().isAfter(auctionRequest.getEndDateTime()) ||
                auctionRequest.getStartDateTime().isBefore(ZonedDateTime.now())){
            throw new InvalidDateException("Invalid start or end date");
        }

        Auction auction = getAuctionFromAuctionRequest(auctionRequest, seller);
        productService.save(auction.getProduct());
        auctionRepository.save(auction);
        return auction;
    }

    private Auction getAuctionFromAuctionRequest(final AuctionRequest auctionRequest, final User seller) {
        Size size = auctionRequest.getProduct().getSize();
        Set<Color> colors = colorRepository.getColorByIdIn(auctionRequest.getProduct().getColors());
        Category category = categoryService.findById(auctionRequest.getProduct().getCategoryId());

        Product product = new Product(auctionRequest.getProduct().getName(),
                auctionRequest.getProduct().getDescription(),
                size,
                colors,
                category
        );

        return new Auction(product,
                seller,
                auctionRequest.getStartDateTime(),
                auctionRequest.getEndDateTime(),
                auctionRequest.getStartPrice()
        );
    }

    private Slice<Auction> getActiveAuctions(int page, int size, Sort sort){
        Pageable pageable = PageRequest.of(page, size, sort);
        return auctionRepository.findByStartDateTimeBeforeAndEndDateTimeAfter(ZonedDateTime.now(), pageable);
    }

    private Slice<Auction> getActiveAuctions(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return auctionRepository.findByStartDateTimeBeforeAndEndDateTimeAfter(ZonedDateTime.now(), pageable);
    }

    public Slice<Auction> getAuctions(int page, int size) {
        return getActiveAuctions(page, size);
    }

    public Slice<Auction> getNewestAuctions(int page, int size) {
        return getActiveAuctions(page, size, Sort.by("startDateTime").descending());
    }

    public List<Auction> getFeaturedProducts(int numberOfProducts) {
        //implement different algorithm for featured
        return getActiveAuctions(0, numberOfProducts).getContent();
    }

    public List<Auction> getLastChance(int limit, int durationInMins) {
        Pageable pageable = PageRequest.of(0, limit);
        ZonedDateTime now = ZonedDateTime.now();
        return auctionRepository
                .findByStartDateTimeBeforeAndEndDateTimeBetween(now, now, now.plusMinutes(durationInMins), pageable)
                .getContent();
    }
}
