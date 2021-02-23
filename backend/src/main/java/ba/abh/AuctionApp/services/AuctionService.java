package ba.abh.AuctionApp.services;

import ba.abh.AuctionApp.domain.Auction;
import ba.abh.AuctionApp.domain.Category;
import ba.abh.AuctionApp.domain.Color;
import ba.abh.AuctionApp.domain.Product;
import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.domain.enums.Size;
import ba.abh.AuctionApp.repositories.AuctionRepository;
import ba.abh.AuctionApp.repositories.ColorRepository;
import ba.abh.AuctionApp.requests.AuctionRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

    public Slice<Auction> getAuctions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return auctionRepository.findAll(pageable);
    }

    public List<Auction> getFeaturedProducts(int numberOfProducts) {
        Pageable pageable = PageRequest.of(0, numberOfProducts);
        return auctionRepository.findAll(pageable).getContent();
    }

    public Slice<Auction> getNewestAuctions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("startDateTime").ascending());
        return auctionRepository.findAll(pageable);
    }

    public List<Auction> getLastChance(int limit, int durationInMins) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("endDateTime").ascending());
        return auctionRepository
                .findAll(pageable)
                .stream()
                .filter(auction -> {
                    long diffMs = auction.getEndDateTime().getTime() - new Date().getTime();
                    long diffMin = TimeUnit.MINUTES.convert(diffMs, TimeUnit.MILLISECONDS);
                    return diffMin <= durationInMins;
                })
                .collect(Collectors.toList());
    }
}
