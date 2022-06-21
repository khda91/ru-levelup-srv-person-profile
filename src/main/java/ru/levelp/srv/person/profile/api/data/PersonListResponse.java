package ru.levelp.srv.person.profile.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Response containing a list of person profiles.
 */
@ApiModel(description = "Response containing a list of person profiles.")
public class PersonListResponse {
    @JsonProperty("data")
    @Valid
    private List<PersonData> data = new ArrayList<>();

    @JsonProperty("meta")
    private PersonListResponseMeta meta;

    public PersonListResponse data(List<PersonData> data) {
        this.data = data;
        return this;
    }

    public PersonListResponse addDataItem(PersonData dataItem) {
        this.data.add(dataItem);
        return this;
    }

    /**
     * Successfully retrieved results.
     *
     * @return data
     */
    @ApiModelProperty(required = true, value = "Successfully retrieved results.")
    @NotNull

    @Valid

    public List<PersonData> getData() {
        return data;
    }

    public void setData(List<PersonData> data) {
        this.data = data;
    }

    public PersonListResponse meta(PersonListResponseMeta meta) {
        this.meta = meta;
        return this;
    }

    /**
     * Get meta
     *
     * @return meta
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Valid

    public PersonListResponseMeta getMeta() {
        return meta;
    }

    public void setMeta(PersonListResponseMeta meta) {
        this.meta = meta;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonListResponse personListResponse = (PersonListResponse) o;
        return Objects.equals(this.data, personListResponse.data) &&
                Objects.equals(this.meta, personListResponse.meta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, meta);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PersonListResponse {\n");

        sb.append("    data: ").append(toIndentedString(data)).append("\n");
        sb.append("    meta: ").append(toIndentedString(meta)).append("\n");
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

