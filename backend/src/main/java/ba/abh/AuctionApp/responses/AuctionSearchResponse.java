package ba.abh.AuctionApp.responses;

public class AuctionSearchResponse{
    private PageableResponse auctions;
    private String suggestion;

    public AuctionSearchResponse(final PageableResponse auctions, final String suggestion) {
        this.auctions = auctions;
        this.suggestion = suggestion;
    }

    public PageableResponse getAuctions() {
        return auctions;
    }

    public void setAuctions(final PageableResponse auctions) {
        this.auctions = auctions;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(final String suggestion) {
        this.suggestion = suggestion;
    }
}
