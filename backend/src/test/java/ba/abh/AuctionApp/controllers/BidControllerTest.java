package ba.abh.AuctionApp.controllers;

import ba.abh.AuctionApp.repositories.ProductRepository;
import ba.abh.AuctionApp.repositories.UserRepository;
import ba.abh.AuctionApp.repositories.auction.AuctionRepository;
import ba.abh.AuctionApp.repositories.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class BidControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

//    @BeforeEach
//    public void setUp() {
//       if(bidder != null) userRepository.delete(bidder);
//        bidder = createUser();
//    }

//    @Test
//    public void testCreate() throws Exception {
//        User seller = userRepository.findByEmail("miki.maus@gmail.com").orElseThrow();
//        Auction auction = createAuction(seller, "Product 0");
//        User bidder = userRepository.findByEmail("lamija.vrnjak@gmail.com").orElseThrow();
//        mockMvc.perform(post(String.format("/auctions/%s/bids", auction.getId()))
//                .with(user(bidder.getEmail()).password("password").roles("BUYER"))
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(String.format("{\n" +
//                        "    \"dateTime\":%s,\n" +
//                        "    \"amount\": 100,\n" +
//                        "}", Instant.now().toEpochMilli())))
//                .andExpect(status().isCreated());
//    }
//
//    @Test
//    public void testBidTooLow() throws Exception {
//        User seller = userRepository.findByEmail("miki.maus@gmail.com").orElseThrow();
//        Auction auction = createAuction(seller, "Product 1");
//        User bidder = userRepository.findByEmail("lamija.vrnjak@gmail.com").orElseThrow();
//        mockMvc.perform(post(String.format("/auctions/%s/bids", auction.getId()))
//                .with(user(bidder.getEmail()).password("password").roles("BUYER"))
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(String.format("{\n" +
//                        "    \"dateTime\":%s,\n" +
//                        "    \"amount\": 20,\n" +
//                        "}", Instant.now().toEpochMilli())))
//                .andExpect(status().isUnprocessableEntity())
//                .andExpect(jsonPath("message", is(String.format("You must place a bid higher than %.2f", auction.getStartPrice()))));
//
//        productRepository.deleteById(auction.getProduct().getId());
//        auctionRepository.delete(auction);
//    }
//
//    @Test
//    public void testAlreadyHighestBidder() throws Exception {
//        User seller = userRepository.findByEmail("miki.maus@gmail.com").orElseThrow();
//        User bidder = userRepository.findByEmail("lamija.vrnjak@gmail.com").orElseThrow();
//        Auction auction = createAuction(seller, "Product 2");
//        mockMvc.perform(post(String.format("/auctions/%s/bids", auction.getId()))
//                .with(user(bidder.getEmail()).password("password").roles("BUYER"))
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(String.format("{\n" +
//                        "    \"dateTime\":%s,\n" +
//                        "    \"amount\": 200,\n" +
//                        "}", Instant.now().toEpochMilli())))
//                .andExpect(status().isCreated());
//
//        mockMvc.perform(post(String.format("/auctions/%s/bids", auction.getId()))
//                .with(user(bidder.getEmail()).password("password123").roles("BUYER"))
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(String.format("{\n" +
//                        "    \"dateTime\":%s,\n" +
//                        "    \"amount\": 201,\n" +
//                        "}", Instant.now().toEpochMilli())))
//                .andExpect(status().isUnprocessableEntity())
//                .andExpect(jsonPath("message", is("You are already the highest bidder")));
//    }
//
////    private User createUser() {
////        User user = new User("Neko", "Nekic", "neko@gmail.com", "password123");
////        return userRepository.save(user);
////    }
//
//    private Auction createAuction(User user, String name) {
//        Category category = categoryRepository.findAllByParentCategoryIsNotNull(PageRequest.of(0, 1)).get(0);
//        Product product = new Product(name, "Description", Size.LARGE, new HashSet<>(), category);
//        Auction auction = new Auction(product,
//                user,
//                Instant.now(),
//                LocalDateTime.now().plusHours(7).toInstant(ZoneOffset.UTC),
//                75.50
//        );
//
//        return auctionRepository.save(auction);
//    }
}
