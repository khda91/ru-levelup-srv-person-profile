package ru.levelp.srv.person.profile.api.mapper;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.levelp.srv.person.profile.api.data.CreatePersonSocialNetworkData;
import ru.levelp.srv.person.profile.infrastructure.annotation.UnitTest;
import ru.levelp.srv.person.profile.model.PersonSocialNetwork;

import java.util.Collections;
import java.util.UUID;

@UnitTest
@DisplayName("PersonSocialNetworkMapper Test")
class PersonSocialNetworkMapperTest {

    private PersonSocialNetworkMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = Mappers.getMapper(PersonSocialNetworkMapper.class);
    }

    @Test
    @DisplayName("should convert PersonSocialNetwork to PersonSocialNetwork")
    void toPersonSocialNetworkDataTest() {
        // given
        var personSocialNetwork = getPersonSocialNetwork();

        // when
        var personSocialNetworkData = mapper.toPersonSocialNetworkData(personSocialNetwork);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(personSocialNetworkData.getId()).as("id").isEqualTo(personSocialNetwork.getId().toString());
            softly.assertThat(personSocialNetworkData.getPersonId()).as("personId").isEqualTo(personSocialNetwork.getPersonId().toString());
            softly.assertThat(personSocialNetworkData.getSocialNetworkId()).as("socialNetworkId").isEqualTo(personSocialNetwork.getSocialNetworkId());
            softly.assertThat(personSocialNetworkData.getLink()).as("link").isEqualTo(personSocialNetwork.getLink());
        });
    }

    @Test
    @DisplayName("should convert CreatePersonSocialNetworkData to PersonSocialNetwork")
    void toPersonSocialNetworkWithoutPersonIdTest() {
        // given
        var createPersonSocialNetworkData = getCreatePersonSocialNetworkData();

        // when
        var personSocialNetwork = mapper.toPersonSocialNetwork(createPersonSocialNetworkData);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(personSocialNetwork.getId()).as("id").isNull();
            softly.assertThat(personSocialNetwork.getPersonId()).as("personId").isNull();
            softly.assertThat(personSocialNetwork.getSocialNetworkId()).as("socialNetworkId").isEqualTo(personSocialNetwork.getSocialNetworkId());
            softly.assertThat(personSocialNetwork.getLink()).as("link").isEqualTo(personSocialNetwork.getLink());
        });
    }

    @Test
    @DisplayName("should convert personId, CreatePersonSocialNetworkData to PersonSocialNetwork")
    void toPersonSocialNetworkWithPersonIdTest() {
        // given
        var personIdUuid = UUID.randomUUID();
        var personId = personIdUuid.toString();
        var createPersonSocialNetworkData = getCreatePersonSocialNetworkData();

        // when
        var personSocialNetwork = mapper.toPersonSocialNetwork(personId, createPersonSocialNetworkData);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(personSocialNetwork.getId()).as("id").isNull();
            softly.assertThat(personSocialNetwork.getPersonId()).as("personId").isEqualTo(personIdUuid);
            softly.assertThat(personSocialNetwork.getSocialNetworkId()).as("socialNetworkId").isEqualTo(createPersonSocialNetworkData.getSocialNetworkId());
            softly.assertThat(personSocialNetwork.getLink()).as("link").isEqualTo(createPersonSocialNetworkData.getLink());
        });
    }

    @Test
    @DisplayName("should convert list of PersonSocialNetwork, limit, offset, count to PersonSocialNetworkListResponse")
    void toPersonSocialNetworkListResponseTest() {
        // given
        final var personSocialNetworks = Collections.singletonList(getPersonSocialNetwork());
        final var limit = 10;
        final var offset = 10;
        final var totalCount = 1;

        // when
        var socialNetworkListResponse = mapper.toPersonSocialNetworkListResponse(limit, offset, totalCount, personSocialNetworks);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(socialNetworkListResponse.getData()).hasSameSizeAs(personSocialNetworks);
            softly.assertThat(socialNetworkListResponse.getMeta().getPagination().getLimit()).isEqualTo(limit);
            softly.assertThat(socialNetworkListResponse.getMeta().getPagination().getOffset()).isEqualTo(offset);
            softly.assertThat(socialNetworkListResponse.getMeta().getPagination().getTotalCount()).isEqualTo(totalCount);

            var personMessenger = personSocialNetworks.get(0);
            var personMessengerData = socialNetworkListResponse.getData().get(0);

            softly.assertThat(personMessengerData.getId()).as("id").isEqualTo(personMessenger.getId().toString());
            softly.assertThat(personMessengerData.getPersonId()).as("personId").isEqualTo(personMessenger.getPersonId().toString());
            softly.assertThat(personMessengerData.getSocialNetworkId()).as("socialNetworkId").isEqualTo(personMessenger.getSocialNetworkId());
            softly.assertThat(personMessengerData.getLink()).as("link").isEqualTo(personMessenger.getLink());
        });
    }

    private PersonSocialNetwork getPersonSocialNetwork() {
        return PersonSocialNetwork.builder()
                .id(UUID.randomUUID())
                .personId(UUID.randomUUID())
                .socialNetworkId("VK")
                .link("https://vk.com/id123456")
                .build();
    }

    private CreatePersonSocialNetworkData getCreatePersonSocialNetworkData() {
        return new CreatePersonSocialNetworkData()
                .socialNetworkId("VK")
                .link("https://vk.com/id123456");
    }
}
