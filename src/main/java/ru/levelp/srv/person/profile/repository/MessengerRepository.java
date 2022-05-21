package ru.levelp.srv.person.profile.repository;

import ru.levelp.srv.person.profile.model.Messenger;

import java.util.List;
import java.util.Optional;

public interface MessengerRepository {

    Optional<Messenger> get(String messenger);

    Messenger create(Messenger messenger);

    void delete(String messenger);

    List<Messenger> getAll(Integer limit, Integer offset);

    Integer getTotalCount();
}
