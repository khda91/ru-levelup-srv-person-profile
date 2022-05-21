package ru.levelp.srv.person.profile.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.levelp.srv.person.profile.api.data.MessengerListResponse;
import ru.levelp.srv.person.profile.service.MessengerService;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-05-20T20:35:45.386531+03:00[Europe/Moscow]")
@RestController
@RequestMapping("${openapi.levelUpPlatformAPIPersonProfile.base-path:}")
@RequiredArgsConstructor
public class MessengersApiController implements MessengersApi {

    private final MessengerService messengerService;

    @Override
    public ResponseEntity<Void> createMessenger(String messengerId) {
        messengerService.createMessenger(messengerId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteMessenger(String messengerId) {
        messengerService.removeMessenger(messengerId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<MessengerListResponse> getMessengers(Integer limit, Integer offset) {
        return ResponseEntity.ok(messengerService.getMessengers(limit, offset));
    }
}
