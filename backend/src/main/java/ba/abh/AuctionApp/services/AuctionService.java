package ba.abh.AuctionApp.services;

import ba.abh.AuctionApp.domain.Auction;
import ba.abh.AuctionApp.domain.Category;
import ba.abh.AuctionApp.domain.Color;
import ba.abh.AuctionApp.domain.Product;
import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.domain.enums.Size;
import ba.abh.AuctionApp.exceptions.custom.InvalidDateException;
import ba.abh.AuctionApp.filters.AuctionFilter;
import ba.abh.AuctionApp.repositories.ColorRepository;
import ba.abh.AuctionApp.repositories.auction.AuctionRepository;
import ba.abh.AuctionApp.requests.AuctionRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
                auctionRequest.getStartDateTime().isBefore(ZonedDateTime.now().toInstant())) {
            throw new InvalidDateException("Invalid start or end date");
        }

        Auction auction = getAuctionFromAuctionRequest(auctionRequest, seller);
        Product product = productService.save(auction.getProduct());
        auctionRepository.save(auction);
        saveImagesForProduct(auctionRequest.getProduct().getImages(), product.getId());
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

    private void saveImagesForProduct(final List<String> imageUrls, Long productId) {
        if (imageUrls != null) {
            imageUrls.forEach(imageUrl -> productService.saveImageForProduct(productId, imageUrl));
        }
    }

    private Slice<Auction> getActiveAuctions(final int page, final int size, final Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        return auctionRepository.findActiveAuctions(ZonedDateTime.now().toInstant(), pageable);
    }

    private Slice<Auction> getActiveAuctions(final int page, final int size) {
        Pageable pageable = PageRequest.of(page, size);
        return auctionRepository.findActiveAuctions(ZonedDateTime.now().toInstant(), pageable);
    }

    private Slice<Auction> getActiveAuctionsWithinCategory(final int page, final int size, Long categoryId) {
        Pageable pageable = PageRequest.of(page, size);
        return auctionRepository.findActiveAuctionsByCategoryId(ZonedDateTime.now().toInstant(), categoryId, pageable);
    }

    public Slice<Auction> getAuctions(final int page, final int size, final Long categoryId) {
        if(categoryId != null){
            return getActiveAuctionsWithinCategory(page, size, categoryId);
        }else{
            return getActiveAuctions(page, size);
        }
    }

    public Slice<Auction> getNewestAuctions(final int page, final int size) {
        return getActiveAuctions(page, size, Sort.by("startDateTime").descending());
    }

    public Slice<Auction> getFeaturedProducts(final int page, final int size) {
        //implement different algorithm for featured
        return getActiveAuctions(page, size);
    }

    public Slice<Auction> getLastChance(final int page, final int size, final int durationInMins) {
        Pageable pageable = PageRequest.of(page, size);
        Instant now = ZonedDateTime.now().toInstant();
        Instant end = ZonedDateTime.now().plusMinutes(durationInMins).toInstant();
        return auctionRepository.findByStartDateTimeBeforeAndEndDateTimeBetween(now, now, end, pageable);
    }

    public Slice<Auction> getFilteredAuctions(int page, int size, AuctionFilter auctionFilter) {
        Instant now = ZonedDateTime.now().toInstant();
        auctionFilter.setStartBefore(now);
        auctionFilter.setEndAfter(now);
        Pageable pageable = PageRequest.of(page, size);
        return auctionRepository.findAllByFilter(auctionFilter, pageable);
    }
}
