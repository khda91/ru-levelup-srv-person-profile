package ru.levelp.srv.person.profile.validation;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.levelp.srv.person.profile.api.data.SocialNetworkData;
import ru.levelp.srv.person.profile.api.data.violation.Violation;
import ru.levelp.srv.person.profile.infrastructure.annotation.UnitTest;
import ru.levelp.srv.person.profile.service.SocialNetworkService;
import ru.levelp.srv.person.profile.validation.social.network.SocialNetworkIdValidator;

import java.util.Collections;

import static org.mockito.Mockito.when;

@UnitTest
@DisplayName("SocialNetworkIdValidator Test")
class SocialNetworkIdValidatorTest {

    @Mock
    private SocialNetworkService socialNetworkService;

    @InjectMocks
    private SocialNetworkIdValidator socialNetworkIdValidator;

    @Test
    @DisplayName("return violation if provided social network already exist")
    void shouldReturnViolationIfSocialNetworkAlreadyExistTest() {
        // given
        String id = "FACEBOOK";

        when(socialNetworkService.exists(id)).thenReturn(Boolean.TRUE);

        // when
        var violation = socialNetworkIdValidator.validate(id);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(violation).isPresent();
            softly.assertThat(violation).get().extracting(Violation::getCode).isEqualTo("duplicated_item");
            softly.assertThat(violation).get().extracting(Violation::getRootType).isEqualTo(SocialNetworkData.class);
            softly.assertThat(violation).get().extracting(Violation::getPath).isEqualTo(Collections.singletonList("socialNetworkId"));
            softly.assertThat(violation).get().extracting(Violation::getDescription).isEqualTo(String.format("Social network with id %s already exist", id));
        });
    }
}
