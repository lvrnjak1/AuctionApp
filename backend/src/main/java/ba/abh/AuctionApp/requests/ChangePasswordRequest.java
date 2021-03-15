package ba.abh.AuctionApp.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ChangePasswordRequest {
    @NotBlank(message = "Password must be present")
    @Size(min = 5, message = "Password should contain at least 5 characters")
    private String newPassword;

    @NotBlank(message = "Confirmation password must be present")
    @Size(min = 5, message = "Password should contain at least 5 characters")
    private String confirmPassword;

    public ChangePasswordRequest() {
    }

    public ChangePasswordRequest(final String newPassword,
                                 final String confirmPassword) {
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(final String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
