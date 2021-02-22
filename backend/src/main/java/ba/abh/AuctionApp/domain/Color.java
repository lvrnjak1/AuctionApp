package ba.abh.AuctionApp.domain;

import ba.abh.AuctionApp.domain.enums.ColorName;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "colors")
public class Color extends BaseEntity{
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
}
