package ru.levelp.srv.person.profile.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.levelp.srv.person.profile.api.data.SocialNetworkListResponse;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-05-20T20:35:45.386531+03:00[Europe/Moscow]")
@RestController
@RequestMapping("${openapi.levelUpPlatformAPIPersonProfile.base-path:}")
public class SocialNetworksApiController implements SocialNetworksApi {


    @Override
    public ResponseEntity<Void> createSocialNetwork(String socialNetworkId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteSocialNetwork(String socialNetworkId) {
        return null;
    }

    @Override
    public ResponseEntity<SocialNetworkListResponse> getSocialNetworks(Integer limit, Integer offset) {
        return null;
    }
}
