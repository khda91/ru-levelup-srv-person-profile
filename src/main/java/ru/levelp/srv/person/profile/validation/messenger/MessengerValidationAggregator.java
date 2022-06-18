package ru.levelp.srv.person.profile.validation.messenger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.levelp.srv.person.profile.validation.ValidationUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessengerValidationAggregator {

    private final ValidationUtils validationUtils;

    private final List<MessengerValidator> validatorList;

    public void validate(String id) {
        validationUtils.validate(id, validatorList);
    }
}
