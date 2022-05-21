package ru.levelp.srv.person.profile.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.levelp.srv.person.profile.api.data.MessengerListResponse;
import ru.levelp.srv.person.profile.api.mapper.MessengerMapper;
import ru.levelp.srv.person.profile.repository.MessengerRepository;

import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class MessengerService {

    private final MessengerRepository messengerRepository;

    private final MessengerMapper messengerMapper;

    @Transactional(readOnly = true)
    public MessengerListResponse getMessengers(Integer limit, Integer offset) {
        var messengers = messengerRepository.getAll(limit, offset);
        var count = messengerRepository.getTotalCount();

        return messengerMapper.toMessengerListResponse(limit, offset, count,
                messengers.stream()
                        .map(messengerMapper::toMessengerData)
                        .collect(Collectors.toList()));
    }

    public void removeMessenger(String messengerId) {
        messengerRepository.delete(messengerId);
    }

    public void createMessenger(String messengerId) {
        var messenger = messengerMapper.toMessenger(messengerId);
        messengerRepository.create(messenger);
    }
}
