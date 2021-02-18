package ba.abh.AuctionApp.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "First name should not be blank")
    private String name;
    @NotBlank(message = "Last name should not be blank")
    private String surname;
    @Email(message = "Email should be valid")
    private String email;
    @Size(min = 5, message = "Password should contain at least 5 characters")
    private String password;
}
