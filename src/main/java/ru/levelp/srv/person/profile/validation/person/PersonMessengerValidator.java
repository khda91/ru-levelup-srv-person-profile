package ru.levelp.srv.person.profile.validation.person;

import ru.levelp.srv.person.profile.api.data.CreatePersonMessengerData;
import ru.levelp.srv.person.profile.validation.Validator;
import ru.levelp.srv.person.profile.validation.data.DataWithPersonId;

public interface PersonMessengerValidator extends Validator<DataWithPersonId<CreatePersonMessengerData>> {
}
