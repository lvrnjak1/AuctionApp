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

    public void setCurrentPage(final int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isNextPageAvailable() {
        return nextPageAvailable;
    }

    public void setNextPageAvailable(final boolean nextPageAvailable) {
        this.nextPageAvailable = nextPageAvailable;
    }
}
