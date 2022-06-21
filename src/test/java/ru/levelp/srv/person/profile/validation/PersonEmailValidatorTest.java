package ru.levelp.srv.person.profile.validation;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.levelp.srv.person.profile.api.data.CreatePersonData;
import ru.levelp.srv.person.profile.api.data.violation.Violation;
import ru.levelp.srv.person.profile.infrastructure.annotation.UnitTest;
import ru.levelp.srv.person.profile.service.PeopleService;
import ru.levelp.srv.person.profile.validation.person.PersonEmailValidator;

import java.util.Collections;
import java.util.regex.Pattern;

import static org.mockito.Mockito.when;

@UnitTest
@DisplayName("PersonEmailValidator Test")
class PersonEmailValidatorTest {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^(?i)[a-z0-9!#$%&'*+\\-/=?^_`{|}~]+" +
            "(\\.[a-z0-9!#$%&'*+\\-/=?^_`{|}~]+)*@[a-z0-9-]{1,63}(\\.[a-z0-9-]{1,63})+$");

    @Mock
    private PeopleService peopleService;

    @InjectMocks
    private PersonEmailValidator personEmailValidator;

    @Test
    @DisplayName("return violation if provided email does not match with pattern")
    void shouldReturnViolationIfEmailDoesNotMatchWithPattern() {
        // given
        var request = new CreatePersonData().email("email@kkk");

        // when
        var violation = personEmailValidator.validate(request);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(violation).isPresent();
            softly.assertThat(violation).get().extracting(Violation::getCode).isEqualTo("invalid_field");
            softly.assertThat(violation).get().extracting(Violation::getRootType).isEqualTo(CreatePersonData.class);
            softly.assertThat(violation).get().extracting(Violation::getPath).isEqualTo(Collections.singletonList("email"));
            softly.assertThat(violation).get().extracting(Violation::getDescription).isEqualTo(String.format("Provided email has incorrect format. Email should match with the patten: %s", EMAIL_PATTERN.pattern()));
        });
    }

    @Test
    @DisplayName("return violation if provided email is empty")
    void shouldReturnViolationIfEmailIsEmpty() {
        // given
        var request = new CreatePersonData().email("");

        // when
        var violation = personEmailValidator.validate(request);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(violation).isPresent();
            softly.assertThat(violation).get().extracting(Violation::getCode).isEqualTo("missing_field");
            softly.assertThat(violation).get().extracting(Violation::getRootType).isEqualTo(CreatePersonData.class);
            softly.assertThat(violation).get().extracting(Violation::getPath).isEqualTo(Collections.singletonList("email"));
            softly.assertThat(violation).get().extracting(Violation::getDescription).isEqualTo("Mandatory field email is empty");
        });
    }

    @Test
    @DisplayName("return violation if provided email already exist")
    void shouldReturnViolationIfEmailAlreadyExist() {
        // given
        var request = new CreatePersonData().email("mail@mail.ru");

        when(peopleService.emailExist(request.getEmail())).thenReturn(Boolean.TRUE);

        // when
        var violation = personEmailValidator.validate(request);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(violation).isPresent();
            softly.assertThat(violation).get().extracting(Violation::getCode).isEqualTo("field_not_unique");
            softly.assertThat(violation).get().extracting(Violation::getRootType).isEqualTo(CreatePersonData.class);
            softly.assertThat(violation).get().extracting(Violation::getPath).isEqualTo(Collections.singletonList("email"));
            softly.assertThat(violation).get().extracting(Violation::getDescription).isEqualTo("Person with provided email already exists");
        });
    }
}
