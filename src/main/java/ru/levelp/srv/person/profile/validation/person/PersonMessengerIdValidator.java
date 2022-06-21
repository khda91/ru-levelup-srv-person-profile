package ru.levelp.srv.person.profile.validation.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.levelp.srv.person.profile.api.data.CreatePersonMessengerData;
import ru.levelp.srv.person.profile.api.data.violation.DuplicatedItemViolation;
import ru.levelp.srv.person.profile.api.data.violation.FieldCannotBeUpdatedViolation;
import ru.levelp.srv.person.profile.api.data.violation.Violation;
import ru.levelp.srv.person.profile.service.MessengerService;
import ru.levelp.srv.person.profile.service.PeopleMessengerService;
import ru.levelp.srv.person.profile.utils.ObjectFieldsUtils;
import ru.levelp.srv.person.profile.validation.data.DataWithPersonId;

import java.util.Optional;

@Component
public class PersonMessengerIdValidator implements PersonMessengerValidator {

    private static final String DUPLICATED_PERSON_MESSENGER_DESCRIPTION = "Person already has a messenger";
    private static final String NOT_EXISTING_MESSENGER_ID_DESCRIPTION = "No such messengerId found in database";

    @Lazy
    @Autowired
    private MessengerService messengerService;

    @Lazy
    @Autowired
    private PeopleMessengerService peopleMessengerService;

    @Override
    public Optional<Violation> validate(DataWithPersonId<CreatePersonMessengerData> data) {
        String messengerIdPath = ObjectFieldsUtils.getFullPropertyPath(data.getData(), "messengerId", false);
        String messengerId = data.getData().getMessengerId();

        if (!messengerService.exists(messengerId)) {
            return Optional.of(new FieldCannotBeUpdatedViolation(CreatePersonMessengerData.class, convertPath(messengerIdPath),
                    NOT_EXISTING_MESSENGER_ID_DESCRIPTION));
        }

        if (peopleMessengerService.exist(data.getPersonId(), messengerId)) {
            return Optional.of(new DuplicatedItemViolation(CreatePersonMessengerData.class, convertPath(messengerIdPath),
                    DUPLICATED_PERSON_MESSENGER_DESCRIPTION));
        }

        return Optional.empty();
    }
}
