package ba.abh.AuctionApp.controllers.utility;

public enum SortCriteria {
    DATE("startDateTime"), PRICE("startPrice"), TIME_LEFT("endDateTime");

    private final String field;

    SortCriteria(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
