package ba.abh.AuctionApp.pagination;

public class PaginationDetails {
    private int currentPage;
    private boolean nextPageAvailable;

    public PaginationDetails(final int currentPage, final boolean nextPageAvailable) {
        this.currentPage = currentPage;
        this.nextPageAvailable = nextPageAvailable;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public PaginationDetails setCurrentPage(final int currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    public boolean isNextPageAvailable() {
        return nextPageAvailable;
    }

    public PaginationDetails setNextPageAvailable(final boolean nextPageAvailable) {
        this.nextPageAvailable = nextPageAvailable;
        return this;
    }
}
