package ba.abh.AuctionApp.filters;

import ba.abh.AuctionApp.controllers.utility.SortCriteria;
import ba.abh.AuctionApp.controllers.utility.SortOrder;

public class SortSpecification {
    private SortCriteria sortCriteria;
    private SortOrder sortOrder = SortOrder.ASC;

    public SortSpecification(final SortCriteria sortCriteria, final SortOrder sortOrder) {
        this.sortCriteria = sortCriteria;
        if(sortOrder != null) {
            this.sortOrder = sortOrder;
        }
    }

    public SortCriteria getSortCriteria() {
        return sortCriteria;
    }

    public void setSortCriteria(final SortCriteria sortCriteria) {
        this.sortCriteria = sortCriteria;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(final SortOrder sortOrder) {
        if(sortOrder != null) {
            this.sortOrder = sortOrder;
        }
    }
}
