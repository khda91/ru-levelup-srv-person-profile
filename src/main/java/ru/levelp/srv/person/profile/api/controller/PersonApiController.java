package ru.levelp.srv.person.profile.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;
import ru.levelp.srv.person.profile.api.data.CreatePersonData;
import ru.levelp.srv.person.profile.api.data.PersonResponse;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-05-19T18:45:57.374415+03:00[Europe/Moscow]")
@Controller
@RequestMapping("${openapi.levelUpPlatformAPIPersonProfile.base-path:}")
public class PersonApiController implements PersonApi {

    private final NativeWebRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public PersonApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public ResponseEntity<PersonResponse> createPerson(CreatePersonData createPersonData) {
        return null;
    }

    @Override
    public ResponseEntity<PersonResponse> getPerson(String personId) {
        return null;
    }
}
