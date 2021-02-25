package ba.abh.AuctionApp.domain;

import ba.abh.AuctionApp.domain.enums.Size;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Column(nullable = false)
    private String name;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private Size size;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_colors",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id"))
    private Set<Color> colors = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<ProductImage> images = new ArrayList<>();

    public Product() {
    }

    public Product(final String name, final String description) {
        this.name = name;
        this.description = description;
    }

    public Product(final Long id,
                   final String name,
                   final String description,
                   final Size size,
                   final Set<Color> colors,
                   final Category category,
                   final List<ProductImage> images) {
        super(id);
        this.name = name;
        this.description = description;
        this.size = size;
        this.colors = colors;
        this.category = category;
        this.images = images;
    }

    public Product(final String name,
                   final String description,
                   final Size size,
                   final Set<Color> colors,
                   final Category category) {
        this.name = name;
        this.description = description;
        this.size = size;
        this.colors = colors;
        this.category = category;
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

    public Size getSize() {
        return size;
    }

    public void setSize(final Size size) {
        this.size = size;
    }

    public Set<Color> getColors() {
        return colors;
    }

    public void setColors(final Set<Color> colors) {
        this.colors = colors;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public Product setImages(final List<ProductImage> images) {
        this.images = images;
        return this;
    }


}
