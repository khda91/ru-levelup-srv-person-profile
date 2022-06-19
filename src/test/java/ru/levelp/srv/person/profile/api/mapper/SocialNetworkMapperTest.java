package ru.levelp.srv.person.profile.api.mapper;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.levelp.srv.person.profile.api.data.SocialNetworkData;
import ru.levelp.srv.person.profile.infrastructure.annotation.UnitTest;
import ru.levelp.srv.person.profile.model.SocialNetwork;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@UnitTest
@DisplayName("SocialNetworkMapper Test")
class SocialNetworkMapperTest {

    private static final SocialNetwork FACEBOOK_SOCIAL_NETWORK = SocialNetwork.builder().id("FACEBOOK").build();
    private static final SocialNetworkData VK_SOCIAL_NETWORK_DATA = new SocialNetworkData().id("VK");

    private SocialNetworkMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(SocialNetworkMapper.class);
    }

    @Test
    @DisplayName("should convert SocialNetwork to SocialNetworkData")
    void toSocialNetworkDataTest() {
        // when
        var socialNetworkData = mapper.toSocialNetworkData(FACEBOOK_SOCIAL_NETWORK);

        // then
        assertThat(socialNetworkData)
                .as("IDs does not matched")
                .extracting(SocialNetworkData::getId)
                .isEqualTo(FACEBOOK_SOCIAL_NETWORK.getId());
    }

    @Test
    @DisplayName("should convert ID to SocialNetwork")
    void toSocialNetworkTest() {
        // given
        final var socialNetworkId = "ODNOCLASNIKI";

        // when
        var socialNetwork = mapper.toSocialNetwork(socialNetworkId);

        // then
        assertThat(socialNetwork)
                .as("IDs does not matched")
                .extracting(SocialNetwork::getId)
                .isEqualTo(socialNetworkId);
    }

    @Test
    @DisplayName("should convert list of SocialNetwork, limit, offset, totalCount to SocialNetworkListResponse")
    void toMessengerListResponseTest() {
        // given
        final var socialNetworksData = Collections.singletonList(VK_SOCIAL_NETWORK_DATA);
        final var limit = 10;
        final var offset = 10;
        final var totalCount = 1;

        // when
        var socialNetworkListResponse = mapper.toSocialNetworkListResponse(limit, offset, totalCount, socialNetworksData);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(socialNetworkListResponse.getData()).hasSameSizeAs(socialNetworksData);
            softly.assertThat(socialNetworkListResponse.getMeta().getPagination().getLimit()).isEqualTo(limit);
            softly.assertThat(socialNetworkListResponse.getMeta().getPagination().getOffset()).isEqualTo(offset);
            softly.assertThat(socialNetworkListResponse.getMeta().getPagination().getTotalCount()).isEqualTo(totalCount);

            var socialNetwork = socialNetworksData.get(0);
            var socialNetworkData = socialNetworkListResponse.getData().get(0);

            softly.assertThat(socialNetworkData.getId()).isEqualTo(socialNetwork.getId());
        });
    }
}
