package ba.abh.AuctionApp.controllers;

import ba.abh.AuctionApp.requests.ProductImageRequest;
import ba.abh.AuctionApp.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/{productId}/images")
    @Secured("ROLE_SELLER")
    public ResponseEntity<?> addProductImage(@PathVariable Long productId,
                                             @RequestBody ProductImageRequest productImageRequest) {
        productService.saveImageForProduct(productId, productImageRequest.getImageUrl());
        return ResponseEntity.ok().build();
    }
}
