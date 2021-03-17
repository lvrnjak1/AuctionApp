package ba.abh.AuctionApp.requests;

import ba.abh.AuctionApp.utility.email.ValidEmail;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @ValidEmail(message = "Email must be present and have a valid format")
    private String email;

    @NotBlank(message = "Password must be present")
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
