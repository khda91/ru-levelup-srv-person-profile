package ru.levelp.srv.person.profile.validation.person;

import ru.levelp.srv.person.profile.api.data.CreatePersonSocialNetworkData;
import ru.levelp.srv.person.profile.validation.Validator;
import ru.levelp.srv.person.profile.validation.data.DataWithPersonId;

public interface PersonSocialNetworkValidator extends Validator<DataWithPersonId<CreatePersonSocialNetworkData>> {
}
