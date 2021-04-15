package ba.abh.AuctionApp.responses;

import ba.abh.AuctionApp.pagination.PageableEntity;
import ba.abh.AuctionApp.pagination.PaginationDetails;

import java.util.List;

public class PageableResponse<T extends PageableEntity> {
    private PaginationDetails pagination;
    private List<T> data;

    public PageableResponse(final PaginationDetails pagination, final List<T> data) {
        this.pagination = pagination;
        this.data = data;
    }

    public PaginationDetails getPagination() {
        return pagination;
    }

    public void setPagination(final PaginationDetails pagination) {
        this.pagination = pagination;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(final List<T> data) {
        this.data = data;
    }
}
