package ba.abh.AuctionApp.requests;

import javax.validation.constraints.NotBlank;

public class ForgotPasswordRequest {
    @NotBlank(message = "You must include the email field")
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
