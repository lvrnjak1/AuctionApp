package ba.abh.AuctionApp.filters;

import java.math.BigDecimal;

public class AuctionFilter {
    private Long sellerId;
    private ProductFilter productFilter;
    private BigDecimal priceMin;
    private BigDecimal priceMax;

    public AuctionFilter() {
    }

    public AuctionFilter(final Long sellerId,
                         final ProductFilter productFilter,
                         final BigDecimal priceMin,
                         final BigDecimal priceMax) {
        this.sellerId = sellerId;
        this.productFilter = productFilter;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(final Long sellerId) {
        this.sellerId = sellerId;
    }

    public ProductFilter getProductFilter() {
        return productFilter;
    }

    public void setProductFilter(final ProductFilter productFilter) {
        this.productFilter = productFilter;
    }

    public BigDecimal getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(final BigDecimal priceMin) {
        this.priceMin = priceMin;
    }

    public BigDecimal getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(final BigDecimal priceMax) {
        this.priceMax = priceMax;
    }
}
