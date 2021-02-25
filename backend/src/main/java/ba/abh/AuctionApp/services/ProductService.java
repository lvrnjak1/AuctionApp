package ba.abh.AuctionApp.services;

import ba.abh.AuctionApp.domain.Product;
import ba.abh.AuctionApp.domain.ProductImage;
import ba.abh.AuctionApp.exceptions.custom.ResourceNotFoundException;
import ba.abh.AuctionApp.repositories.ProductImageRepository;
import ba.abh.AuctionApp.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    public ProductService(final ProductRepository productRepository,
                          final ProductImageRepository productImageRepository) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
    }

    public Product save(final Product product) {
        return productRepository.save(product);
    }

    public Product findProductById(Long productId) {
        return productRepository
                .findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + productId + " doesn't exist"));
    }

    public void saveImageForProduct(Long productId, String imageUrl) {
        Product product = findProductById(productId);
        ProductImage productImage = new ProductImage(imageUrl, product);
        productImageRepository.save(productImage);
        product.getImages().add(productImage);
    }
}
