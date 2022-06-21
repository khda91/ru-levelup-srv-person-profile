package ru.levelp.srv.person.profile.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.levelp.srv.person.profile.api.data.AddressData;
import ru.levelp.srv.person.profile.api.data.PersonData;
import ru.levelp.srv.person.profile.infrastructure.annotation.UnitTest;

import static org.assertj.core.api.Assertions.assertThat;

@UnitTest
@DisplayName("ObjectFieldUtils Test")
class ObjectFieldsUtilsTest {

    @Test
    @DisplayName("Get Full Property Path")
    void shouldGetFullPropertyPath() {
        //given
        var personData = new PersonData().address(new AddressData().city("city"));

        //when
        String propertyPath = ObjectFieldsUtils.getFullPropertyPath(personData, "city", true);

        //then
        assertThat(propertyPath).isEqualTo("PersonData.address.city");
    }

    @Test
    @DisplayName("Get Full Property Path When Two Same Field Name Present")
    void shouldGetFullPropertyPathWhenTwoSameFieldNamePresent() {
        //given
        var addressData = new AddressData().city("city");
        var personData = new PersonData().address(addressData);

        //when
        String propertyPath = ObjectFieldsUtils.getFullPropertyPath(personData, addressData, "city", true);

        //then
        assertThat(propertyPath).isEqualTo("PersonData.address.city");
    }
}
