package ru.levelp.srv.person.profile.integration.api.controller;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import lombok.SneakyThrows;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.levelp.srv.person.profile.api.data.SocialNetworkData;
import ru.levelp.srv.person.profile.api.data.SocialNetworkListResponse;
import ru.levelp.srv.person.profile.infrastructure.annotation.ApiTest;

import java.util.List;

@ApiTest
@DisplayName("SocialNetworksApiController integration test")
@DatabaseTearDown(value = "classpath:database/social_network/social_network.xml", type = DatabaseOperation.DELETE_ALL)
class SocialNetworksApiControllerIT extends AbstractController {

    private static final List<SocialNetworkData> EXPECTED_SOCIAL_NETWORKS = List
            .of(new SocialNetworkData().id("VK"), new SocialNetworkData().id("FACEBOOK"), new SocialNetworkData().id("LINKED_IN"));

    @Test
    @DisplayName("should return all social networks")
    @DatabaseSetup(value = "classpath:database/social_network/social_network.xml", type = DatabaseOperation.CLEAN_INSERT)
    @SneakyThrows
    void getAllSocialNetworksTest() {
        // given
        var request = prepareGetJsonContentRequest(Endpoint.SOCIAL_NETWORK_PATH);

        // when
        var response = mockMvc.perform(request).andReturn().getResponse();

        var objectResponse = objectMapper.readValue(response.getContentAsString(), SocialNetworkListResponse.class);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(objectResponse.getData()).hasSameSizeAs(EXPECTED_SOCIAL_NETWORKS);
            softly.assertThat(objectResponse.getMeta().getPagination().getTotalCount()).isEqualTo(3);
            softly.assertThat(objectResponse.getMeta().getPagination().getLimit()).isEqualTo(10);
            softly.assertThat(objectResponse.getMeta().getPagination().getOffset()).isEqualTo(0);

            softly.assertThat(objectResponse.getData()).containsExactlyInAnyOrderElementsOf(EXPECTED_SOCIAL_NETWORKS);
        });
    }

    @Test
    @DisplayName("should created social network")
    @DatabaseSetup(value = "classpath:database/social_network/social_network.xml", type = DatabaseOperation.CLEAN_INSERT)
    @SneakyThrows
    void createSocialNetworkTest() {
        // given
        var request = preparePutJsonContentRequest(Endpoint.SOCIAL_NETWORK_PATH + "/ODNOCLASSNIKI", null);

        // when-then
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("should delete social network")
    @DatabaseSetup(value = "classpath:database/social_network/social_network.xml", type = DatabaseOperation.CLEAN_INSERT)
    @SneakyThrows
    void deleteSocialNetworkTest() {
        // given
        var request = prepareDeleteJsonContentRequest(Endpoint.SOCIAL_NETWORK_PATH + "/FACEBOOK");

        // when-then
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
