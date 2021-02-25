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

    public ProductImage(String imageUrl) {
        super();
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ProductImage setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public ProductImage setProduct(final Product product) {
        this.product = product;
        return this;
    }

    @Override
    public boolean equals(Object o) {
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
