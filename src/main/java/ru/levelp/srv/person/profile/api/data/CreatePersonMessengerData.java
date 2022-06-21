package ru.levelp.srv.person.profile.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Represents attributes required to add a messenger with some predefined type to a person.
 */
@ApiModel(description = "Represents attributes required to add a messenger with some predefined type to a person.")
public class CreatePersonMessengerData {
    @JsonProperty("messengerId")
    private String messengerId;

    @JsonProperty("nickname")
    private String nickname;

    public CreatePersonMessengerData messengerId(String messengerId) {
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

    public CreatePersonMessengerData nickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    /**
     * User name of the person in messenger.
     *
     * @return nickname
     */
    @ApiModelProperty(example = "@ivan_ivanov", value = "User name of the person in messenger.")

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
        CreatePersonMessengerData createPersonMessengerData = (CreatePersonMessengerData) o;
        return Objects.equals(this.messengerId, createPersonMessengerData.messengerId) &&
                Objects.equals(this.nickname, createPersonMessengerData.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messengerId, nickname);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CreatePersonMessengerData {\n");

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

