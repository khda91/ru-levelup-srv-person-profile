package ru.levelp.srv.person.profile.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Represents attributes required to add a social network with some predefined type to a person.
 */
@ApiModel(description = "Represents attributes required to add a social network with some predefined type to a person.")
public class CreatePersonSocialNetworkData {
    @JsonProperty("socialNetworkId")
    private String socialNetworkId;

    @JsonProperty("link")
    private String link;

    public CreatePersonSocialNetworkData socialNetworkId(String socialNetworkId) {
        this.socialNetworkId = socialNetworkId;
        return this;
    }

    /**
     * Universally unique and immutable identifier of the social network.
     *
     * @return socialNetworkId
     */
    @ApiModelProperty(example = "LINKED_IN", value = "Universally unique and immutable identifier of the social network.")

    @Pattern(regexp = "([A-Z0-9_]*)")
    @Size(min = 2, max = 255)
    public String getSocialNetworkId() {
        return socialNetworkId;
    }

    public void setSocialNetworkId(String socialNetworkId) {
        this.socialNetworkId = socialNetworkId;
    }

    public CreatePersonSocialNetworkData link(String link) {
        this.link = link;
        return this;
    }

    /**
     * Link to the person in social network.
     *
     * @return link
     */
    @ApiModelProperty(example = "https://vk.com/id123456", value = "Link to the person in social network.")

    @Size(max = 255)
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CreatePersonSocialNetworkData createPersonSocialNetworkData = (CreatePersonSocialNetworkData) o;
        return Objects.equals(this.socialNetworkId, createPersonSocialNetworkData.socialNetworkId) &&
                Objects.equals(this.link, createPersonSocialNetworkData.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socialNetworkId, link);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CreatePersonSocialNetworkData {\n");

        sb.append("    socialNetworkId: ").append(toIndentedString(socialNetworkId)).append("\n");
        sb.append("    link: ").append(toIndentedString(link)).append("\n");
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

