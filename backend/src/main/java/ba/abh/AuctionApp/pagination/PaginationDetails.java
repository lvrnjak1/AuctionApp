package ba.abh.AuctionApp.pagination;

public class PaginationDetails {
    private int pageNumber;
    private boolean hasNext;
    private boolean hasPrevious;
    private int pageSize;
    private long available;

    public PaginationDetails(final int pageNumber,
                             final boolean hasNext,
                             final boolean hasPrevious,
                             final int pageSize,
                             final long available) {
        this.pageNumber = pageNumber;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
        this.pageSize = pageSize;
        this.available = available;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(final int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(final boolean hasNext) {
        this.hasNext = hasNext;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(final boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public long getAvailable() {
        return available;
    }

    public void setAvailable(final long available) {
        this.available = available;
    }
}
