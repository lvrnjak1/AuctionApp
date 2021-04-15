package ba.abh.AuctionApp.services;

import ba.abh.AuctionApp.domain.Product;
import ba.abh.AuctionApp.domain.ProductImage;
import ba.abh.AuctionApp.exceptions.custom.ResourceNotFoundException;
import ba.abh.AuctionApp.repositories.ProductImageRepository;
import ba.abh.AuctionApp.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Product findProductById(final Long productId) {
        return productRepository
                .findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Product with id %d doesn't exist", productId)));
    }

    public void saveImageForProduct(final Long productId, final String imageUrl) {
        Product product = findProductById(productId);
        ProductImage productImage = new ProductImage(imageUrl, product);
        productImageRepository.save(productImage);
        product.getImages().add(productImage);
    }

    public void saveImages(final List<ProductImage> productImages) {
        productImageRepository.saveAll(productImages);
    }
}
