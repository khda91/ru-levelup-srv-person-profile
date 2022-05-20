package ru.levelp.srv.person.profile.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Represents attributes required to create a new person record.
 */
@ApiModel(description = "Represents attributes required to create a new person record.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-05-19T18:45:57.374415+03:00[Europe/Moscow]")
public class CreatePersonData {
    @JsonProperty("role")
    private PersonRole role;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("identity")
    private IdentityData identity;

    @JsonProperty("address")
    private AddressData address;

    public CreatePersonData role(PersonRole role) {
        this.role = role;
        return this;
    }

    /**
     * Get role
     *
     * @return role
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Valid

    public PersonRole getRole() {
        return role;
    }

    public void setRole(PersonRole role) {
        this.role = role;
    }

    public CreatePersonData email(String email) {
        this.email = email;
        return this;
    }

    /**
     * Email of the person.
     *
     * @return email
     */
    @ApiModelProperty(example = "person@mail.ru", required = true, value = "Email of the person.")
    @NotNull

    @Size(max = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CreatePersonData phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    /**
     * Phone number of the person.
     *
     * @return phoneNumber
     */
    @ApiModelProperty(example = "+79211234567", required = true, value = "Phone number of the person.")
    @NotNull

    @Size(max = 255)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public CreatePersonData identity(IdentityData identity) {
        this.identity = identity;
        return this;
    }

    /**
     * Get identity
     *
     * @return identity
     */
    @ApiModelProperty(value = "")

    @Valid

    public IdentityData getIdentity() {
        return identity;
    }

    public void setIdentity(IdentityData identity) {
        this.identity = identity;
    }

    public CreatePersonData address(AddressData address) {
        this.address = address;
        return this;
    }

    /**
     * Get address
     *
     * @return address
     */
    @ApiModelProperty(value = "")

    @Valid

    public AddressData getAddress() {
        return address;
    }

    public void setAddress(AddressData address) {
        this.address = address;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CreatePersonData createPersonData = (CreatePersonData) o;
        return Objects.equals(this.role, createPersonData.role) &&
                Objects.equals(this.email, createPersonData.email) &&
                Objects.equals(this.phoneNumber, createPersonData.phoneNumber) &&
                Objects.equals(this.identity, createPersonData.identity) &&
                Objects.equals(this.address, createPersonData.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, email, phoneNumber, identity, address);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CreatePersonData {\n");

        sb.append("    role: ").append(toIndentedString(role)).append("\n");
        sb.append("    email: ").append(toIndentedString(email)).append("\n");
        sb.append("    phoneNumber: ").append(toIndentedString(phoneNumber)).append("\n");
        sb.append("    identity: ").append(toIndentedString(identity)).append("\n");
        sb.append("    address: ").append(toIndentedString(address)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

