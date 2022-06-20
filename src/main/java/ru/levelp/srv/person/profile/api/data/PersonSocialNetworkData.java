package ru.levelp.srv.person.profile.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Represents a social networks of a person.
 */
@ApiModel(description = "Represents a social networks of a person.")
public class PersonSocialNetworkData {
    @JsonProperty("id")
    private String id;

    @JsonProperty("personId")
    private String personId;

    @JsonProperty("socialNetworkId")
    private String socialNetworkId;

    @JsonProperty("link")
    private String link;

    public PersonSocialNetworkData id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Universally unique and immutable identifier of the person social network.
     *
     * @return id
     */
    @ApiModelProperty(example = "123e4567-e89b-12d3-a456-426655440000", value = "Universally unique and immutable identifier of the person social network.")

    @Size(max = 255)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PersonSocialNetworkData personId(String personId) {
        this.personId = personId;
        return this;
    }

    /**
     * Universally unique and immutable person identifier used across the whole platform.
     *
     * @return personId
     */
    @ApiModelProperty(example = "123e4567-e89b-12d3-a456-426655440000", value = "Universally unique and immutable person identifier used across the whole platform.")

    @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public PersonSocialNetworkData socialNetworkId(String socialNetworkId) {
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

    public PersonSocialNetworkData link(String link) {
        this.link = link;
        return this;
    }

    /**
     * Link to the person's profile in social network
     *
     * @return link
     */
    @ApiModelProperty(example = "https://linkedin.com/person/VasilyPupkin", value = "Link to the person's profile in social network")

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
        PersonSocialNetworkData personSocialNetworkData = (PersonSocialNetworkData) o;
        return Objects.equals(this.id, personSocialNetworkData.id) &&
                Objects.equals(this.personId, personSocialNetworkData.personId) &&
                Objects.equals(this.socialNetworkId, personSocialNetworkData.socialNetworkId) &&
                Objects.equals(this.link, personSocialNetworkData.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, personId, socialNetworkId, link);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PersonSocialNetworkData {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    personId: ").append(toIndentedString(personId)).append("\n");
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

