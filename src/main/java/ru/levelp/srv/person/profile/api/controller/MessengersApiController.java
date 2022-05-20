package ru.levelp.srv.person.profile.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.levelp.srv.person.profile.api.data.MessengerListResponse;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-05-20T20:35:45.386531+03:00[Europe/Moscow]")
@RestController
@RequestMapping("${openapi.levelUpPlatformAPIPersonProfile.base-path:}")
public class MessengersApiController implements MessengersApi {

    @Override
    public ResponseEntity<Void> createMessenger(String messengerId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteMessenger(String messengerId) {
        return null;
    }

    @Override
    public ResponseEntity<MessengerListResponse> getMessengers(Integer limit, Integer offset) {
        return null;
    }
}
