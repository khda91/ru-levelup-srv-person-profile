package ru.levelp.srv.person.profile.validation.social.network;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.levelp.srv.person.profile.api.data.SocialNetworkData;
import ru.levelp.srv.person.profile.api.data.violation.DuplicatedItemViolation;
import ru.levelp.srv.person.profile.api.data.violation.Violation;
import ru.levelp.srv.person.profile.service.SocialNetworkService;

import java.util.Collections;
import java.util.Optional;


@Component
public class SocialNetworkIdValidator implements SocialNetworkValidator {

    private static final String DUPLICATED_SOCIAL_NETWORK_DESCRIPTION = "Social network with id %s already exist";

    @Lazy
    @Autowired
    private SocialNetworkService socialNetworkService;

    @Override
    public Optional<Violation> validate(String socialNetworkId) {
        if (socialNetworkService.exists(socialNetworkId)) {
            return Optional.of(new DuplicatedItemViolation(SocialNetworkData.class, Collections.singletonList("socialNetworkId"),
                    String.format(DUPLICATED_SOCIAL_NETWORK_DESCRIPTION, socialNetworkId)));
        }

        return Optional.empty();
    }
}
