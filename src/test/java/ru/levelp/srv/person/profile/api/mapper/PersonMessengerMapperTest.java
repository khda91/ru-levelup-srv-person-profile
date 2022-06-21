package ru.levelp.srv.person.profile.api.mapper;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.levelp.srv.person.profile.api.data.CreatePersonMessengerData;
import ru.levelp.srv.person.profile.infrastructure.annotation.UnitTest;
import ru.levelp.srv.person.profile.model.PersonMessenger;

import java.util.Collections;
import java.util.UUID;

@UnitTest
@DisplayName("PersonMessengerMapper Test")
class PersonMessengerMapperTest {

    private PersonMessengerMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = Mappers.getMapper(PersonMessengerMapper.class);
    }

    @Test
    @DisplayName("should convert PersonMessenger to PersonMessengerData")
    void toPersonMessengerDataTest() {
        // given
        var personMessenger = getPersonMessenger();

        // when
        var personMessengerData = mapper.toPersonMessengerData(personMessenger);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(personMessengerData.getId()).as("id").isEqualTo(personMessenger.getId().toString());
            softly.assertThat(personMessengerData.getPersonId()).as("personId").isEqualTo(personMessenger.getPersonId().toString());
            softly.assertThat(personMessengerData.getMessengerId()).as("messengerId").isEqualTo(personMessenger.getMessengerId());
            softly.assertThat(personMessengerData.getNickname()).as("nickname").isEqualTo(personMessenger.getNickname());
        });
    }

    @Test
    @DisplayName("should convert CreatePersonMessengerData to PersonMessenger")
    void toPersonMessengerWithoutPersonIdTest() {
        // given
        var createPersonMessengerData = getCreatePersonMessengerData();

        // when
        var personMessenger = mapper.toPersonMessenger(createPersonMessengerData);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(personMessenger.getId()).as("id").isNull();
            softly.assertThat(personMessenger.getPersonId()).as("personId").isNull();
            softly.assertThat(personMessenger.getMessengerId()).as("messengerId").isEqualTo(createPersonMessengerData.getMessengerId());
            softly.assertThat(personMessenger.getNickname()).as("nickname").isEqualTo(createPersonMessengerData.getNickname());
        });
    }

    @Test
    @DisplayName("should convert personId, CreatePersonMessengerData to PersonMessenger")
    void toPersonMessengerWithPersonIdTest() {
        // given
        var personIdUuid = UUID.randomUUID();
        var personId = personIdUuid.toString();
        var createPersonMessengerData = getCreatePersonMessengerData();

        // when
        var personMessenger = mapper.toPersonMessenger(personId, createPersonMessengerData);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(personMessenger.getId()).as("id").isNull();
            softly.assertThat(personMessenger.getPersonId()).as("personId").isEqualTo(personIdUuid);
            softly.assertThat(personMessenger.getMessengerId()).as("messengerId").isEqualTo(createPersonMessengerData.getMessengerId());
            softly.assertThat(personMessenger.getNickname()).as("nickname").isEqualTo(createPersonMessengerData.getNickname());
        });
    }

    @Test
    @DisplayName("should convert list of PersonMessenger, limit, offset, count to PersonMessengerListResponse")
    void toPersonMessengerListResponseTest() {
        // given
        final var personMessengers = Collections.singletonList(getPersonMessenger());
        final var limit = 10;
        final var offset = 10;
        final var totalCount = 1;

        // when
        var personMessengerListResponse = mapper.toPersonMessengerListResponse(limit, offset, totalCount, personMessengers);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(personMessengerListResponse.getData()).hasSameSizeAs(personMessengers);
            softly.assertThat(personMessengerListResponse.getMeta().getPagination().getLimit()).isEqualTo(limit);
            softly.assertThat(personMessengerListResponse.getMeta().getPagination().getOffset()).isEqualTo(offset);
            softly.assertThat(personMessengerListResponse.getMeta().getPagination().getTotalCount()).isEqualTo(totalCount);

            var personMessenger = personMessengers.get(0);
            var personMessengerData = personMessengerListResponse.getData().get(0);

            softly.assertThat(personMessengerData.getId()).as("id").isEqualTo(personMessenger.getId().toString());
            softly.assertThat(personMessengerData.getPersonId()).as("personId").isEqualTo(personMessenger.getPersonId().toString());
            softly.assertThat(personMessengerData.getMessengerId()).as("messengerId").isEqualTo(personMessenger.getMessengerId());
            softly.assertThat(personMessengerData.getNickname()).as("nickname").isEqualTo(personMessenger.getNickname());
        });
    }

    private PersonMessenger getPersonMessenger() {
        return PersonMessenger.builder()
                .id(UUID.randomUUID())
                .personId(UUID.randomUUID())
                .messengerId("TELEGRAM")
                .nickname("nick123")
                .build();
    }

    private CreatePersonMessengerData getCreatePersonMessengerData() {
        return new CreatePersonMessengerData()
                .messengerId("WHATS_UP")
                .nickname("nick432");
    }
}
