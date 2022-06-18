package ru.levelp.srv.person.profile.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.levelp.srv.person.profile.api.data.MessengerListResponse;
import ru.levelp.srv.person.profile.api.mapper.MessengerMapper;
import ru.levelp.srv.person.profile.repository.MessengerRepository;
import ru.levelp.srv.person.profile.validation.messenger.MessengerValidationAggregator;

import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class MessengerService {

    private final MessengerRepository messengerRepository;

    private final MessengerMapper messengerMapper;

    private final MessengerValidationAggregator messengerValidationAggregator;

    @Transactional(readOnly = true)
    public MessengerListResponse getMessengers(Integer limit, Integer offset) {
        var messengers = messengerRepository.getAll(limit, offset);
        var count = messengerRepository.getTotalCount();

        return messengerMapper.toMessengerListResponse(limit, offset, count,
                messengers.stream()
                        .map(messengerMapper::toMessengerData)
                        .collect(Collectors.toList()));
    }

    @Transactional
    public void removeMessenger(String messengerId) {
        messengerRepository.delete(messengerId);
    }

    @Transactional
    public void createMessenger(String messengerId) {
        messengerValidationAggregator.validate(messengerId);

        var messenger = messengerMapper.toMessenger(messengerId);
        messengerRepository.create(messenger);
    }

    public boolean exists(String messengerId) {
        return messengerRepository.get(messengerId).isPresent();
    }
}
