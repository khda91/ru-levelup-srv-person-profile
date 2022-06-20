package ru.levelp.srv.person.profile.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Represents a person&#39;s address.
 */
@ApiModel(description = "Represents a person's address.")
public class AddressData {
    @JsonProperty("street")
    private String street;

    @JsonProperty("houseNumber")
    private Integer houseNumber;

    @JsonProperty("houseBuilding")
    private Integer houseBuilding;

    @JsonProperty("houseLetter")
    private String houseLetter;

    @JsonProperty("flat")
    private Integer flat;

    @JsonProperty("city")
    private String city;

    @JsonProperty("postalCode")
    private String postalCode;

    public AddressData street(String street) {
        this.street = street;
        return this;
    }

    /**
     * The person's street of living.
     *
     * @return street
     */
    @ApiModelProperty(example = "Beethovenstrasse", value = "The person's street of living.")

    @Size(max = 255)
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public AddressData houseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    /**
     * The person's house number of living.
     *
     * @return houseNumber
     */
    @ApiModelProperty(example = "12", value = "The person's house number of living.")


    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public AddressData houseBuilding(Integer houseBuilding) {
        this.houseBuilding = houseBuilding;
        return this;
    }

    /**
     * The person's house building of living.
     *
     * @return houseBuilding
     */
    @ApiModelProperty(example = "1", value = "The person's house building of living.")


    public Integer getHouseBuilding() {
        return houseBuilding;
    }

    public void setHouseBuilding(Integer houseBuilding) {
        this.houseBuilding = houseBuilding;
    }

    public AddressData houseLetter(String houseLetter) {
        this.houseLetter = houseLetter;
        return this;
    }

    /**
     * The person's house letter of living.
     *
     * @return houseLetter
     */
    @ApiModelProperty(example = "A", value = "The person's house letter of living.")


    public String getHouseLetter() {
        return houseLetter;
    }

    public void setHouseLetter(String houseLetter) {
        this.houseLetter = houseLetter;
    }

    public AddressData flat(Integer flat) {
        this.flat = flat;
        return this;
    }

    /**
     * The person's flat number of living.
     *
     * @return flat
     */
    @ApiModelProperty(example = "123", value = "The person's flat number of living.")


    public Integer getFlat() {
        return flat;
    }

    public void setFlat(Integer flat) {
        this.flat = flat;
    }

    public AddressData city(String city) {
        this.city = city;
        return this;
    }

    /**
     * The person's city of living.
     *
     * @return city
     */
    @ApiModelProperty(example = "Moscow", value = "The person's city of living.")

    @Size(max = 255)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public AddressData postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    /**
     * Postal code of the address.
     *
     * @return postalCode
     */
    @ApiModelProperty(example = "123456", value = "Postal code of the address.")

    @Size(max = 255)
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AddressData addressData = (AddressData) o;
        return Objects.equals(this.street, addressData.street) &&
                Objects.equals(this.houseNumber, addressData.houseNumber) &&
                Objects.equals(this.houseBuilding, addressData.houseBuilding) &&
                Objects.equals(this.houseLetter, addressData.houseLetter) &&
                Objects.equals(this.flat, addressData.flat) &&
                Objects.equals(this.city, addressData.city) &&
                Objects.equals(this.postalCode, addressData.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, houseNumber, houseBuilding, houseLetter, flat, city, postalCode);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AddressData {\n");

        sb.append("    street: ").append(toIndentedString(street)).append("\n");
        sb.append("    houseNumber: ").append(toIndentedString(houseNumber)).append("\n");
        sb.append("    houseBuilding: ").append(toIndentedString(houseBuilding)).append("\n");
        sb.append("    houseLetter: ").append(toIndentedString(houseLetter)).append("\n");
        sb.append("    flat: ").append(toIndentedString(flat)).append("\n");
        sb.append("    city: ").append(toIndentedString(city)).append("\n");
        sb.append("    postalCode: ").append(toIndentedString(postalCode)).append("\n");
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

