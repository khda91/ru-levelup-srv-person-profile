package ru.levelp.srv.person.profile.api.mapper;

import java.util.UUID;

public interface UuidToStringMappingStrategy {

    default String map(UUID value) {
        return value.toString();
    }

    default UUID map(String value) {
        return UUID.fromString(value);
    }
}
