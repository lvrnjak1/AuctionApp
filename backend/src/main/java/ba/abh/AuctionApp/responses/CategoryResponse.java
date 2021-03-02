package ba.abh.AuctionApp.responses;

import java.util.ArrayList;
import java.util.List;

public class CategoryResponse {
    private Long id;
    private String name;
    private List<CategoryResponse> subcategories;
    private String imageUrl;
    private Long numberOfProducts;

    public CategoryResponse(final Long id,
                            final String name,
                            final List<CategoryResponse> subcategories,
                            final String imageUrl,
                            final Long numberOfProducts) {
        this.id = id;
        this.name = name;
        this.subcategories = subcategories;
        this.imageUrl = imageUrl;
        this.numberOfProducts = numberOfProducts;
    }

    public CategoryResponse(final Long id, final String name, final String imageUrl, final Long numberOfProducts) {
        this.id = id;
        this.name = name;
        this.subcategories = new ArrayList<>();
        this.imageUrl = imageUrl;
        this.numberOfProducts = numberOfProducts;
    }

    public CategoryResponse(final Long id, final String name, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.subcategories = new ArrayList<>();
        this.imageUrl = imageUrl;
        this.numberOfProducts = 0L;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<CategoryResponse> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(final List<CategoryResponse> subcategories) {
        this.subcategories = subcategories;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(final Long numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    public void addSubcategory(final CategoryResponse sc) {
        subcategories.add(sc);
        numberOfProducts += sc.getNumberOfProducts();
    }
}