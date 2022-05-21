package ru.levelp.srv.person.profile.repository;

import ru.levelp.srv.person.profile.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonRepository {

    Optional<Person> get(UUID id);

    Person save(Person person);

    List<Person> getAll(Integer limit, Integer offset);

    Integer getTotalPeopleCount();
}
