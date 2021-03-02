package ba.abh.AuctionApp.domain;

import ba.abh.AuctionApp.domain.enums.ColorName;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "colors")
public class Color extends BaseEntity {
    @Enumerated(value = EnumType.STRING)
    @NaturalId
    private ColorName color;

    public Color() {
    }

    public Color(final ColorName color) {
        this.color = color;
    }

    public ColorName getColor() {
        return color;
    }

    public void setColor(final ColorName color) {
        this.color = color;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Color color1 = (Color) o;
        return color == color1.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), color);
    }
}
