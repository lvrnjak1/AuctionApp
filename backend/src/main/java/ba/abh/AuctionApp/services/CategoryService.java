package ba.abh.AuctionApp.services;

import ba.abh.AuctionApp.domain.Category;
import ba.abh.AuctionApp.exceptions.custom.ResourceNotFoundException;
import ba.abh.AuctionApp.repositories.category.CategoryRepository;
import ba.abh.AuctionApp.repositories.category.ExtendedCategory;
import ba.abh.AuctionApp.responses.CategoryResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> getCategoriesTree() {
        Instant now = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        List<ExtendedCategory> queryResult = categoryRepository.findAllCategoriesWithActiveAuctionCount(now);
        List<CategoryResponse> categories = new ArrayList<>();
        for (ExtendedCategory ec : queryResult){
            Category category = ec.getCategory();
            Long productsPerCategory = ec.getCount();

            if(category.getParentCategory() == null){
                CategoryResponse c = getCategoryResponseFromCategory(category);
                categories.add(c);
            }else {
                final Optional<CategoryResponse> c = categories
                        .stream()
                        .filter(cr -> cr.getId().equals(category.getParentCategory().getId()))
                        .findFirst();
                CategoryResponse sc = getCategoryResponseFromCategory(category, productsPerCategory);
                c.ifPresent(categoryResponse -> categoryResponse.addSubcategory(sc));
            }
        }

        return categories;
    }

    public Category findById(final Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category with id %d doesn't exist", id)));
    }

    public List<Category> getFeaturedCategories(final int numberOfCategories) {
        Pageable pageable = PageRequest.of(0, numberOfCategories);
        return categoryRepository.findAllByParentCategoryIsNotNull(pageable);
    }

    private CategoryResponse getCategoryResponseFromCategory(final Category category, final Long numberOfProducts){
        return new CategoryResponse(category.getId(), category.getName(), category.getImageUrl(), numberOfProducts);
    }

    private CategoryResponse getCategoryResponseFromCategory(final Category category){
        return new CategoryResponse(category.getId(), category.getName(), category.getImageUrl());
    }
}
