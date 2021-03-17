package ba.abh.AuctionApp.requests;

import ba.abh.AuctionApp.utility.email.ValidEmail;

public class ForgotPasswordRequest {
    @ValidEmail(message = "Email must be present and have a valid format")
    private String email;

    public ForgotPasswordRequest() {
    }

    public ForgotPasswordRequest(final String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
}
