package ru.levelp.srv.person.profile.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@ApiModel(description = "Meta information for the provided results.")
public class PersonMessengerListResponseMeta {
    @JsonProperty("pagination")
    private PaginationData pagination;

    public PersonMessengerListResponseMeta pagination(PaginationData pagination) {
        this.pagination = pagination;
        return this;
    }

    /**
     * Get pagination
     *
     * @return pagination
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Valid

    public PaginationData getPagination() {
        return pagination;
    }

    public void setPagination(PaginationData pagination) {
        this.pagination = pagination;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonMessengerListResponseMeta personMessengerListResponseMeta = (PersonMessengerListResponseMeta) o;
        return Objects.equals(this.pagination, personMessengerListResponseMeta.pagination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pagination);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PersonMessengerListResponseMeta {\n");

        sb.append("    pagination: ").append(toIndentedString(pagination)).append("\n");
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

