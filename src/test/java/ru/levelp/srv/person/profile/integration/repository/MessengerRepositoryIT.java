package ru.levelp.srv.person.profile.integration.repository;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import ru.levelp.srv.person.profile.infrastructure.annotation.ComponentTest;
import ru.levelp.srv.person.profile.model.Messenger;
import ru.levelp.srv.person.profile.repository.MessengerRepository;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentTest
@DisplayName("MessengerRepository integration test")
@DirtiesContext
@DatabaseTearDown(value = "classpath:database/messenger/messenger.xml", type = DatabaseOperation.DELETE_ALL)
class MessengerRepositoryIT {

    private static final String TELEGRAM = "TELEGRAM";
    private static final String VIBER = "VIBER";
    private static final String NOT_EXISTING_MESSENGER = "NOT_EXISTING_MESSENGER";
    private static final String NEW_MESSENGER_ID = "NEW_MESSENGER";
    private static final Messenger NEW_MESSENGER = Messenger.builder().id(NEW_MESSENGER_ID).build();
    private static final Integer DEFAULT_LIMIT = 10;
    private static final Integer DEFAULT_OFFSET = 0;

    @Autowired
    private MessengerRepository messengerRepository;

    @Test
    @DisplayName("should find messenger by messengerId")
    @DatabaseSetup(value = "classpath:database/messenger/messenger.xml", type = DatabaseOperation.CLEAN_INSERT)
    void testGetMessengerById() {
        // when
        var messenger = messengerRepository.get(TELEGRAM);

        // then
        assertThat(messenger)
                .as(String.format("Unable to find messenger by messengerId: '%s'", TELEGRAM))
                .isPresent();
    }

    @Test
    @DisplayName("should not find non existing messenger by id")
    @DatabaseSetup(value = "classpath:database/messenger/messenger.xml", type = DatabaseOperation.CLEAN_INSERT)
    void testGetNotExistingMessengerById() {
        // when
        var messenger = messengerRepository.get(NOT_EXISTING_MESSENGER);

        // then
        assertThat(messenger)
                .as(String.format("Messenger by id : '%s' was found", NOT_EXISTING_MESSENGER))
                .isNotPresent();
    }

    @Test
    @DisplayName("should create new messenger")
    @DatabaseSetup(value = "classpath:database/messenger/messenger.xml", type = DatabaseOperation.CLEAN_INSERT)
    void testCreateMessenger() {
        // when
        var messenger = messengerRepository.create(NEW_MESSENGER);

        // then
        var newMessenger = messengerRepository.get(NEW_MESSENGER_ID);
        assertThat(newMessenger)
                .as(String.format("Messenger with id: '%s' was not created", NEW_MESSENGER_ID))
                .isPresent()
                .get()
                .isEqualTo(messenger);
    }

    @Test
    @DisplayName("should delete messenger by id")
    @DatabaseSetup(value = "classpath:database/messenger/messenger.xml", type = DatabaseOperation.CLEAN_INSERT)
    void testDeleteMessenger() {
        // when
        messengerRepository.delete(VIBER);

        // then
        var deletedMessenger = messengerRepository.get(VIBER);
        assertThat(deletedMessenger)
                .as(String.format("Messenger with id: '%s' was not deleted", VIBER))
                .isNotPresent();
    }

    @Test
    @DisplayName("should return empty list when no records was found")
    @DatabaseSetup(value = "classpath:database/messenger/messenger.xml", type = DatabaseOperation.DELETE_ALL)
    void testGetAllReturnsEmptyList() {
        // when
        var messengers = messengerRepository.getAll(DEFAULT_LIMIT, DEFAULT_OFFSET);

        // then
        assertThat(messengers)
                .as("Messengers were found")
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("should return list when records was found")
    @DatabaseSetup(value = "classpath:database/messenger/messenger.xml", type = DatabaseOperation.CLEAN_INSERT)
    void testGetAllReturnsList() {
        // when
        var messengers = messengerRepository.getAll(DEFAULT_LIMIT, DEFAULT_OFFSET);

        // then
        assertThat(messengers)
                .as("Messengers were not found")
                .isNotNull()
                .hasSize(2)
                .containsExactlyInAnyOrder(Messenger.builder().id(TELEGRAM).build(),
                        Messenger.builder().id(VIBER).build());
    }

    @Test
    @DisplayName("should return count messengers")
    @DatabaseSetup(value = "classpath:database/messenger/messenger.xml", type = DatabaseOperation.CLEAN_INSERT)
    void testGetTotalCountReturnsCountMessengers() {
        // when
        var count = messengerRepository.getTotalCount();

        // then
        assertThat(count)
                .as("Unable to count messengers")
                .isEqualTo(2);
    }

}
