package ba.abh.AuctionApp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    @Column(nullable = false)
    private String name;

    private int cLeft;
    private int cRight;

    public Category() {
    }

    public Category(final Long id,
                    final String name,
                    final int cLeft,
                    final int cRight) {
        super(id);
        this.name = name;
        this.cLeft = cLeft;
        this.cRight = cRight;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getcLeft() {
        return cLeft;
    }

    public void setcLeft(final int cLeft) {
        this.cLeft = cLeft;
    }

    public int getcRight() {
        return cRight;
    }

    public void setcRight(final int cRight) {
        this.cRight = cRight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Category category = (Category) o;
        return cLeft == category.cLeft &&
                cRight == category.cRight &&
                Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, cLeft, cRight);
    }
}
