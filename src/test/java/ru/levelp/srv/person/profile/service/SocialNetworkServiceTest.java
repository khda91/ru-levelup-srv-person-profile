package ru.levelp.srv.person.profile.service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import ru.levelp.srv.person.profile.api.mapper.SocialNetworkMapper;
import ru.levelp.srv.person.profile.infrastructure.annotation.UnitTest;
import ru.levelp.srv.person.profile.model.SocialNetwork;
import ru.levelp.srv.person.profile.repository.SocialNetworkRepository;
import ru.levelp.srv.person.profile.validation.social.network.SocialNetworkValidationAggregator;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@UnitTest
@DisplayName("SocialNetworkService Test")
class SocialNetworkServiceTest {

    @Spy
    private final SocialNetworkMapper socialNetworkMapper = Mappers.getMapper(SocialNetworkMapper.class);

    @Mock
    private SocialNetworkValidationAggregator socialNetworkValidationAggregator;

    @Mock
    private SocialNetworkRepository socialNetworkRepository;

    @Captor
    private ArgumentCaptor<SocialNetwork> socialNetworkArgumentCaptor;

    @Spy
    @InjectMocks
    private SocialNetworkService socialNetworkService;

    @Test
    @DisplayName("should return social networks id when limit, offset are received")
    void getAllTest() {
        // given social network's data
        final var limit = 10;
        final var offset = 0;
        final var totalCount = 1;

        final var socialNetworks = Collections.singletonList(SocialNetwork.builder().id("FACEBOOK").build());

        when(socialNetworkRepository.getAll(eq(limit), eq(offset))).thenReturn(socialNetworks);
        when(socialNetworkRepository.getTotalCount()).thenReturn(socialNetworks.size());

        // when
        var socialNetworkListResponse = socialNetworkService.getSocialNetworks(limit, offset);

        // then should return social network data
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(socialNetworkListResponse.getData()).hasSameSizeAs(socialNetworks);
            softly.assertThat(socialNetworkListResponse.getMeta().getPagination().getLimit()).isEqualTo(limit);
            softly.assertThat(socialNetworkListResponse.getMeta().getPagination().getOffset()).isEqualTo(offset);
            softly.assertThat(socialNetworkListResponse.getMeta().getPagination().getTotalCount()).isEqualTo(totalCount);

            var socialNetwork = socialNetworks.get(0);
            var socialNetworkData = socialNetworkListResponse.getData().get(0);

            softly.assertThat(socialNetworkData.getId()).isEqualTo(socialNetwork.getId());
        });
        // and fetch data from database
        verify(socialNetworkRepository, times(1)).getAll(limit, offset);
        verify(socialNetworkRepository, times(1)).getTotalCount();
    }

    @Test
    @DisplayName("should created social network")
    void createdSocialNetworkTest() {
        // given
        final var socialNetworkId = "FACEBOOK";
        final var socialNetwork = SocialNetwork.builder().id(socialNetworkId).build();

        when(socialNetworkRepository.create(socialNetwork)).thenReturn(socialNetwork);

        // when
        socialNetworkService.createSocialNetwork(socialNetworkId);

        // then
        verify(socialNetworkRepository, times(1)).create(socialNetworkArgumentCaptor.capture());

        var actualSocialNetwork = socialNetworkArgumentCaptor.getValue();
        assertThat(actualSocialNetwork)
                .as("IDs does not matched")
                .extracting(SocialNetwork::getId)
                .isEqualTo(socialNetworkId);
    }

    @Test
    @DisplayName("should delete social network")
    void deleteSocialNetworkTest() {
        // given
        final var socialNetworkId = "VK";

        // when
        socialNetworkService.removeSocialNetwork(socialNetworkId);

        // then
        verify(socialNetworkRepository, times(1)).delete(socialNetworkId);
    }

    @Test
    @DisplayName("social network should exist")
    void existTest() {
        // given
        final var socialNetworkId = "MESSENGER";

        when(socialNetworkRepository.get(socialNetworkId)).thenReturn(Optional.of(SocialNetwork.builder().id(socialNetworkId).build()));

        // when
        boolean exists = socialNetworkService.exists(socialNetworkId);

        // then
        assertThat(exists).isTrue();

        verify(socialNetworkRepository, times(1)).get(socialNetworkId);
    }
}
