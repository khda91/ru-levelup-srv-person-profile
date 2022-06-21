package ru.levelp.srv.person.profile.api.mapper;

import com.github.javafaker.Faker;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.levelp.srv.person.profile.api.data.AddressData;
import ru.levelp.srv.person.profile.api.data.CreatePersonData;
import ru.levelp.srv.person.profile.api.data.IdentityData;
import ru.levelp.srv.person.profile.api.data.PassportData;
import ru.levelp.srv.person.profile.api.data.PersonData;
import ru.levelp.srv.person.profile.api.data.PersonRole;
import ru.levelp.srv.person.profile.infrastructure.annotation.UnitTest;
import ru.levelp.srv.person.profile.model.Person;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@UnitTest
@DisplayName("PersonMapper test")
class PersonMapperTest {

    private PersonMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(PersonMapper.class);
    }

    @Test
    @DisplayName("covert Person to PersonData")
    void shouldConvertPersonToPersonDataTest() {
        // given
        var person = getPerson();

        // when
        var personData = mapper.toPersonData(person);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(personData.getRole().name()).as("role").isEqualTo(person.getRole().name());
            softly.assertThat(personData.getEmail()).as("email").isEqualTo(person.getEmail());
            softly.assertThat(personData.getPhoneNumber()).as("phoneNumber").isEqualTo(person.getPhoneNumber());
            softly.assertThat(personData.getPlaceOfWork()).as("placeOfWork").isEqualTo(person.getPlaceOfWork());
            softly.assertThat(personData.getIdentity().getFirstName()).as("firstName").isEqualTo(person.getFirstName());
            softly.assertThat(personData.getIdentity().getLastName()).as("lastName").isEqualTo(person.getLastName());
            softly.assertThat(personData.getIdentity().getMiddleName()).as("middleName").isEqualTo(person.getMiddleName());
            softly.assertThat(personData.getIdentity().getGender()).as("gender").isEqualTo(person.getGender());
            softly.assertThat(personData.getIdentity().getDateOfBirth()).as("dateOfBirth").isEqualTo(person.getDateOfBirth());
            softly.assertThat(personData.getIdentity().getPlaceOfBirth()).as("placeOfBirth").isEqualTo(person.getPlaceOfBirth());
            softly.assertThat(personData.getIdentity().getPassport().getSeries()).as("passportSeries").isEqualTo(person.getPassportSeries().toString());
            softly.assertThat(personData.getIdentity().getPassport().getNumber()).as("passportNumber").isEqualTo(person.getPassportNumber().toString());
            softly.assertThat(personData.getIdentity().getPassport().getPlaceOfIssue()).as("placeOfIssue").isEqualTo(person.getPlaceOfIssue());
            softly.assertThat(personData.getIdentity().getPassport().getDateOfIssue()).as("dateOfIssue").isEqualTo(person.getDateOfIssue());
            softly.assertThat(personData.getIdentity().getPassport().getDepartmentCode()).as("departmentCode").isEqualTo(person.getDepartmentCode());
            softly.assertThat(personData.getAddress().getStreet()).as("street").isEqualTo(person.getStreet());
            softly.assertThat(personData.getAddress().getHouseNumber()).as("houseNumber").isEqualTo(person.getHouseNumber());
            softly.assertThat(personData.getAddress().getHouseBuilding()).as("houseBuilding").isEqualTo(person.getHouseBuilding());
            softly.assertThat(personData.getAddress().getHouseLetter()).as("houseLetter").isEqualTo(person.getHouseLetter());
            softly.assertThat(personData.getAddress().getFlat()).as("flat").isEqualTo(person.getFlat());
            softly.assertThat(personData.getAddress().getCity()).as("city").isEqualTo(person.getCity());
            softly.assertThat(personData.getAddress().getPostalCode()).as("postalCode").isEqualTo(person.getPostalCode());
        });
    }

    @Test
    @DisplayName("covert CreatePersonData to Person")
    void shouldConvertCreatePersonDataToPersonTest() {
        // given
        var personData = getCreatePersonData();

        // when
        var person = mapper.toPerson(personData);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(person.getId()).as("id").isNull();
            softly.assertThat(person.getRole().name()).as("role").isEqualTo(personData.getRole().name());
            softly.assertThat(person.getEmail()).as("email").isEqualTo(personData.getEmail());
            softly.assertThat(person.getPhoneNumber()).as("phoneNumber").isEqualTo(personData.getPhoneNumber());
            softly.assertThat(person.getPlaceOfWork()).as("placeOfWork").isEqualTo(personData.getPlaceOfWork());
            softly.assertThat(person.getFirstName()).as("firstName").isEqualTo(personData.getIdentity().getFirstName());
            softly.assertThat(person.getLastName()).as("lastName").isEqualTo(personData.getIdentity().getLastName());
            softly.assertThat(person.getMiddleName()).as("middleName").isEqualTo(personData.getIdentity().getMiddleName());
            softly.assertThat(person.getGender()).as("gender").isEqualTo(personData.getIdentity().getGender());
            softly.assertThat(person.getDateOfBirth()).as("dateOfBirth").isEqualTo(personData.getIdentity().getDateOfBirth());
            softly.assertThat(person.getPlaceOfBirth()).as("placeOfBirth").isEqualTo(personData.getIdentity().getPlaceOfBirth());
            softly.assertThat(person.getPassportSeries()).as("passportSeries").hasToString(personData.getIdentity().getPassport().getSeries());
            softly.assertThat(person.getPassportNumber()).as("passportNumber").hasToString(personData.getIdentity().getPassport().getNumber());
            softly.assertThat(person.getPlaceOfIssue()).as("placeOfIssue").isEqualTo(personData.getIdentity().getPassport().getPlaceOfIssue());
            softly.assertThat(person.getDateOfIssue()).as("dateOfIssue").isEqualTo(personData.getIdentity().getPassport().getDateOfIssue());
            softly.assertThat(person.getDepartmentCode()).as("departmentCode").isEqualTo(personData.getIdentity().getPassport().getDepartmentCode());
            softly.assertThat(person.getStreet()).as("street").isEqualTo(personData.getAddress().getStreet());
            softly.assertThat(person.getHouseNumber()).as("houseNumber").isEqualTo(personData.getAddress().getHouseNumber());
            softly.assertThat(person.getHouseBuilding()).as("houseBuilding").isEqualTo(personData.getAddress().getHouseBuilding());
            softly.assertThat(person.getHouseLetter()).as("houseLetter").isEqualTo(personData.getAddress().getHouseLetter());
            softly.assertThat(person.getFlat()).as("flat").isEqualTo(personData.getAddress().getFlat());
            softly.assertThat(person.getCity()).as("city").isEqualTo(personData.getAddress().getCity());
            softly.assertThat(person.getPostalCode()).as("postalCode").isEqualTo(personData.getAddress().getPostalCode());
        });
    }

    @Test
    @DisplayName("should convert list of PersonData, limit, offset, totalCount to PersonListResponse")
    void toPersonListResponseTest() {
        // given
        final var limit = 10;
        final var offset = 0;
        final var totalCount = 1;
        final var personData = getPersonData();
        final var people = Collections.singletonList((personData));

        // when
        var personListResponse = mapper.toPersonListResponse(limit, offset, totalCount, people);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(personListResponse.getData()).hasSameSizeAs(people);
            softly.assertThat(personListResponse.getMeta().getPagination().getLimit()).isEqualTo(limit);
            softly.assertThat(personListResponse.getMeta().getPagination().getOffset()).isEqualTo(offset);
            softly.assertThat(personListResponse.getMeta().getPagination().getTotalCount()).isEqualTo(totalCount);

            var actualPerson = personListResponse.getData().get(0);

            softly.assertThat(actualPerson.getId()).as("id").isNull();
            softly.assertThat(actualPerson.getRole().name()).as("role").isEqualTo(personData.getRole().name());
            softly.assertThat(actualPerson.getEmail()).as("email").isEqualTo(personData.getEmail());
            softly.assertThat(actualPerson.getPhoneNumber()).as("phoneNumber").isEqualTo(personData.getPhoneNumber());
            softly.assertThat(actualPerson.getPlaceOfWork()).as("placeOfWork").isEqualTo(personData.getPlaceOfWork());
            softly.assertThat(actualPerson.getIdentity().getFirstName()).as("firstName").isEqualTo(personData.getIdentity().getFirstName());
            softly.assertThat(actualPerson.getIdentity().getLastName()).as("lastName").isEqualTo(personData.getIdentity().getLastName());
            softly.assertThat(actualPerson.getIdentity().getMiddleName()).as("middleName").isEqualTo(personData.getIdentity().getMiddleName());
            softly.assertThat(actualPerson.getIdentity().getGender()).as("gender").isEqualTo(personData.getIdentity().getGender());
            softly.assertThat(actualPerson.getIdentity().getDateOfBirth()).as("dateOfBirth").isEqualTo(personData.getIdentity().getDateOfBirth());
            softly.assertThat(actualPerson.getIdentity().getPlaceOfBirth()).as("placeOfBirth").isEqualTo(personData.getIdentity().getPlaceOfBirth());
            softly.assertThat(actualPerson.getIdentity().getPassport().getSeries()).as("passportSeries").isEqualTo(personData.getIdentity().getPassport().getSeries());
            softly.assertThat(actualPerson.getIdentity().getPassport().getNumber()).as("passportNumber").isEqualTo(personData.getIdentity().getPassport().getNumber());
            softly.assertThat(actualPerson.getIdentity().getPassport().getPlaceOfIssue()).as("placeOfIssue").isEqualTo(personData.getIdentity().getPassport().getPlaceOfIssue());
            softly.assertThat(actualPerson.getIdentity().getPassport().getDateOfIssue()).as("dateOfIssue").isEqualTo(personData.getIdentity().getPassport().getDateOfIssue());
            softly.assertThat(actualPerson.getIdentity().getPassport().getDepartmentCode()).as("departmentCode").isEqualTo(personData.getIdentity().getPassport().getDepartmentCode());
            softly.assertThat(actualPerson.getAddress().getStreet()).as("street").isEqualTo(personData.getAddress().getStreet());
            softly.assertThat(actualPerson.getAddress().getHouseNumber()).as("houseNumber").isEqualTo(personData.getAddress().getHouseNumber());
            softly.assertThat(actualPerson.getAddress().getHouseBuilding()).as("houseBuilding").isEqualTo(personData.getAddress().getHouseBuilding());
            softly.assertThat(actualPerson.getAddress().getHouseLetter()).as("houseLetter").isEqualTo(personData.getAddress().getHouseLetter());
            softly.assertThat(actualPerson.getAddress().getFlat()).as("flat").isEqualTo(personData.getAddress().getFlat());
            softly.assertThat(actualPerson.getAddress().getCity()).as("city").isEqualTo(personData.getAddress().getCity());
            softly.assertThat(actualPerson.getAddress().getPostalCode()).as("postalCode").isEqualTo(personData.getAddress().getPostalCode());
        });
    }

    private Person getPerson() {
        final var faker = new Faker();
        return Person.builder()
                .id(UUID.randomUUID())
                .email(faker.internet().emailAddress())
                .phoneNumber(faker.phoneNumber().phoneNumber())
                .role(Person.Role.LECTOR)
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .middleName("some middle name")
                .gender(faker.demographic().sex())
                .dateOfBirth(LocalDate.ofInstant(faker.date().birthday().toInstant(), ZoneId.systemDefault()))
                .placeOfBirth(faker.address().city())
                .placeOfWork(faker.company().name())
                .passportSeries(1234)
                .passportNumber(567890)
                .placeOfIssue(faker.address().fullAddress())
                .dateOfIssue(LocalDate.ofInstant(faker.date().past(3000, TimeUnit.DAYS).toInstant(),
                        ZoneId.systemDefault()))
                .departmentCode("121-000")
                .street(faker.address().streetName())
                .houseNumber(1)
                .houseBuilding(1)
                .houseLetter("A")
                .flat(1)
                .city(faker.address().city())
                .postalCode(faker.address().zipCode())
                .build();
    }

    private CreatePersonData getCreatePersonData() {
        final var faker = new Faker();
        return new CreatePersonData()
                .role(PersonRole.ADMINISTRATOR)
                .email(faker.internet().emailAddress())
                .phoneNumber(faker.phoneNumber().phoneNumber())
                .placeOfWork(faker.company().name())
                .identity(new IdentityData()
                        .firstName(faker.name().firstName())
                        .lastName(faker.name().lastName())
                        .middleName("some middle name")
                        .gender(faker.demographic().sex())
                        .dateOfBirth(LocalDate.ofInstant(faker.date().birthday().toInstant(), ZoneId.systemDefault()))
                        .placeOfBirth(faker.address().city())
                        .passport(new PassportData()
                                .series("1234")
                                .number("567890")
                                .placeOfIssue(faker.address().fullAddress())
                                .dateOfIssue(LocalDate.ofInstant(faker.date().past(3000, TimeUnit.DAYS).toInstant(),
                                        ZoneId.systemDefault()))
                                .departmentCode("121-000")))
                .address(new AddressData()
                        .street(faker.address().streetName())
                        .houseNumber(1)
                        .houseBuilding(1)
                        .houseLetter("A")
                        .flat(1)
                        .city(faker.address().city())
                        .postalCode(faker.address().zipCode()));
    }

    private PersonData getPersonData() {
        final var faker = new Faker();
        return new PersonData()
                .role(PersonRole.ADMINISTRATOR)
                .email(faker.internet().emailAddress())
                .phoneNumber(faker.phoneNumber().phoneNumber())
                .placeOfWork(faker.company().name())
                .identity(new IdentityData()
                        .firstName(faker.name().firstName())
                        .lastName(faker.name().lastName())
                        .middleName("some middle name")
                        .gender(faker.demographic().sex())
                        .dateOfBirth(LocalDate.ofInstant(faker.date().birthday().toInstant(), ZoneId.systemDefault()))
                        .placeOfBirth(faker.address().city())
                        .passport(new PassportData()
                                .series("1234")
                                .number("567890")
                                .placeOfIssue(faker.address().fullAddress())
                                .dateOfIssue(LocalDate.ofInstant(faker.date().past(3000, TimeUnit.DAYS).toInstant(),
                                        ZoneId.systemDefault()))
                                .departmentCode("121-000")))
                .address(new AddressData()
                        .street(faker.address().streetName())
                        .houseNumber(1)
                        .houseBuilding(1)
                        .houseLetter("A")
                        .flat(1)
                        .city(faker.address().city())
                        .postalCode(faker.address().zipCode()));
    }
}
