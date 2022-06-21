package ru.levelp.srv.person.profile.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.levelp.srv.person.profile.api.data.CreatePersonData;
import ru.levelp.srv.person.profile.api.data.CreatePersonMessengerData;
import ru.levelp.srv.person.profile.api.data.CreatePersonSocialNetworkData;
import ru.levelp.srv.person.profile.api.data.PersonListResponse;
import ru.levelp.srv.person.profile.api.data.PersonMessengerListResponse;
import ru.levelp.srv.person.profile.api.data.PersonResponse;
import ru.levelp.srv.person.profile.api.data.PersonRole;
import ru.levelp.srv.person.profile.api.data.PersonSocialNetworkListResponse;
import ru.levelp.srv.person.profile.service.PeopleMessengerService;
import ru.levelp.srv.person.profile.service.PeopleService;
import ru.levelp.srv.person.profile.service.PeopleSocialNetworkService;

import java.util.List;

@RestController
@RequestMapping("${openapi.levelUpPlatformAPIPersonProfile.base-path:}")
@RequiredArgsConstructor
public class PeopleApiController implements PeopleApi {

    private final PeopleService peopleService;

    private final PeopleMessengerService peopleMessengerService;

    private final PeopleSocialNetworkService peopleSocialNetworkService;

    @Override
    public ResponseEntity<Void> addMessengerToPerson(String personId, CreatePersonMessengerData createPersonMessengerData) {
        peopleMessengerService.addMessenger(personId, createPersonMessengerData);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> addSocialNetworkToPerson(String personId, CreatePersonSocialNetworkData createPersonSocialNetworkData) {
        peopleSocialNetworkService.addSocialNetwork(personId, createPersonSocialNetworkData);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PersonResponse> createPerson(CreatePersonData createPersonData) {
        return new ResponseEntity<>((new PersonResponse().data(peopleService.create(createPersonData))), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PersonListResponse> getPeople(Integer limit, Integer offset, String email, List<PersonRole> role) {
        return ResponseEntity.ok(peopleService.getPeople(email, role, limit, offset));
    }

    @Override
    public ResponseEntity<PersonResponse> getPerson(String personId) {
        return ResponseEntity.ok(new PersonResponse().data(peopleService.getPersonData(personId)));
    }

    @Override
    public ResponseEntity<PersonMessengerListResponse> getPersonMessengers(String personId, Integer limit, Integer offset, List<String> messenger) {
        return ResponseEntity.ok(peopleMessengerService.getAll(personId, limit, offset, messenger));
    }

    @Override
    public ResponseEntity<PersonSocialNetworkListResponse> getPersonSocialNetworks(String personId, Integer limit, Integer offset, List<String> socialNetwork) {
        return ResponseEntity.ok(peopleSocialNetworkService.getAll(personId, limit, offset, socialNetwork));
    }

    @Override
    public ResponseEntity<Void> removeMessengerFromPerson(String personId, String messengerId) {
        peopleMessengerService.removeMessenger(personId, messengerId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> removeSocialNetworkFromPerson(String personId, String socialNetworkId) {
        peopleSocialNetworkService.removeSocialNetwork(personId, socialNetworkId);
        return ResponseEntity.noContent().build();
    }
}
