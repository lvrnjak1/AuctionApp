package ba.abh.AuctionApp.controllers;

import ba.abh.AuctionApp.domain.Category;
import ba.abh.AuctionApp.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getCategoriesTree());
    }

    @GetMapping("/featured")
    public ResponseEntity<List<Category>> getFeaturedCategories(@RequestParam(defaultValue = "3") int limit) {
        List<Category> categories = categoryService.getFeaturedCategories(limit);
        return ResponseEntity.ok(categories);
    }
}
