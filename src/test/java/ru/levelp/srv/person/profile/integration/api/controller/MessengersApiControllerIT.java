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
import ru.levelp.srv.person.profile.api.data.MessengerData;
import ru.levelp.srv.person.profile.api.data.MessengerListResponse;
import ru.levelp.srv.person.profile.infrastructure.annotation.ApiTest;

import java.util.List;

@ApiTest
@DisplayName("MessengersApiController integration test")
@DatabaseTearDown(value = "classpath:database/messenger/messenger.xml", type = DatabaseOperation.DELETE_ALL)
class MessengersApiControllerIT extends AbstractController {

    private static final List<MessengerData> EXPECTED_MESSENGERS = List
            .of(new MessengerData().id("VIBER"), new MessengerData().id("TELEGRAM"));

    @Test
    @DisplayName("should return all messengers")
    @DatabaseSetup(value = "classpath:database/messenger/messenger.xml", type = DatabaseOperation.CLEAN_INSERT)
    @SneakyThrows
    void getAllMessengersTest() {
        // given
        var request = prepareGetJsonContentRequest(Endpoint.MESSENGER_PATH);

        // when
        var response = mockMvc.perform(request).andReturn().getResponse();

        var objectResponse = objectMapper.readValue(response.getContentAsString(), MessengerListResponse.class);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(objectResponse.getData()).hasSize(2);
            softly.assertThat(objectResponse.getMeta().getPagination().getTotalCount()).isEqualTo(2);
            softly.assertThat(objectResponse.getMeta().getPagination().getLimit()).isEqualTo(10);
            softly.assertThat(objectResponse.getMeta().getPagination().getOffset()).isEqualTo(0);

            softly.assertThat(objectResponse.getData()).containsExactlyInAnyOrderElementsOf(EXPECTED_MESSENGERS);
        });
    }

    @Test
    @DisplayName("should created messenger")
    @DatabaseSetup(value = "classpath:database/messenger/messenger.xml", type = DatabaseOperation.CLEAN_INSERT)
    @SneakyThrows
    void createMessengerTest() {
        // given
        var request = preparePutJsonContentRequest(Endpoint.MESSENGER_PATH + "/WHATS_UP", null);

        // when-then
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("should delete messenger")
    @DatabaseSetup(value = "classpath:database/messenger/messenger.xml", type = DatabaseOperation.CLEAN_INSERT)
    @SneakyThrows
    void deleteMessengerTest() {
        // given
        var request = prepareDeleteJsonContentRequest(Endpoint.MESSENGER_PATH + "/VIBER");

        // when-then
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
