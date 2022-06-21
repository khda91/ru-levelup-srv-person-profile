package ru.levelp.srv.person.profile.validation.person;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.levelp.srv.person.profile.api.data.CreatePersonMessengerData;
import ru.levelp.srv.person.profile.validation.ValidationUtils;
import ru.levelp.srv.person.profile.validation.data.DataWithPersonId;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonMessengerValidationAggregator {

    private final ValidationUtils validationUtils;

    private final List<PersonMessengerValidator> validatorList;

    public void validate(CreatePersonMessengerData personMessenger, String personId) {
        validationUtils.validate(new DataWithPersonId<>(personMessenger, personId), validatorList);
    }
}
