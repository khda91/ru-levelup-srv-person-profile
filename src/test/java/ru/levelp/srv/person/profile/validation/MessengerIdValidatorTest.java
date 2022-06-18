package ru.levelp.srv.person.profile.validation;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.levelp.srv.person.profile.api.data.MessengerData;
import ru.levelp.srv.person.profile.api.data.violation.Violation;
import ru.levelp.srv.person.profile.infrastructure.annotation.UnitTest;
import ru.levelp.srv.person.profile.service.MessengerService;
import ru.levelp.srv.person.profile.validation.messenger.MessengerIdValidator;

import java.util.Collections;

import static org.mockito.Mockito.when;

@UnitTest
@DisplayName("MessengerIdValidator Test")
class MessengerIdValidatorTest {

    @Mock
    private MessengerService messengerService;

    @InjectMocks
    private MessengerIdValidator messengerIdValidator;

    @Test
    @DisplayName("return violation if provided messenger already exist")
    void shouldReturnViolationIfMessengerAlreadyExistTest() {
        // given
        String id = "VIBER";

        when(messengerService.exists(id)).thenReturn(Boolean.TRUE);

        // when
        var violation = messengerIdValidator.validate(id);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(violation).isPresent();
            softly.assertThat(violation).get().extracting(Violation::getCode).isEqualTo("duplicated_item");
            softly.assertThat(violation).get().extracting(Violation::getRootType).isEqualTo(MessengerData.class);
            softly.assertThat(violation).get().extracting(Violation::getPath).isEqualTo(Collections.singletonList("messengerId"));
            softly.assertThat(violation).get().extracting(Violation::getDescription).isEqualTo(String.format("Messenger with id %s already exist", id));
        });
    }
}
