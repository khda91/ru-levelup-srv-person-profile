package ru.levelp.srv.person.profile.validation.social.network;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.levelp.srv.person.profile.validation.ValidationUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SocialNetworkValidationAggregator {

    private final ValidationUtils validationUtils;

    private final List<SocialNetworkValidator> validatorList;

    public void validate(String id) {
        validationUtils.validate(id, validatorList);
    }
}
