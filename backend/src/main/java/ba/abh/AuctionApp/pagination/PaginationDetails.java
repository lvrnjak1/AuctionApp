package ba.abh.AuctionApp.pagination;

public class PaginationDetails {
    private int currentPage;
    private boolean nextPageAvailable;
    private int numberOfItemsOnPage;

    public PaginationDetails(final int currentPage, final boolean nextPageAvailable, final int numberOfItemsOnPage) {
        this.currentPage = currentPage;
        this.nextPageAvailable = nextPageAvailable;
        this.numberOfItemsOnPage = numberOfItemsOnPage;
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

    public int getNumberOfItemsOnPage() {
        return numberOfItemsOnPage;
    }

    public void setNumberOfItemsOnPage(final int numberOfItemsOnPage) {
        this.numberOfItemsOnPage = numberOfItemsOnPage;
    }
}
