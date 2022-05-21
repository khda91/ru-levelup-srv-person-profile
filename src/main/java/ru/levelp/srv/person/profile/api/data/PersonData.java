package ru.levelp.srv.person.profile.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@ApiModel(description = "Represents the main set of a person's attributes.")
public class PersonData {
    @JsonProperty("id")
    private String id;

    @JsonProperty("role")
    private PersonRole role;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("placeOfWork")
    private String placeOfWork;

    @JsonProperty("identity")
    private IdentityData identity;

    @JsonProperty("address")
    private AddressData address;

    public PersonData id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Universally unique and immutable identifier of the person.
     *
     * @return id
     */
    @ApiModelProperty(example = "123e4567-e89b-12d3-a456-426655440000", required = true, value = "Universally unique and immutable identifier of the person.")
    @NotNull

    @Size(max = 255)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PersonData role(PersonRole role) {
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

    public PersonData email(String email) {
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

    public PersonData phoneNumber(String phoneNumber) {
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

    public PersonData placeOfWork(String placeOfWork) {
        this.placeOfWork = placeOfWork;
        return this;
    }

    /**
     * Place of work of the person.
     *
     * @return placeOfWork
     */
    @ApiModelProperty(example = "Engineer", value = "Place of work of the person.")

    @Size(max = 255)
    public String getPlaceOfWork() {
        return placeOfWork;
    }

    public void setPlaceOfWork(String placeOfWork) {
        this.placeOfWork = placeOfWork;
    }

    public PersonData identity(IdentityData identity) {
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

    public PersonData address(AddressData address) {
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
        PersonData personData = (PersonData) o;
        return Objects.equals(this.id, personData.id) &&
                Objects.equals(this.role, personData.role) &&
                Objects.equals(this.email, personData.email) &&
                Objects.equals(this.phoneNumber, personData.phoneNumber) &&
                Objects.equals(this.placeOfWork, personData.placeOfWork) &&
                Objects.equals(this.identity, personData.identity) &&
                Objects.equals(this.address, personData.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, email, phoneNumber, placeOfWork, identity, address);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PersonData {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    role: ").append(toIndentedString(role)).append("\n");
        sb.append("    email: ").append(toIndentedString(email)).append("\n");
        sb.append("    phoneNumber: ").append(toIndentedString(phoneNumber)).append("\n");
        sb.append("    placeOfWork: ").append(toIndentedString(placeOfWork)).append("\n");
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

