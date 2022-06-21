package ru.levelp.srv.person.profile.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Representing a unique social network
 */
@ApiModel(description = "Representing a unique social network")
public class SocialNetworkData {
    @JsonProperty("id")
    private String id;

    public SocialNetworkData id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Universally unique and immutable identifier of the social network.
     *
     * @return id
     */
    @ApiModelProperty(example = "LINKED_IN", required = true, value = "Universally unique and immutable identifier of the social network.")
    @NotNull

    @Pattern(regexp = "([A-Z0-9_]*)")
    @Size(min = 2, max = 255)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SocialNetworkData socialNetworkData = (SocialNetworkData) o;
        return Objects.equals(this.id, socialNetworkData.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SocialNetworkData {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
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

