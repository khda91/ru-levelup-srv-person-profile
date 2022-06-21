package ru.levelp.srv.person.profile.integration.repository;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import ru.levelp.srv.person.profile.infrastructure.annotation.ComponentTest;
import ru.levelp.srv.person.profile.model.PersonMessenger;
import ru.levelp.srv.person.profile.repository.PersonMessengerRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentTest
@DisplayName("PersonMessengerRepository integration test")
@DirtiesContext
@DatabaseSetup(value = {"classpath:database/messenger/messenger.xml", "classpath:database/person/person.xml"},
        type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = {"classpath:database/messenger/messenger.xml", "classpath:database/person/person.xml"},
        type = DatabaseOperation.DELETE_ALL)
class PersonMessengerRepositoryIT {

    private static final Integer DEFAULT_LIMIT = 10;
    private static final Integer DEFAULT_OFFSET = 0;
    private static final String PERSON_ID = "ec9655d2-e35d-4f45-be81-7ed4abc7cd14";

    private static final PersonMessenger TELEGRAM_DATA = PersonMessenger.builder()
            .id(UUID.fromString("9f0ca8c5-36f0-496c-a05e-df73ce31290a"))
            .personId(UUID.fromString(PERSON_ID))
            .messengerId("TELEGRAM")
            .nickname("@ivanovi")
            .build();

    private static final PersonMessenger VIBER_DATA = PersonMessenger.builder()
            .id(UUID.fromString("7fa4fc70-f6fd-4dc6-9379-1e2fa3130c0d"))
            .personId(UUID.fromString(PERSON_ID))
            .messengerId("VIBER")
            .nickname("ivan_ivanov")
            .build();

    private static final PersonMessenger WHATS_UP_DATA = PersonMessenger.builder()
            .id(UUID.randomUUID())
            .personId(UUID.fromString(PERSON_ID))
            .messengerId("WHATS_UP")
            .nickname("ivan_i")
            .build();

    @Autowired
    private PersonMessengerRepository personMessengerRepository;

    @Test
    @DisplayName("should find linked messenger with person by personId and messengerId")
    @DatabaseSetup(value = "classpath:database/person/person_messenger.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:database/person/person_messenger.xml", type = DatabaseOperation.DELETE_ALL)
    void getPersonMessengerTest() {
        // when
        var personMessenger = personMessengerRepository.getPersonMessenger(PERSON_ID, TELEGRAM_DATA.getMessengerId());

        // then
        assertThat(personMessenger)
                .as("Unable to find messenger %s for person %s", TELEGRAM_DATA.getMessengerId(), PERSON_ID)
                .isPresent()
                .get()
                .isEqualTo(TELEGRAM_DATA);
    }

    @Test
    @DisplayName("should create person messenger")
    @DatabaseSetup(value = "classpath:database/person/person_messenger.xml", type = DatabaseOperation.DELETE_ALL)
    void shouldCreatePersonMessengerTest() {
        // when
        personMessengerRepository.create(WHATS_UP_DATA);

        // then
        var count = personMessengerRepository.count(WHATS_UP_DATA.getPersonId().toString(), Optional.empty());
        assertThat(count)
                .as("Person messenger was not created")
                .isEqualTo(1);
    }

    @Test
    @DisplayName("should create person messenger")
    @DatabaseSetup(value = "classpath:database/person/person_messenger.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:database/person/person_messenger.xml", type = DatabaseOperation.DELETE_ALL)
    void shouldDeletePersonMessengerTest() {
        // when
        personMessengerRepository.delete(VIBER_DATA.getId());

        // then
        var personMessenger = personMessengerRepository.getPersonMessenger(VIBER_DATA.getPersonId().toString(), VIBER_DATA.getMessengerId());
        assertThat(personMessenger)
                .as("Person messenger was not deleted")
                .isNotPresent();
    }

    @Test
    @DisplayName("should return count person messengers")
    @DatabaseSetup(value = "classpath:database/person/person_messenger.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:database/person/person_messenger.xml", type = DatabaseOperation.DELETE_ALL)
    void getTotalCountReturnsCountPersonMessengersTest() {
        // when
        var count = personMessengerRepository.count(PERSON_ID, Optional.empty());

        // then
        assertThat(count)
                .as("Unable to count person messengers")
                .isEqualTo(2);
    }

    @Test
    @DisplayName("should return empty list when no records was found")
    @DatabaseSetup(value = "classpath:database/person/person_messenger.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:database/person/person_messenger.xml", type = DatabaseOperation.DELETE_ALL)
    void testGetAllReturnsEmptyList() {
        // when
        var messengers = personMessengerRepository.getAll(UUID.randomUUID().toString(), DEFAULT_LIMIT, DEFAULT_OFFSET, Optional.empty());

        // then
        assertThat(messengers)
                .as("Person messengers were found")
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("should return list when records was found")
    @DatabaseSetup(value = "classpath:database/person/person_messenger.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:database/person/person_messenger.xml", type = DatabaseOperation.DELETE_ALL)
    void testGetAllReturnsList() {
        // when
        var messengers = personMessengerRepository.getAll(PERSON_ID, DEFAULT_LIMIT, DEFAULT_OFFSET, Optional.empty());

        // then
        assertThat(messengers)
                .as("Person messengers were not found")
                .isNotNull()
                .hasSize(2)
                .containsExactlyInAnyOrder(VIBER_DATA, TELEGRAM_DATA);
    }
}
