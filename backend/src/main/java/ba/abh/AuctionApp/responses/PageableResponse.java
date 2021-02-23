package ba.abh.AuctionApp.responses;

import ba.abh.AuctionApp.pagination.PageableEntity;
import ba.abh.AuctionApp.pagination.PaginationDetails;

import java.util.List;

public class PageableResponse {
    private PaginationDetails pagination;
    private List<PageableEntity> data;

    public PageableResponse(final PaginationDetails pagination, final List<PageableEntity> data) {
        this.pagination = pagination;
        this.data = data;
    }

    public PaginationDetails getPagination() {
        return pagination;
    }

    public PageableResponse setPagination(final PaginationDetails pagination) {
        this.pagination = pagination;
        return this;
    }

    public List<PageableEntity> getData() {
        return data;
    }

    public PageableResponse setData(final List<PageableEntity> data) {
        this.data = data;
        return this;
    }
}
