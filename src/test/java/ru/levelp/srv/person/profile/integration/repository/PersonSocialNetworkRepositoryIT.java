package ru.levelp.srv.person.profile.integration.repository;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import ru.levelp.srv.person.profile.infrastructure.annotation.ComponentTest;
import ru.levelp.srv.person.profile.model.PersonSocialNetwork;
import ru.levelp.srv.person.profile.repository.PersonSocialNetworkRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentTest
@DisplayName("PersonSocialNetworkRepository integration test")
@DirtiesContext
@DatabaseSetup(value = {"classpath:database/social_network/social_network.xml", "classpath:database/person/person.xml"},
        type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = {"classpath:database/social_network/social_network.xml", "classpath:database/person/person.xml"},
        type = DatabaseOperation.DELETE_ALL)
class PersonSocialNetworkRepositoryIT {

    private static final Integer DEFAULT_LIMIT = 10;
    private static final Integer DEFAULT_OFFSET = 0;
    private static final String PERSON_ID = "ec9655d2-e35d-4f45-be81-7ed4abc7cd14";

    private static final PersonSocialNetwork VK_DATA = PersonSocialNetwork.builder()
            .id(UUID.fromString("48573702-a147-43ad-8ebd-05e103a2397a"))
            .personId(UUID.fromString(PERSON_ID))
            .socialNetworkId("VK")
            .link("https://vk.com/id123456")
            .build();

    private static final PersonSocialNetwork FACEBOOK_DATA = PersonSocialNetwork.builder()
            .id(UUID.fromString("e10ee189-019c-4fa4-a32d-7c47b17f21d3"))
            .personId(UUID.fromString(PERSON_ID))
            .socialNetworkId("FACEBOOK")
            .link("https://facebook.com/person/ivanov_ivan")
            .build();

    private static final PersonSocialNetwork LINKED_IN_DATA = PersonSocialNetwork.builder()
            .id(UUID.randomUUID())
            .personId(UUID.fromString(PERSON_ID))
            .socialNetworkId("LINKED_IN")
            .link("https://linkedin.com/profile/ivanov_ivan468453")
            .build();

    @Autowired
    private PersonSocialNetworkRepository personSocialNetworkRepository;

    @Test
    @DisplayName("should find linked social network with person by personId and socialNetworkId")
    @DatabaseSetup(value = "classpath:database/person/person_social_network.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:database/person/person_social_network.xml", type = DatabaseOperation.DELETE_ALL)
    void getPersonSocialNetworkTest() {
        // when
        var personSocialNetwork = personSocialNetworkRepository.getPersonSocialNetwork(PERSON_ID, VK_DATA.getSocialNetworkId());

        // then
        assertThat(personSocialNetwork)
                .as("Unable to find social network %s for person %s", VK_DATA.getSocialNetworkId(), PERSON_ID)
                .isPresent()
                .get()
                .isEqualTo(VK_DATA);
    }

    @Test
    @DisplayName("should create person social network")
    @DatabaseSetup(value = "classpath:database/person/person_social_network.xml", type = DatabaseOperation.DELETE_ALL)
    void shouldCreatePersonSocialNetworkTest() {
        // when
        personSocialNetworkRepository.create(LINKED_IN_DATA);

        // then
        var count = personSocialNetworkRepository.count(LINKED_IN_DATA.getPersonId().toString(), Optional.empty());
        assertThat(count)
                .as("Person social network was not created")
                .isEqualTo(1);
    }

    @Test
    @DisplayName("should create person social network")
    @DatabaseSetup(value = "classpath:database/person/person_social_network.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:database/person/person_social_network.xml", type = DatabaseOperation.DELETE_ALL)
    void shouldDeletePersonSocialNetworkTest() {
        // when
        personSocialNetworkRepository.delete(FACEBOOK_DATA.getId());

        // then
        var personSocialNetwork = personSocialNetworkRepository
                .getPersonSocialNetwork(FACEBOOK_DATA.getPersonId().toString(), FACEBOOK_DATA.getSocialNetworkId());
        assertThat(personSocialNetwork)
                .as("Person social network was not deleted")
                .isNotPresent();
    }

    @Test
    @DisplayName("should return count person social network")
    @DatabaseSetup(value = "classpath:database/person/person_social_network.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:database/person/person_social_network.xml", type = DatabaseOperation.DELETE_ALL)
    void getTotalCountReturnsCountPersonSocialNetworkTest() {
        // when
        var count = personSocialNetworkRepository.count(PERSON_ID, Optional.empty());

        // then
        assertThat(count)
                .as("Unable to count person social networks")
                .isEqualTo(2);
    }

    @Test
    @DisplayName("should return empty list when no records was found")
    @DatabaseSetup(value = "classpath:database/person/person_social_network.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:database/person/person_social_network.xml", type = DatabaseOperation.DELETE_ALL)
    void testGetAllReturnsEmptyList() {
        // when
        var personSocialNetworks = personSocialNetworkRepository
                .getAll(UUID.randomUUID().toString(), DEFAULT_LIMIT, DEFAULT_OFFSET, Optional.empty());

        // then
        assertThat(personSocialNetworks)
                .as("Person social networks were found")
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("should return list when records was found")
    @DatabaseSetup(value = "classpath:database/person/person_social_network.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:database/person/person_social_network.xml", type = DatabaseOperation.DELETE_ALL)
    void testGetAllReturnsList() {
        // when
        var personSocialNetworks = personSocialNetworkRepository.getAll(PERSON_ID, DEFAULT_LIMIT, DEFAULT_OFFSET, Optional.empty());

        // then
        assertThat(personSocialNetworks)
                .as("Person social networks were not found")
                .isNotNull()
                .hasSize(2)
                .containsExactlyInAnyOrder(FACEBOOK_DATA, VK_DATA);
    }
}
