package ru.levelp.srv.person.profile.api.controller;

import lombok.RequiredArgsConstructor;
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
import ru.levelp.srv.person.profile.service.PeopleService;

import java.util.List;

@RestController
@RequestMapping("${openapi.levelUpPlatformAPIPersonProfile.base-path:}")
@RequiredArgsConstructor
public class PeopleApiController implements PeopleApi {

    private final PeopleService peopleService;

    @Override
    public ResponseEntity<Void> addMessengerToPerson(String personId, CreatePersonMessengerData createPersonMessengerData) {
        return null;
    }

    @Override
    public ResponseEntity<Void> addSocialNetworkToPerson(String personId, CreatePersonSocialNetworkData createPersonSocialNetworkData) {
        return null;
    }

    @Override
    public ResponseEntity<PersonResponse> createPerson(CreatePersonData createPersonData) {
        return null;
    }

    @Override
    public ResponseEntity<PersonListResponse> getPeople(Integer limit, Integer offset, String email, List<PersonRole> role) {
        return ResponseEntity.ok(peopleService.getPeople(email, role, limit, offset));
    }

    @Override
    public ResponseEntity<PersonResponse> getPerson(String personId) {
        return null;
    }

    @Override
    public ResponseEntity<PersonMessengerListResponse> getPersonMessengers(String personId, Integer limit, Integer offset, List<String> messenger) {
        return null;
    }

    @Override
    public ResponseEntity<PersonSocialNetworkListResponse> getPersonSocialNetworks(String personId, Integer limit, Integer offset, List<String> socialNetwork) {
        return null;
    }

    @Override
    public ResponseEntity<Void> removeMessengerFromPerson(String personId, String messengerId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> removeSocialNetworkFromPerson(String personId, String socialNetworkId) {
        return null;
    }
}
