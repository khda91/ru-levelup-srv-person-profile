package ru.levelp.srv.person.profile.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.levelp.srv.person.profile.api.data.CreatePersonData;
import ru.levelp.srv.person.profile.api.data.PersonData;
import ru.levelp.srv.person.profile.api.data.PersonListResponse;
import ru.levelp.srv.person.profile.api.data.PersonRole;
import ru.levelp.srv.person.profile.api.mapper.PersonMapper;
import ru.levelp.srv.person.profile.exception.EntityNotFoundProblemException;
import ru.levelp.srv.person.profile.model.Person;
import ru.levelp.srv.person.profile.repository.PersonRepository;
import ru.levelp.srv.person.profile.validation.person.CreatePersonValidationAggregator;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PeopleService {

    private final PersonRepository personRepository;

    private final PersonMapper personMapper;

    private final CreatePersonValidationAggregator createPersonValidationAggregator;

    @Transactional(readOnly = true)
    public PersonListResponse getPeople(String email, List<PersonRole> role, Integer limit, Integer offset) {
        List<String> roles = CollectionUtils.emptyIfNull(role).stream()
                .map(Enum::name)
                .collect(Collectors.toList());

        List<Person> people = personRepository.getAll(email, roles, limit, offset);
        List<PersonData> personData = people.stream()
                .map(personMapper::toPersonData)
                .collect(Collectors.toList());

        var totalCount = personRepository.getTotalPeopleCount();

        return personMapper.toPersonListResponse(limit, offset, totalCount, personData);
    }

    @Transactional
    public PersonData create(CreatePersonData createPersonData) {
        createPersonValidationAggregator.validate(createPersonData);

        var person = personMapper.toPerson(createPersonData);

        var uuid = UUID.randomUUID();
        person.setId(uuid);

        Person createdPerson = personRepository.save(person);

        return personMapper.toPersonData(createdPerson);
    }

    @Transactional(readOnly = true)
    public PersonData getPersonData(String id) {
        var person = get(id);
        return personMapper.toPersonData(person);
    }

    public Person get(String id) {
        var uuid = UUID.fromString(id);
        return personRepository.get(uuid)
                .orElseThrow(() -> new EntityNotFoundProblemException(Person.class, uuid));
    }

    public boolean emailExist(String email) {
        return personRepository.getByEmail(email).isPresent();
    }
}
