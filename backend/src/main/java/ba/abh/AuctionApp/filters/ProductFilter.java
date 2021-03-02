package ba.abh.AuctionApp.filters;

import ba.abh.AuctionApp.domain.enums.Size;

public class ProductFilter {
    private String name;
    private Size size;
    private Long categoryId;

    public ProductFilter() {
    }

    public ProductFilter(final String name, final Size size, final Long categoryId) {
        this.name = name;
        this.size = size;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(final Size size) {
        this.size = size;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(final Long categoryId) {
        this.categoryId = categoryId;
    }
}
