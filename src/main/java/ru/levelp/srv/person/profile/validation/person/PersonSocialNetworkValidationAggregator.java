package ru.levelp.srv.person.profile.validation.person;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.levelp.srv.person.profile.api.data.CreatePersonSocialNetworkData;
import ru.levelp.srv.person.profile.validation.ValidationUtils;
import ru.levelp.srv.person.profile.validation.data.DataWithPersonId;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonSocialNetworkValidationAggregator {

    private final ValidationUtils validationUtils;

    private final List<PersonSocialNetworkValidator> validatorList;

    public void validate(CreatePersonSocialNetworkData personSocialNetwork, String personId) {
        validationUtils.validate(new DataWithPersonId<>(personSocialNetwork, personId), validatorList);
    }
}
