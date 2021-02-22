package ba.abh.AuctionApp.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    public RegisterRequest() {
    }

    public RegisterRequest(final String name, final String surname, final String email, final String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
