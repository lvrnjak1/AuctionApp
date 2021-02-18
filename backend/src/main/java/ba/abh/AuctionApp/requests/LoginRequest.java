package ba.abh.AuctionApp.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Email must be present")
    private String email;
    @NotBlank(message = "Password must be present")
    private String password;
}
