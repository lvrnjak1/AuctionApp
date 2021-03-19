package ba.abh.AuctionApp.filters;

import ba.abh.AuctionApp.domain.enums.Size;

import java.util.List;

public class ProductFilter {
    private String name;
    private Size size;
    private List<Long> categoryIds;

    public ProductFilter() {
    }

    public ProductFilter(final String name, final Size size, final List<Long> categoryIds) {
        this.name = name;
        this.size = size;
        this.categoryIds = categoryIds;
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

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(final List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }
}
