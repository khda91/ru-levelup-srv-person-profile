package ru.levelp.srv.person.profile.repository;

import ru.levelp.srv.person.profile.model.PersonMessenger;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonMessengerRepository {

    void create(PersonMessenger personMessenger);

    List<PersonMessenger> getAll(String personId, Integer limit, Integer offset, Optional<List<String>> messengers);

    Integer count(String personId, Optional<List<String>> messengers);

    Optional<PersonMessenger> getPersonMessenger(String personId, String messengerId);

    void delete(UUID id);
}
