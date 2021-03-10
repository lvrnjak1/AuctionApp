package ba.abh.AuctionApp.controllers.utility;

import ba.abh.AuctionApp.domain.enums.Size;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class RequestParams {
    private static final int MIN_PAGE = 1;
    private static final int MIN_LIMIT = 1;
    private static final int DEFAULT_LIMIT = 10;
    private static final int MAX_LIMIT = 500;

    @Min(value = MIN_PAGE, message = "Page index should start at 1")
    private int page = MIN_PAGE;

    @Min(value = MIN_LIMIT, message = "Page size (limit) should be at least 1")
    @Max(value = MAX_LIMIT, message = "Page size (limit) should be less than 500")
    private int limit = DEFAULT_LIMIT;

    private Long sellerId;
    private Double priceMin;
    private Double priceMax;
    private Size size;
    private Long categoryId;
    private String name;
    private Long minutesLeft;
    private SortCriteria sort;
    private SortOrder sortOrder;

    public RequestParams() {
    }

    public RequestParams(final Integer page,
                         final Integer limit,
                         final Long sellerId,
                         final Double priceMin,
                         final Double priceMax,
                         final Size size,
                         final Long categoryId,
                         final String name,
                         final Long minutesLeft,
                         final SortCriteria sort,
                         final SortOrder sortOrder) {
        this.page = page;
        this.limit = limit;
        this.sellerId = sellerId;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.size = size;
        this.categoryId = categoryId;
        this.name = name;
        this.minutesLeft = minutesLeft;
        this.sort = sort;
        this.sortOrder = sortOrder;
    }

    public RequestParams(final int page, final int limit) {
        this.page = page;
        this.limit = limit;
    }

    public static int getMinPage() {
        return MIN_PAGE;
    }

    public static int getMinLimit() {
        return MIN_LIMIT;
    }

    public static int getDefaultLimit() {
        return DEFAULT_LIMIT;
    }

    public static int getMaxLimit() {
        return MAX_LIMIT;
    }

    public int getPage() {
        return page;
    }

    public void setPage(final int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(final int limit) {
        this.limit = limit;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(final Long sellerId) {
        this.sellerId = sellerId;
    }

    public Double getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(final Double priceMin) {
        this.priceMin = priceMin;
    }

    public Double getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(final Double priceMax) {
        this.priceMax = priceMax;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(final Size size) {
        this.size = size;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(final Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Long getMinutesLeft() {
        return minutesLeft;
    }

    public void setMinutesLeft(final Long minutesLeft) {
        this.minutesLeft = minutesLeft;
    }

    public SortCriteria getSort() {
        return sort;
    }

    public void setSort(final SortCriteria sort) {
        this.sort = sort;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(final SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }
//
//    private static void checkPagination(final int page, final int limit) {
//        if (page < 1) {
//            throw new InvalidPaginationException("Page index should start at 1");
//        }
//
//        if(limit < 1) {
//            throw new InvalidPaginationException("Page size (limit) should be at least 1");
//        }
//
//        if(limit > 500){
//            throw new InvalidPaginationException("Page size (limit) should be less than 500");
//        }
//    }
}
