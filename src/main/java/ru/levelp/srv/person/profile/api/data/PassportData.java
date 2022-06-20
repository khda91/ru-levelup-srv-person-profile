package ru.levelp.srv.person.profile.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a person&#39;s passport data
 */
@ApiModel(description = "Represents a person's passport data")
public class PassportData {
    @JsonProperty("series")
    private String series;

    @JsonProperty("number")
    private String number;

    @JsonProperty("placeOfIssue")
    private String placeOfIssue;

    @JsonProperty("dateOfIssue")
    @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
    private LocalDate dateOfIssue;

    @JsonProperty("departmentCode")
    private String departmentCode;

    public PassportData series(String series) {
        this.series = series;
        return this;
    }

    /**
     * Series of person's passport.
     *
     * @return series
     */
    @ApiModelProperty(example = "1234", value = "Series of person's passport.")

    @Pattern(regexp = "\\d{4}")
    @Size(min = 4, max = 4)
    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public PassportData number(String number) {
        this.number = number;
        return this;
    }

    /**
     * Number of person's passport.
     *
     * @return number
     */
    @ApiModelProperty(example = "123456", value = "Number of person's passport.")

    @Pattern(regexp = "\\d{6}")
    @Size(min = 6, max = 6)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PassportData placeOfIssue(String placeOfIssue) {
        this.placeOfIssue = placeOfIssue;
        return this;
    }

    /**
     * Place where person received passport.
     *
     * @return placeOfIssue
     */
    @ApiModelProperty(example = "", value = "Place where person received passport.")

    @Size(max = 255)
    public String getPlaceOfIssue() {
        return placeOfIssue;
    }

    public void setPlaceOfIssue(String placeOfIssue) {
        this.placeOfIssue = placeOfIssue;
    }

    public PassportData dateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
        return this;
    }

    /**
     * Date when a person received passport.
     *
     * @return dateOfIssue
     */
    @ApiModelProperty(example = "Thu Feb 07 03:00:00 MSK 1980", value = "Date when a person received passport.")

    @Valid

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public PassportData departmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
        return this;
    }

    /**
     * Department code where person received passport.
     *
     * @return departmentCode
     */
    @ApiModelProperty(example = "123-456", value = "Department code where person received passport.")

    @Pattern(regexp = "(\\d{3}-\\d{3})")
    @Size(min = 7, max = 7)
    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PassportData passportData = (PassportData) o;
        return Objects.equals(this.series, passportData.series) &&
                Objects.equals(this.number, passportData.number) &&
                Objects.equals(this.placeOfIssue, passportData.placeOfIssue) &&
                Objects.equals(this.dateOfIssue, passportData.dateOfIssue) &&
                Objects.equals(this.departmentCode, passportData.departmentCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(series, number, placeOfIssue, dateOfIssue, departmentCode);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PassportData {\n");

        sb.append("    series: ").append(toIndentedString(series)).append("\n");
        sb.append("    number: ").append(toIndentedString(number)).append("\n");
        sb.append("    placeOfIssue: ").append(toIndentedString(placeOfIssue)).append("\n");
        sb.append("    dateOfIssue: ").append(toIndentedString(dateOfIssue)).append("\n");
        sb.append("    departmentCode: ").append(toIndentedString(departmentCode)).append("\n");
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

