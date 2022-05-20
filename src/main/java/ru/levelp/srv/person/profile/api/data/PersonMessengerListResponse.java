package ru.levelp.srv.person.profile.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ApiModel(description = "Response containing a list of person messengers.")
public class PersonMessengerListResponse {
    @JsonProperty("data")
    @Valid
    private List<PersonMessengerData> data = new ArrayList<>();

    @JsonProperty("meta")
    private PersonMessengerListResponseMeta meta;

    public PersonMessengerListResponse data(List<PersonMessengerData> data) {
        this.data = data;
        return this;
    }

    public PersonMessengerListResponse addDataItem(PersonMessengerData dataItem) {
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

    public List<PersonMessengerData> getData() {
        return data;
    }

    public void setData(List<PersonMessengerData> data) {
        this.data = data;
    }

    public PersonMessengerListResponse meta(PersonMessengerListResponseMeta meta) {
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

    public PersonMessengerListResponseMeta getMeta() {
        return meta;
    }

    public void setMeta(PersonMessengerListResponseMeta meta) {
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
        PersonMessengerListResponse personMessengerListResponse = (PersonMessengerListResponse) o;
        return Objects.equals(this.data, personMessengerListResponse.data) &&
                Objects.equals(this.meta, personMessengerListResponse.meta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, meta);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PersonMessengerListResponse {\n");

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

