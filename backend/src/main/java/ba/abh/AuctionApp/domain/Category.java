package ba.abh.AuctionApp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    private List<Category> subCategories = new ArrayList<>();

    private String imageUrl;

    public Category() {
    }

    public Category(final Long id, final String name, final Category parentCategory, final String imageUrl) {
        super(id);
        this.name = name;
        this.parentCategory = parentCategory;
        this.imageUrl = imageUrl;
    }

    public Category(final Long id,
                    final String name,
                    final Category parentCategory,
                    final List<Category> subCategories,
                    final String imageUrl) {
        super(id);
        this.name = name;
        this.parentCategory = parentCategory;
        this.subCategories = subCategories;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(final Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(final List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name) &&
                Objects.equals(parentCategory, category.parentCategory) &&
                Objects.equals(subCategories, category.subCategories) &&
                Objects.equals(imageUrl, category.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, parentCategory, subCategories, imageUrl);
    }
}
