package ba.abh.AuctionApp.filters;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;

public class AuctionFilter {
    private Long sellerId;
    private ProductFilter productFilter;
    private BigDecimal priceMin;
    private BigDecimal priceMax;
    private Instant startBefore;
    private Instant startAfter;
    private Instant endBefore;
    private Instant endAfter;
    private SortSpecification sortSpecification;

    public AuctionFilter() {
    }

    public AuctionFilter(final Long sellerId,
                         final ProductFilter productFilter,
                         final BigDecimal priceMin,
                         final BigDecimal priceMax,
                         final Long minutesLeft,
                         final Instant startBefore,
                         final Instant startAfter,
                         final Instant endBefore,
                         final Instant endAfter,
                         final SortSpecification sortSpecification) {
        this.sellerId = sellerId;
        this.productFilter = productFilter;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.startBefore = startBefore;
        this.startAfter = startAfter;
        this.endBefore = endBefore;
        this.endAfter = endAfter;
        this.sortSpecification = sortSpecification;
        setEndBefore(minutesLeft);
    }

    public AuctionFilter(final Long sellerId,
                         final ProductFilter productFilter,
                         final BigDecimal priceMin,
                         final BigDecimal priceMax,
                         final Long minutesLeft,
                         final SortSpecification sortSpecification) {
        this.sellerId = sellerId;
        this.productFilter = productFilter;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.sortSpecification = sortSpecification;
        setEndBefore(minutesLeft);
    }

    public void setEndBefore(final Long minutesLeft) {
        if(minutesLeft != null){
            Instant end = ZonedDateTime.now().plusMinutes(minutesLeft).toInstant();
            setEndBefore(end);
        }
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

    public Instant getStartBefore() {
        return startBefore;
    }

    public void setStartBefore(Instant startBefore) {
        this.startBefore = startBefore;
    }

    public Instant getStartAfter() {
        return startAfter;
    }

    public void setStartAfter(Instant startAfter) {
        this.startAfter = startAfter;
    }

    public Instant getEndBefore() {
        return endBefore;
    }

    public void setEndBefore(Instant endBefore) {
        this.endBefore = endBefore;
    }

    public Instant getEndAfter() {
        return endAfter;
    }

    public void setEndAfter(Instant endAfter) {
        this.endAfter = endAfter;
    }

    public SortSpecification getSortSpecification() {
        return sortSpecification;
    }

    public void setSortSpecification(SortSpecification sortSpecification) {
        this.sortSpecification = sortSpecification;
    }
}
