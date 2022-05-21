package ru.levelp.srv.person.profile.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@ApiModel(description = "Representing a unique messenger")
public class MessengerData {
    @JsonProperty("id")
    private String id;

    public MessengerData id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Universally unique and immutable identifier of the messenger.
     *
     * @return id
     */
    @ApiModelProperty(example = "TELEGRAM", required = true, value = "Universally unique and immutable identifier of the messenger.")
    @NotNull

    @Size(max = 255)
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
        MessengerData messengerData = (MessengerData) o;
        return Objects.equals(this.id, messengerData.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class MessengerData {\n");

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

