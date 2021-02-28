package ba.abh.AuctionApp.pagination;

public class PaginationDetails {
    private int currentPage;
    private boolean hasNext;
    private boolean hasPrevious;
    private int numberOfItemsOnPage;

    public PaginationDetails(final int currentPage,
                             final boolean hasNext,
                             final boolean hasPrevious,
                             final int numberOfItemsOnPage) {
        this.currentPage = currentPage;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
        this.numberOfItemsOnPage = numberOfItemsOnPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(final int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(final boolean hasNext) {
        this.hasNext = hasNext;
    }

    public int getNumberOfItemsOnPage() {
        return numberOfItemsOnPage;
    }

    public void setNumberOfItemsOnPage(final int numberOfItemsOnPage) {
        this.numberOfItemsOnPage = numberOfItemsOnPage;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(final boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }
}
