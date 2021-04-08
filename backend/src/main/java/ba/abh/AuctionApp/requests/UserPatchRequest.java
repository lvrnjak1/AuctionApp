package ba.abh.AuctionApp.requests;

import ba.abh.AuctionApp.utility.email.ValidEmail;
import ba.abh.AuctionApp.utility.url.ValidUrl;
import org.openapitools.jackson.nullable.JsonNullable;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserPatchRequest {
    @NotBlank(message = "Name shouldn't be blank")
    @Size(min = 2, max = 30, message = "Name should have between 2 and 30 characters")
    private JsonNullable<String> name = JsonNullable.undefined();

    @NotBlank(message = "Name shouldn't be blank")
    @Size(min = 2, max = 30, message = "Name should have between 2 and 30 characters")
    private JsonNullable<String> surname = JsonNullable.undefined();

    @ValidEmail(message = "Provide a valid email address")
    private JsonNullable<String> email = JsonNullable.undefined();

    @Pattern(regexp = "^(female|FEMALE|MALE|male)$", message = "Possible values are male (MALE) and female (FEMALE)")
    private JsonNullable<String> gender = JsonNullable.undefined();

    private JsonNullable<Long> dateOfBirth = JsonNullable.undefined();

    @Pattern(regexp = "^\\+\\d{1,3}[0-9 ]{7,15}$", message = "Invalid phone number format")
    private JsonNullable<String> phoneNumber = JsonNullable.undefined();

    @ValidUrl
    private JsonNullable<String> profilePhotoUrl = JsonNullable.undefined();

    @Valid
    private JsonNullable<CardDetailsRequest> cardDetails = JsonNullable.undefined();

    public UserPatchRequest() {
    }

    public JsonNullable<String> getName() {
        return name;
    }

    public void setName(final JsonNullable<String> name) {
        this.name = name;
    }

    public JsonNullable<String> getSurname() {
        return surname;
    }

    public void setSurname(final JsonNullable<String> surname) {
        this.surname = surname;
    }

    public JsonNullable<String> getEmail() {
        return email;
    }

    public void setEmail(final JsonNullable<String> email) {
        this.email = email;
    }

    public JsonNullable<String> getGender() {
        return gender;
    }

    public void setGender(final JsonNullable<String> gender) {
        this.gender = gender;
    }

    public JsonNullable<Long> getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(final JsonNullable<Long> dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public JsonNullable<String> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final JsonNullable<String> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public JsonNullable<String> getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(final JsonNullable<String> profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public JsonNullable<CardDetailsRequest> getCardDetails() {
        return cardDetails;
    }

    public void setCardDetails(final JsonNullable<CardDetailsRequest> cardDetails) {
        this.cardDetails = cardDetails;
    }
}
