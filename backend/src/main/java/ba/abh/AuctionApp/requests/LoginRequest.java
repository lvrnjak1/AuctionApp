package ba.abh.AuctionApp.requests;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "Email must be present")
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
