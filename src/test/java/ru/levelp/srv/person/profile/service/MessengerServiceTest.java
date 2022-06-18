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
import ru.levelp.srv.person.profile.api.mapper.MessengerMapper;
import ru.levelp.srv.person.profile.infrastructure.annotation.UnitTest;
import ru.levelp.srv.person.profile.model.Messenger;
import ru.levelp.srv.person.profile.repository.MessengerRepository;
import ru.levelp.srv.person.profile.validation.messenger.MessengerValidationAggregator;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@UnitTest
@DisplayName("MessengerService Test")
class MessengerServiceTest {

    @Spy
    private final MessengerMapper messengerMapper = Mappers.getMapper(MessengerMapper.class);

    @Mock
    private MessengerValidationAggregator messengerValidationAggregator;

    @Mock
    private MessengerRepository messengerRepository;

    @Captor
    private ArgumentCaptor<Messenger> messengerArgumentCaptor;

    @Spy
    @InjectMocks
    private MessengerService messengerService;

    @Test
    @DisplayName("should return messengers id when limit, offset are received")
    void getAllTest() {
        // given messenger's data
        final var limit = 10;
        final var offset = 0;
        final var totalCount = 1;

        final var messengers = Collections.singletonList(Messenger.builder().id("VIBER").build());

        when(messengerRepository.getAll(eq(limit), eq(offset))).thenReturn(messengers);
        when(messengerRepository.getTotalCount()).thenReturn(messengers.size());

        // when
        var messengerListResponse = messengerService.getMessengers(limit, offset);

        // then should return messenger data
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(messengerListResponse.getData()).hasSameSizeAs(messengers);
            softly.assertThat(messengerListResponse.getMeta().getPagination().getLimit()).isEqualTo(limit);
            softly.assertThat(messengerListResponse.getMeta().getPagination().getOffset()).isEqualTo(offset);
            softly.assertThat(messengerListResponse.getMeta().getPagination().getTotalCount()).isEqualTo(totalCount);

            var messenger = messengers.get(0);
            var messengerData = messengerListResponse.getData().get(0);

            softly.assertThat(messengerData.getId()).isEqualTo(messenger.getId());
        });
        // and fetch data from database
        verify(messengerRepository, times(1)).getAll(limit, offset);
        verify(messengerRepository, times(1)).getTotalCount();
    }

    @Test
    @DisplayName("should created messenger")
    void createdMessengerTest() {
        // given
        final var messengerId = "VIBER";
        final var messenger = Messenger.builder().id(messengerId).build();

        when(messengerRepository.create(messenger)).thenReturn(messenger);

        // when
        messengerService.createMessenger(messengerId);

        // then
        verify(messengerRepository, times(1)).create(messengerArgumentCaptor.capture());

        var actualMessenger = messengerArgumentCaptor.getValue();
        assertThat(actualMessenger)
                .as("IDs does not matched")
                .extracting(Messenger::getId)
                .isEqualTo(messengerId);
    }

    @Test
    @DisplayName("should delete messenger")
    void deleteMessengerTest() {
        // given
        final var messengerId = "TELEGRAM";

        // when
        messengerService.removeMessenger(messengerId);

        // then
        verify(messengerRepository, times(1)).delete(messengerId);
    }
}
