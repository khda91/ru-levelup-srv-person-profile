package ru.levelp.srv.person.profile.validation.messenger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.levelp.srv.person.profile.api.data.MessengerData;
import ru.levelp.srv.person.profile.api.data.violation.DuplicatedItemViolation;
import ru.levelp.srv.person.profile.api.data.violation.Violation;
import ru.levelp.srv.person.profile.service.MessengerService;

import java.util.Collections;
import java.util.Optional;


@Component
public class MessengerIdValidator implements MessengerValidator {

    private static final String DUPLICATED_MESSENGER_DESCRIPTION = "Messenger with id %s already exist";

    @Lazy
    @Autowired
    private MessengerService messengerService;

    @Override
    public Optional<Violation> validate(String messengerId) {
        if (messengerService.exists(messengerId)) {
            return Optional.of(new DuplicatedItemViolation(MessengerData.class, Collections.singletonList("messengerId"),
                    String.format(DUPLICATED_MESSENGER_DESCRIPTION, messengerId)));
        }

        return Optional.empty();
    }
}
