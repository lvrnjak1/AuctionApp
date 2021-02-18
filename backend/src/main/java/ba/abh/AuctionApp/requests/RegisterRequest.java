package ba.abh.AuctionApp.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Name shouldn't be blank")
    private String name;
    @NotBlank(message = "Surname shouldn't be blank")
    private String surname;
    @NotBlank(message = "Email shouldn't be blank")
    @Email(message = "Provide a valid email address")
    private String email;
    @NotNull(message = "Password must be present")
    @Size(min = 5, message = "Password should contain at least 5 characters")
    private String password;
}
