package ru.levelp.srv.person.profile.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;
import ru.levelp.srv.person.profile.api.data.PersonListResponse;
import ru.levelp.srv.person.profile.api.data.PersonRole;

import java.util.List;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-05-19T18:45:57.374415+03:00[Europe/Moscow]")
@Controller
@RequestMapping("${openapi.levelUpPlatformAPIPersonProfile.base-path:}")
public class PeopleApiController implements PeopleApi {

    private final NativeWebRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public PeopleApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public ResponseEntity<PersonListResponse> getPeople(Integer limit, Integer offset, String email, List<PersonRole> role) {
        return null;
    }
}
