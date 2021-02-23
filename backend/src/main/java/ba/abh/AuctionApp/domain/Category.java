package ba.abh.AuctionApp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Category parentCategory;

    public Category() {
    }

    public Category(Long id, String name, Category parentCategory) {
        super(id);
        this.name = name;
        this.parentCategory = parentCategory;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name) &&
                Objects.equals(parentCategory, category.parentCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, parentCategory);
    }
}
