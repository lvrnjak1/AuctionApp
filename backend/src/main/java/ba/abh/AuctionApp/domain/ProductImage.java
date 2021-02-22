package ba.abh.AuctionApp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "images")
public class ProductImage extends BaseEntity {
    @Lob
    @Column(nullable = false)
    private byte[] image;

    @ManyToOne
    private Product product;

    public ProductImage() {
    }

    public ProductImage(final Long id, final byte[] image) {
        super(id);
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(final byte[] image) {
        this.image = image;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(final Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProductImage that = (ProductImage) o;
        return Arrays.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode());
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }
}
