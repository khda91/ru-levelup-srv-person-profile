package ru.levelp.srv.person.profile.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Represents a messengers of a person.
 */
@ApiModel(description = "Represents a messengers of a person.")
public class PersonMessengerData {
    @JsonProperty("id")
    private String id;

    @JsonProperty("personId")
    private String personId;

    @JsonProperty("messengerId")
    private String messengerId;

    @JsonProperty("nickname")
    private String nickname;

    public PersonMessengerData id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Universally unique and immutable identifier of the person messenger.
     *
     * @return id
     */
    @ApiModelProperty(example = "123e4567-e89b-12d3-a456-426655440000", value = "Universally unique and immutable identifier of the person messenger.")

    @Size(max = 255)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PersonMessengerData personId(String personId) {
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

    public PersonMessengerData messengerId(String messengerId) {
        this.messengerId = messengerId;
        return this;
    }

    /**
     * Universally unique and immutable identifier of the messenger.
     *
     * @return messengerId
     */
    @ApiModelProperty(example = "TELEGRAM", value = "Universally unique and immutable identifier of the messenger.")

    @Pattern(regexp = "([A-Z0-9_]*)")
    @Size(min = 2, max = 255)
    public String getMessengerId() {
        return messengerId;
    }

    public void setMessengerId(String messengerId) {
        this.messengerId = messengerId;
    }

    public PersonMessengerData nickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    /**
     * Link to the person's profile in messenger
     *
     * @return nickname
     */
    @ApiModelProperty(example = "@VasilyPupkin", value = "Link to the person's profile in messenger")

    @Size(max = 255)
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonMessengerData personMessengerData = (PersonMessengerData) o;
        return Objects.equals(this.id, personMessengerData.id) &&
                Objects.equals(this.personId, personMessengerData.personId) &&
                Objects.equals(this.messengerId, personMessengerData.messengerId) &&
                Objects.equals(this.nickname, personMessengerData.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, personId, messengerId, nickname);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PersonMessengerData {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    personId: ").append(toIndentedString(personId)).append("\n");
        sb.append("    messengerId: ").append(toIndentedString(messengerId)).append("\n");
        sb.append("    nickname: ").append(toIndentedString(nickname)).append("\n");
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

