package ru.levelp.srv.person.profile.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;
import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SqlUtils {

    public static <T> Optional<T> emptyResultExceptionHelper(Supplier<T> entitySupplier) {
        try {
            return Optional.ofNullable(entitySupplier.get());
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
