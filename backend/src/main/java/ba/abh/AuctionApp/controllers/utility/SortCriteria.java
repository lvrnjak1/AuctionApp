package ba.abh.AuctionApp.controllers.utility;

public enum SortCriteria {
    DATE("startDateTime"), PRICE("startPrice");

    private final String field;

    SortCriteria(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
