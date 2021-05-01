package ba.abh.AuctionApp.services;

import ba.abh.AuctionApp.domain.Auction;
import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.domain.Wishlist;
import ba.abh.AuctionApp.repositories.WishlistProjection;
import ba.abh.AuctionApp.repositories.WishlistRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final AuctionService auctionService;
    private final UserService userService;

    public WishlistService(final WishlistRepository wishlistRepository,
                           final AuctionService auctionService,
                           final UserService userService) {
        this.wishlistRepository = wishlistRepository;
        this.auctionService = auctionService;
        this.userService = userService;
    }

    public void toggleWishlist(final Long auctionId, final User user) {
        Auction auction = auctionService.getAuctionById(auctionId);
        Optional<Wishlist> optionalWishlist = wishlistRepository.findByAuctionAndUser(auction, user);
        if (optionalWishlist.isEmpty()) {
            Wishlist wishlist = new Wishlist(auction, user);
            wishlistRepository.save(wishlist);
        }else{
            wishlistRepository.delete(optionalWishlist.get());
        }
    }

    public Page<WishlistProjection> getWishlistForUser(final User user, final int page, final int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return wishlistRepository.findWishlistByUserDetailed(user, pageable);
    }

    public boolean isInWishlist(final Auction auction, final User user) {
        return wishlistRepository.findByAuctionAndUser(auction, user).isPresent();
    }
}
