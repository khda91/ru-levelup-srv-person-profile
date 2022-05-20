package ru.levelp.srv.person.profile.api.data;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;

/**
 * Results of a validation constraint violation produced by some request.
 */
@ApiModel(description = "Results of a validation constraint violation produced by some request.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-05-19T18:45:57.374415+03:00[Europe/Moscow]")
public class ViolationData   {
  @JsonProperty("code")
  private String code;

  @JsonProperty("field")
  private String field;

  @JsonProperty("detail")
  private String detail;

  public ViolationData code(String code) {
    this.code = code;
    return this;
  }

  /**
   * Unique identifier of the type of the validation problem.
   * @return code
  */
  @ApiModelProperty(example = "PA1234", required = true, value = "Unique identifier of the type of the validation problem.")
  @NotNull

@Size(max=255)
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public ViolationData field(String field) {
    this.field = field;
    return this;
  }

  /**
   * Reference to the field that did not pass the validation.
   * @return field
  */
  @ApiModelProperty(example = "PersonData.email", value = "Reference to the field that did not pass the validation.")

@Size(max=255)
  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public ViolationData detail(String detail) {
    this.detail = detail;
    return this;
  }

  /**
   * Human-readable explanation of the validation problem.
   * @return detail
  */
  @ApiModelProperty(example = "Your current balance is 30, but that costs 50.", value = "Human-readable explanation of the validation problem.")

@Size(max=65535)
  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ViolationData violationData = (ViolationData) o;
    return Objects.equals(this.code, violationData.code) &&
        Objects.equals(this.field, violationData.field) &&
        Objects.equals(this.detail, violationData.detail);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, field, detail);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ViolationData {\n");

    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    field: ").append(toIndentedString(field)).append("\n");
    sb.append("    detail: ").append(toIndentedString(detail)).append("\n");
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

