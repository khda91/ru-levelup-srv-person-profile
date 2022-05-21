package ru.levelp.srv.person.profile.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import ru.levelp.srv.person.profile.api.data.PersonData;
import ru.levelp.srv.person.profile.api.data.PersonListResponse;
import ru.levelp.srv.person.profile.api.data.PersonRole;
import ru.levelp.srv.person.profile.api.mapper.PersonMapper;
import ru.levelp.srv.person.profile.model.Person;
import ru.levelp.srv.person.profile.repository.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PeopleService {

    private final PersonRepository personRepository;

    private final PersonMapper personMapper;

    public PersonListResponse getPeople(String email, List<PersonRole> role, Integer limit, Integer offset) {
        List<String> roles = CollectionUtils.emptyIfNull(role).stream()
                .map(Enum::name)
                .collect(Collectors.toList());

        List<Person> people = personRepository.getAll(limit, offset);
        List<PersonData> personData = people.stream()
                .map(personMapper::toPersonData)
                .collect(Collectors.toList());

        var totalCount = personRepository.getTotalPeopleCount();

        return personMapper.toPersonListResponse(limit, offset, totalCount, personData);
    }
}
