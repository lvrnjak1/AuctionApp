package ba.abh.AuctionApp.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ChangePasswordRequest {
    @NotBlank(message = "Password must be present")
    @Size(min = 5, message = "Password should contain at least 5 characters")
    private String password;

    public ChangePasswordRequest() {
    }

    public ChangePasswordRequest(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
