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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

    public Auction createAuction(final AuctionRequest auctionRequest, final User seller) {
        if (auctionRequest.getInstantStartDateTime().isAfter(auctionRequest.getInstantEndDateTime()) ||
                auctionRequest.getInstantStartDateTime().isBefore(LocalDateTime.now().toInstant(ZoneOffset.UTC))) {
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
                auctionRequest.getInstantStartDateTime(),
                auctionRequest.getInstantEndDateTime(),
                auctionRequest.getStartPrice()
        );
    }

    private void saveImagesForProduct(final List<String> imageUrls, Long productId) {
        if (imageUrls != null) {
            imageUrls.forEach(imageUrl -> productService.saveImageForProduct(productId, imageUrl));
        }
    }

    private Page<Auction> getActiveAuctions(final int page, final int size) {
        Pageable pageable = PageRequest.of(page, size);
        return auctionRepository.findActiveAuctions(LocalDateTime.now().toInstant(ZoneOffset.UTC), pageable);
    }

    public Page<Auction> getFeaturedProducts(final int page, final int size) {
        //implement different algorithm for featured
        return getActiveAuctions(page, size);
    }

    public Page<Auction> getFilteredAuctions(int page, int size, AuctionFilter auctionFilter) {
        Instant now = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        auctionFilter.setStartBefore(now);
        auctionFilter.setEndAfter(now);
        Pageable pageable = PageRequest.of(page, size);
        return auctionRepository.findAllByFilter(auctionFilter, pageable);
    }

    public Auction getByIdIfExists(final Long id) {
        return auctionRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Auction with id %d doesn't exist", id))
        );
    }
}
