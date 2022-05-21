package ru.levelp.srv.person.profile.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@ApiModel(description = "Represents a person's personal data")
public class IdentityData {
    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("middleName")
    private String middleName;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("dateOfBirth")
    @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;

    @JsonProperty("placeOfBirth")
    private String placeOfBirth;

    @JsonProperty("passport")
    private PassportData passport;

    public IdentityData firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * First name of the person.
     *
     * @return firstName
     */
    @ApiModelProperty(example = "Vasily", value = "First name of the person.")

    @Size(max = 255)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public IdentityData lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Last name of the person.
     *
     * @return lastName
     */
    @ApiModelProperty(example = "Pupkin", value = "Last name of the person.")

    @Size(max = 255)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public IdentityData middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    /**
     * Middle name of the person.
     *
     * @return middleName
     */
    @ApiModelProperty(example = "Ivanovich", value = "Middle name of the person.")

    @Size(max = 255)
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public IdentityData gender(String gender) {
        this.gender = gender;
        return this;
    }

    /**
     * Unique identifier of the gender of the person.
     *
     * @return gender
     */
    @ApiModelProperty(example = "MALE", value = "Unique identifier of the gender of the person.")

    @Size(max = 255)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public IdentityData dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    /**
     * Date of a person's birth.
     *
     * @return dateOfBirth
     */
    @ApiModelProperty(example = "Thu Feb 07 03:00:00 MSK 1980", value = "Date of a person's birth.")

    @Valid

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public IdentityData placeOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
        return this;
    }

    /**
     * City of person's birth
     *
     * @return placeOfBirth
     */
    @ApiModelProperty(example = "Moscow", value = "City of person's birth")

    @Size(max = 255)
    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public IdentityData passport(PassportData passport) {
        this.passport = passport;
        return this;
    }

    /**
     * Get passport
     *
     * @return passport
     */
    @ApiModelProperty(value = "")

    @Valid

    public PassportData getPassport() {
        return passport;
    }

    public void setPassport(PassportData passport) {
        this.passport = passport;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IdentityData identityData = (IdentityData) o;
        return Objects.equals(this.firstName, identityData.firstName) &&
                Objects.equals(this.lastName, identityData.lastName) &&
                Objects.equals(this.middleName, identityData.middleName) &&
                Objects.equals(this.gender, identityData.gender) &&
                Objects.equals(this.dateOfBirth, identityData.dateOfBirth) &&
                Objects.equals(this.placeOfBirth, identityData.placeOfBirth) &&
                Objects.equals(this.passport, identityData.passport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, middleName, gender, dateOfBirth, placeOfBirth, passport);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class IdentityData {\n");

        sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
        sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
        sb.append("    middleName: ").append(toIndentedString(middleName)).append("\n");
        sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
        sb.append("    dateOfBirth: ").append(toIndentedString(dateOfBirth)).append("\n");
        sb.append("    placeOfBirth: ").append(toIndentedString(placeOfBirth)).append("\n");
        sb.append("    passport: ").append(toIndentedString(passport)).append("\n");
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

