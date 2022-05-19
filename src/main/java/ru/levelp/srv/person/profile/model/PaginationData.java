package ru.levelp.srv.person.profile.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Describes the pagination information for the provided results.
 */
@ApiModel(description = "Describes the pagination information for the provided results.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-05-19T18:45:57.374415+03:00[Europe/Moscow]")
public class PaginationData   {
  @JsonProperty("limit")
  private Integer limit;

  @JsonProperty("offset")
  private Integer offset;

  @JsonProperty("totalCount")
  private Integer totalCount;

  public PaginationData limit(Integer limit) {
    this.limit = limit;
    return this;
  }

  /**
   * Inbound pagination parameter. Defines the maximum number of entries returned in the response.
   * minimum: 1
   * @return limit
  */
  @ApiModelProperty(example = "25", value = "Inbound pagination parameter. Defines the maximum number of entries returned in the response.")

@Min(1)
  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public PaginationData offset(Integer offset) {
    this.offset = offset;
    return this;
  }

  /**
   * Inbound pagination parameter. Defines the number of entries to skip before beginning to return the search results.
   * minimum: 0
   * @return offset
  */
  @ApiModelProperty(example = "10", value = "Inbound pagination parameter. Defines the number of entries to skip before beginning to return the search results.")

@Min(0)
  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public PaginationData totalCount(Integer totalCount) {
    this.totalCount = totalCount;
    return this;
  }

  /**
   * Total number of results found for the request.
   * minimum: 0
   * @return totalCount
  */
  @ApiModelProperty(example = "252", value = "Total number of results found for the request.")

@Min(0)
  public Integer getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaginationData paginationData = (PaginationData) o;
    return Objects.equals(this.limit, paginationData.limit) &&
        Objects.equals(this.offset, paginationData.offset) &&
        Objects.equals(this.totalCount, paginationData.totalCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(limit, offset, totalCount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaginationData {\n");

    sb.append("    limit: ").append(toIndentedString(limit)).append("\n");
    sb.append("    offset: ").append(toIndentedString(offset)).append("\n");
    sb.append("    totalCount: ").append(toIndentedString(totalCount)).append("\n");
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

