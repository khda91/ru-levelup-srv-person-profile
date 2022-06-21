package ru.levelp.srv.person.profile.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.levelp.srv.person.profile.api.data.CreatePersonMessengerData;
import ru.levelp.srv.person.profile.api.data.PersonMessengerListResponse;
import ru.levelp.srv.person.profile.api.mapper.PersonMessengerMapper;
import ru.levelp.srv.person.profile.exception.EntityNotFoundProblemException;
import ru.levelp.srv.person.profile.model.PersonMessenger;
import ru.levelp.srv.person.profile.repository.PersonMessengerRepository;
import ru.levelp.srv.person.profile.validation.person.PersonMessengerValidationAggregator;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class PeopleMessengerService {

    private final PeopleService peopleService;

    private final PersonMessengerRepository personMessengerRepository;

    private final PersonMessengerMapper personMessengerMapper;

    private final PersonMessengerValidationAggregator messengerValidationAggregator;

    @Transactional
    public void addMessenger(String personId, CreatePersonMessengerData body) {
        messengerValidationAggregator.validate(body, personId);

        peopleService.get(personId);
        UUID uuid = UUID.randomUUID();

        var personMessenger = personMessengerMapper.toPersonMessenger(personId, body);
        personMessenger.setId(uuid);

        personMessengerRepository.create(personMessenger);
    }

    @Transactional(readOnly = true)
    public PersonMessengerListResponse getAll(String personId, Integer limit, Integer offset, List<String> messenger) {
        Optional<List<String>> messengersCondition = CollectionUtils.isEmpty(messenger) ? Optional.empty() : Optional.of(messenger);

        var messengers = personMessengerRepository.getAll(personId, limit, offset, messengersCondition);
        var count = personMessengerRepository.count(personId, messengersCondition);
        return personMessengerMapper.toPersonMessengerListResponse(limit, offset, count, messengers);
    }

    @Transactional
    public void removeMessenger(String personId, String messengerId) {
        var personMessenger = personMessengerRepository.getPersonMessenger(personId, messengerId)
                .orElseThrow(() -> new EntityNotFoundProblemException(PersonMessenger.class, Map.of("personId", personId, "messengerId", messengerId)));
        var personMessengerId = personMessenger.getId();
        personMessengerRepository.delete(personMessengerId);
    }

    @Transactional(readOnly = true)
    public boolean exist(String personId, String messengerId) {
        return personMessengerRepository.getPersonMessenger(personId, messengerId).isPresent();
    }
}
