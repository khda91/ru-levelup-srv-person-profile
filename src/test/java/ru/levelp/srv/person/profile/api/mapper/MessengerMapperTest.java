package ru.levelp.srv.person.profile.api.mapper;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.levelp.srv.person.profile.api.data.MessengerData;
import ru.levelp.srv.person.profile.infrastructure.annotation.UnitTest;
import ru.levelp.srv.person.profile.model.Messenger;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@UnitTest
@DisplayName("MessengerMapper Test")
class MessengerMapperTest {

    private static final Messenger VIBER_MESSENGER = Messenger.builder().id("VIBER").build();
    private static final MessengerData WHATS_UP_MESSENGER_DATA = new MessengerData().id("WHATS_UP");

    private MessengerMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(MessengerMapper.class);
    }

    @Test
    @DisplayName("should convert Messenger to MessengerData")
    void toMessengerDataTest() {
        // when
        var messengerData = mapper.toMessengerData(VIBER_MESSENGER);

        // then
        assertThat(messengerData)
                .as("IDs does not matched")
                .extracting(MessengerData::getId)
                .isEqualTo(VIBER_MESSENGER.getId());
    }

    @Test
    @DisplayName("should convert ID to Messenger")
    void toMessengerTest() {
        // given
        final var messengerId = "TELEGRAM";

        // when
        var messenger = mapper.toMessenger(messengerId);

        // then
        assertThat(messenger)
                .as("IDs does not matched")
                .extracting(Messenger::getId)
                .isEqualTo(messengerId);
    }

    @Test
    @DisplayName("should convert list of MessengerData, limit, offset, totalCount to MessengerListResponse")
    void toMessengerListResponseTest() {
        // given
        final var messengersData = Collections.singletonList(WHATS_UP_MESSENGER_DATA);
        final var limit = 10;
        final var offset = 10;
        final var totalCount = 1;

        // when
        var messengerListResponse = mapper.toMessengerListResponse(limit, offset, totalCount, messengersData);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(messengerListResponse.getData()).hasSameSizeAs(messengersData);
            softly.assertThat(messengerListResponse.getMeta().getPagination().getLimit()).isEqualTo(limit);
            softly.assertThat(messengerListResponse.getMeta().getPagination().getOffset()).isEqualTo(offset);
            softly.assertThat(messengerListResponse.getMeta().getPagination().getTotalCount()).isEqualTo(totalCount);

            var messenger = messengersData.get(0);
            var messengerData = messengerListResponse.getData().get(0);

            softly.assertThat(messengerData.getId()).isEqualTo(messenger.getId());
        });
    }
}
