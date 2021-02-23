package ba.abh.AuctionApp.services;

import ba.abh.AuctionApp.domain.Product;
import ba.abh.AuctionApp.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void save(final Product product) {
        productRepository.save(product);
    }
}
