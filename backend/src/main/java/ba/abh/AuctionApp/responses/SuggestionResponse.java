package ba.abh.AuctionApp.responses;

public class SuggestionResponse{
    private String suggestion;

    public SuggestionResponse(final String suggestion) {
        this.suggestion = suggestion;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(final String suggestion) {
        this.suggestion = suggestion;
    }
}
