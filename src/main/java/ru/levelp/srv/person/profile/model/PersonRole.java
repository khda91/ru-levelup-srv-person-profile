package ru.levelp.srv.person.profile.model;

import java.util.Objects;
import io.swagger.annotations.ApiModel;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * The role of the person in the LevelUp Trainig Center
 */
public enum PersonRole {

  STUDENT("STUDENT"),

  LECTOR("LECTOR"),

  WORK_INSPECTOR("WORK_INSPECTOR"),

  ADMINISTRATOR("ADMINISTRATOR");

  private String value;

  PersonRole(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static PersonRole fromValue(String value) {
    for (PersonRole b : PersonRole.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

