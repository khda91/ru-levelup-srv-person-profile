package ru.levelp.srv.person.profile.validation.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.levelp.srv.person.profile.api.data.CreatePersonData;
import ru.levelp.srv.person.profile.api.data.violation.FieldNotUniqueViolation;
import ru.levelp.srv.person.profile.api.data.violation.Violation;
import ru.levelp.srv.person.profile.service.PeopleService;
import ru.levelp.srv.person.profile.utils.ObjectFieldsUtils;

import java.util.Optional;

@Component
public class PersonEmailValidator implements CreatePersonValidator, EmailValidator<CreatePersonData> {

    @Lazy
    @Autowired
    private PeopleService peopleService;

    @Override
    public Optional<Violation> validate(CreatePersonData createPersonData) {
        var emailPath = ObjectFieldsUtils.getFullPropertyPath(createPersonData, "email", false);
        var email = createPersonData.getEmail();
        var emailValidationData = defaultEmailValidate(email, CreatePersonData.class, emailPath);

        if (emailValidationData.isPresent()) {
            return emailValidationData;
        }

        if (peopleService.emailExist(email)) {
            return Optional.of(new FieldNotUniqueViolation(CreatePersonData.class, convertPath(emailPath),
                    "Person with provided email already exists"));
        }

        return Optional.empty();
    }
}
