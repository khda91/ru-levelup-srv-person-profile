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
 * Response containing a list of person social networks.
 */
@ApiModel(description = "Response containing a list of person social networks.")
public class PersonSocialNetworkListResponse {
    @JsonProperty("data")
    @Valid
    private List<PersonSocialNetworkData> data = new ArrayList<>();

    @JsonProperty("meta")
    private PersonListResponseMeta meta;

    public PersonSocialNetworkListResponse data(List<PersonSocialNetworkData> data) {
        this.data = data;
        return this;
    }

    public PersonSocialNetworkListResponse addDataItem(PersonSocialNetworkData dataItem) {
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

    public List<PersonSocialNetworkData> getData() {
        return data;
    }

    public void setData(List<PersonSocialNetworkData> data) {
        this.data = data;
    }

    public PersonSocialNetworkListResponse meta(PersonListResponseMeta meta) {
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
        PersonSocialNetworkListResponse personSocialNetworkListResponse = (PersonSocialNetworkListResponse) o;
        return Objects.equals(this.data, personSocialNetworkListResponse.data) &&
                Objects.equals(this.meta, personSocialNetworkListResponse.meta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, meta);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PersonSocialNetworkListResponse {\n");

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

