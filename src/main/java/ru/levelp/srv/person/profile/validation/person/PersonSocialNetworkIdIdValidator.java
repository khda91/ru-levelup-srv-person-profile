package ru.levelp.srv.person.profile.validation.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.levelp.srv.person.profile.api.data.CreatePersonSocialNetworkData;
import ru.levelp.srv.person.profile.api.data.violation.DuplicatedItemViolation;
import ru.levelp.srv.person.profile.api.data.violation.FieldCannotBeUpdatedViolation;
import ru.levelp.srv.person.profile.api.data.violation.Violation;
import ru.levelp.srv.person.profile.service.PeopleSocialNetworkService;
import ru.levelp.srv.person.profile.service.SocialNetworkService;
import ru.levelp.srv.person.profile.utils.ObjectFieldsUtils;
import ru.levelp.srv.person.profile.validation.data.DataWithPersonId;

import java.util.Optional;

@Component
public class PersonSocialNetworkIdIdValidator implements PersonSocialNetworkValidator {

    private static final String DUPLICATED_PERSON_SOCIAL_NETWORK_DESCRIPTION = "Person already has a social network";
    private static final String NOT_EXISTING_SOCIAL_NETWORK_ID_DESCRIPTION = "No such socialNetworkId found in database";

    @Lazy
    @Autowired
    private SocialNetworkService socialNetworkService;

    @Lazy
    @Autowired
    private PeopleSocialNetworkService peopleSocialNetworkService;

    @Override
    public Optional<Violation> validate(DataWithPersonId<CreatePersonSocialNetworkData> data) {
        String socialNetworkIdPath = ObjectFieldsUtils.getFullPropertyPath(data.getData(), "socialNetworkId", false);
        String socialNetworkId = data.getData().getSocialNetworkId();

        if (!socialNetworkService.exists(socialNetworkId)) {
            return Optional.of(new FieldCannotBeUpdatedViolation(CreatePersonSocialNetworkData.class, convertPath(socialNetworkIdPath),
                    NOT_EXISTING_SOCIAL_NETWORK_ID_DESCRIPTION));
        }

        if (peopleSocialNetworkService.exist(data.getPersonId(), socialNetworkId)) {
            return Optional.of(new DuplicatedItemViolation(CreatePersonSocialNetworkData.class, convertPath(socialNetworkIdPath),
                    DUPLICATED_PERSON_SOCIAL_NETWORK_DESCRIPTION));
        }

        return Optional.empty();
    }
}
