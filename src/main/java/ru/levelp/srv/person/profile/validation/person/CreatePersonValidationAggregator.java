package ru.levelp.srv.person.profile.validation.person;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.levelp.srv.person.profile.api.data.CreatePersonData;
import ru.levelp.srv.person.profile.validation.ValidationUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreatePersonValidationAggregator {

    private final ValidationUtils validationUtils;

    private final List<CreatePersonValidator> validatorList;

    public void validate(CreatePersonData createPersonData) {
        validationUtils.validate(createPersonData, validatorList);
    }
}
