package ru.levelp.srv.person.profile.validation.person;

import org.springframework.util.StringUtils;
import ru.levelp.srv.person.profile.api.data.violation.InvalidFieldViolation;
import ru.levelp.srv.person.profile.api.data.violation.MissingFieldViolation;
import ru.levelp.srv.person.profile.api.data.violation.Violation;
import ru.levelp.srv.person.profile.validation.Validator;

import java.util.Optional;
import java.util.regex.Pattern;

public interface EmailValidator<T> extends Validator<T> {

    Pattern EMAIL_PATTERN = Pattern.compile("^(?i)[a-z0-9!#$%&'*+\\-/=?^_`{|}~]+" +
            "(\\.[a-z0-9!#$%&'*+\\-/=?^_`{|}~]+)*@[a-z0-9-]{1,63}(\\.[a-z0-9-]{1,63})+$");

    default boolean matchesPattern(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    default Optional<Violation> defaultEmailValidate(String email, Class<T> rootType, String path) {
        if (!StringUtils.hasText(email)) {
            return Optional.of(new MissingFieldViolation(rootType, convertPath(path),
                    "Mandatory field email is empty"));
        }

        if (!matchesPattern(email)) {
            return Optional.of(new InvalidFieldViolation(rootType, convertPath(path),
                    "Provided email has incorrect format. Email should match with the patten: "
                            + EMAIL_PATTERN.pattern()));
        }

        return Optional.empty();
    }
}
