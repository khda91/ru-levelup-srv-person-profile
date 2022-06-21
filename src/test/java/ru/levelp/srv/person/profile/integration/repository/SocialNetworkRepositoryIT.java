package ru.levelp.srv.person.profile.integration.repository;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import ru.levelp.srv.person.profile.infrastructure.annotation.ComponentTest;
import ru.levelp.srv.person.profile.model.SocialNetwork;
import ru.levelp.srv.person.profile.repository.SocialNetworkRepository;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentTest
@DisplayName("SocialNetworkRepository integration test")
@DirtiesContext
@DatabaseTearDown(value = "classpath:database/social_network/social_network.xml", type = DatabaseOperation.DELETE_ALL)
class SocialNetworkRepositoryIT {

    private static final String VK = "VK";
    private static final String FACEBOOK = "FACEBOOK";
    private static final String LINKED_ID = "LINKED_IN";
    private static final String NOT_EXISTING_SOCIAL_NETWORK = "NOT_EXISTING_SOCIAL_NETWORK";
    private static final String NEW_SOCIAL_NETWORK_ID = "NEW_SOCIAL_NETWORK";
    private static final SocialNetwork NEW_SOCIAL_NETWORK = SocialNetwork.builder().id(NEW_SOCIAL_NETWORK_ID).build();
    private static final Integer DEFAULT_LIMIT = 10;
    private static final Integer DEFAULT_OFFSET = 0;

    @Autowired
    private SocialNetworkRepository socialNetworkRepository;

    @Test
    @DisplayName("should find social network by socialNetworkId")
    @DatabaseSetup(value = "classpath:database/social_network/social_network.xml", type = DatabaseOperation.CLEAN_INSERT)
    void testGetSocialNetworkById() {
        // when
        var socialNetwork = socialNetworkRepository.get(VK);

        // then
        assertThat(socialNetwork)
                .as(String.format("Unable to find social network by socialNetworkId: '%s'", VK))
                .isPresent();
    }

    @Test
    @DisplayName("should not find non existing social network by id")
    @DatabaseSetup(value = "classpath:database/social_network/social_network.xml", type = DatabaseOperation.CLEAN_INSERT)
    void testGetNotExistingSocialNetworkById() {
        // when
        var socialNetwork = socialNetworkRepository.get(NOT_EXISTING_SOCIAL_NETWORK);

        // then
        assertThat(socialNetwork)
                .as(String.format("Social network by id : '%s' was found", NOT_EXISTING_SOCIAL_NETWORK))
                .isNotPresent();
    }

    @Test
    @DisplayName("should create new social network")
    @DatabaseSetup(value = "classpath:database/social_network/social_network.xml", type = DatabaseOperation.CLEAN_INSERT)
    void testCreateSocialNetwork() {
        // when
        var socialNetwork = socialNetworkRepository.create(NEW_SOCIAL_NETWORK);

        // then
        var newSocialNetwork = socialNetworkRepository.get(NEW_SOCIAL_NETWORK_ID);
        assertThat(newSocialNetwork)
                .as(String.format("Social network with id: '%s' was not created", NEW_SOCIAL_NETWORK_ID))
                .isPresent()
                .get()
                .isEqualTo(socialNetwork);
    }

    @Test
    @DisplayName("should delete social network by id")
    @DatabaseSetup(value = "classpath:database/social_network/social_network.xml", type = DatabaseOperation.CLEAN_INSERT)
    void testDeleteSocialNetwork() {
        // when
        socialNetworkRepository.delete(FACEBOOK);

        // then
        var deletedSocialNetwork = socialNetworkRepository.get(FACEBOOK);
        assertThat(deletedSocialNetwork)
                .as(String.format("Social network with id: '%s' was not deleted", FACEBOOK))
                .isNotPresent();
    }

    @Test
    @DisplayName("should return empty list when no records was found")
    @DatabaseSetup(value = "classpath:database/social_network/social_network.xml", type = DatabaseOperation.DELETE_ALL)
    void testGetAllReturnsEmptyList() {
        // when
        var socialNetworks = socialNetworkRepository.getAll(DEFAULT_LIMIT, DEFAULT_OFFSET);

        // then
        assertThat(socialNetworks)
                .as("Social networks were found")
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("should return list when records was found")
    @DatabaseSetup(value = "classpath:database/social_network/social_network.xml", type = DatabaseOperation.CLEAN_INSERT)
    void testGetAllReturnsList() {
        // when
        var socialNetworks = socialNetworkRepository.getAll(DEFAULT_LIMIT, DEFAULT_OFFSET);

        // then
        assertThat(socialNetworks)
                .as("Social networks were not found")
                .isNotNull()
                .hasSize(3)
                .containsExactlyInAnyOrder(SocialNetwork.builder().id(VK).build(),
                        SocialNetwork.builder().id(FACEBOOK).build(),SocialNetwork.builder().id(LINKED_ID).build());
    }

    @Test
    @DisplayName("should return count social networks")
    @DatabaseSetup(value = "classpath:database/social_network/social_network.xml", type = DatabaseOperation.CLEAN_INSERT)
    void testGetTotalCountReturnsCountSocialNetworks() {
        // when
        var count = socialNetworkRepository.getTotalCount();

        // then
        assertThat(count)
                .as("Unable to count social networks")
                .isEqualTo(3);
    }
}
