package ru.levelp.srv.person.profile.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import ru.levelp.srv.person.profile.api.data.PersonListResponse;
import ru.levelp.srv.person.profile.api.data.PersonRole;
import ru.levelp.srv.person.profile.service.PersonService;

import java.util.List;

@Validated
@RestController
//@RequestMapping("${openapi.levelUpPlatformAPIPersonProfile.base-path:}")
@RequiredArgsConstructor
public class PeopleApiController implements PeopleApi {

    private final PersonService personService;

    @Override
    public ResponseEntity<PersonListResponse> getPeople(Integer limit, Integer offset, String email, List<PersonRole> role) {
        return ResponseEntity.ok(personService.getPeople(email, role, limit, offset));
    }
}
