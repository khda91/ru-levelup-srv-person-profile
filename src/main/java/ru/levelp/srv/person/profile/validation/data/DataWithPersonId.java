package ru.levelp.srv.person.profile.validation.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a wrapper for player attributes and id.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataWithPersonId<T> {

    private T data;
    private String personId;
}
