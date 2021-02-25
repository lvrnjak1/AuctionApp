package ba.abh.AuctionApp.requests;

import ba.abh.AuctionApp.domain.enums.Size;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ProductRequest {
    @NotBlank(message = "Name must be present")
    private String name;

    private String description;

    @NotNull(message = "Category id must be present")
    private Long categoryId;

    @NotNull(message = "Size must be present")
    private Size size;

    @NotEmpty(message = "Provide at least one color")
    private List<Long> colors;

    private List<String> images;

    public ProductRequest(final String name,
                          final String description,
                          final Long categoryId,
                          final Size size,
                          final List<Long> colors,
                          final List<String> images) {
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.size = size;
        this.colors = colors;
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(final Long categoryId) {
        this.categoryId = categoryId;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(final Size size) {
        this.size = size;
    }

    public List<Long> getColors() {
        return colors;
    }

    public void setColors(final List<Long> colors) {
        this.colors = colors;
    }

    public List<String> getImages() {
        return images;
    }

    public ProductRequest setImages(final List<String> images) {
        this.images = images;
        return this;
    }
}
