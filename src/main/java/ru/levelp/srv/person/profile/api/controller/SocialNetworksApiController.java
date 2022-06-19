package ru.levelp.srv.person.profile.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.levelp.srv.person.profile.api.data.SocialNetworkListResponse;
import ru.levelp.srv.person.profile.service.SocialNetworkService;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-05-20T20:35:45.386531+03:00[Europe/Moscow]")
@RestController
@RequestMapping("${openapi.levelUpPlatformAPIPersonProfile.base-path:}")
@RequiredArgsConstructor
public class SocialNetworksApiController implements SocialNetworksApi {

    private final SocialNetworkService socialNetworkService;

    @Override
    public ResponseEntity<Void> createSocialNetwork(String socialNetworkId) {
        socialNetworkService.createSocialNetwork(socialNetworkId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteSocialNetwork(String socialNetworkId) {
        socialNetworkService.removeSocialNetwork(socialNetworkId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<SocialNetworkListResponse> getSocialNetworks(Integer limit, Integer offset) {
        return ResponseEntity.ok(socialNetworkService.getSocialNetworks(limit, offset));
    }
}
