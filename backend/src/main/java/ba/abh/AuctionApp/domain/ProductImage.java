package ba.abh.AuctionApp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "images")
public class ProductImage extends BaseEntity {
    @Column(nullable = false)
    private String imageUrl;

    @JsonIgnore
    @ManyToOne
    private Product product;

    public ProductImage() {
    }

    public ProductImage(final Long id, final String imageUrl, final Product product) {
        super(id);
        this.imageUrl = imageUrl;
        this.product = product;
    }

    public ProductImage(final String imageUrl, final Product product) {
        super();
        this.imageUrl = imageUrl;
        this.product = product;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(final Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProductImage that = (ProductImage) o;
        return Objects.equals(imageUrl, that.imageUrl) &&
                Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), imageUrl, product);
    }
}
