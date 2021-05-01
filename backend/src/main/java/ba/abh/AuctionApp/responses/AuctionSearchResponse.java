package ba.abh.AuctionApp.responses;

public class AuctionSearchResponse{
    private PageableResponse<AuctionResponse> auctions;
    private String suggestion;

    public AuctionSearchResponse(final PageableResponse<AuctionResponse> auctions, final String suggestion) {
        this.auctions = auctions;
        this.suggestion = suggestion;
    }

    public PageableResponse<AuctionResponse> getAuctions() {
        return auctions;
    }

    public void setAuctions(final PageableResponse<AuctionResponse> auctions) {
        this.auctions = auctions;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(final String suggestion) {
        this.suggestion = suggestion;
    }
}
