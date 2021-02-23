package ba.abh.AuctionApp.services;

import ba.abh.AuctionApp.domain.Category;
import ba.abh.AuctionApp.exceptions.custom.ResourceNotFoundException;
import ba.abh.AuctionApp.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategoriesTree() {
        return categoryRepository.findAllByParentCategoryIsNull();
    }

    public Category findById(final Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " doesn't exist"));
    }
}
