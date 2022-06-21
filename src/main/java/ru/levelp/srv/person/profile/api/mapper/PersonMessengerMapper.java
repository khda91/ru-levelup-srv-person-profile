package ru.levelp.srv.person.profile.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.levelp.srv.person.profile.api.data.CreatePersonMessengerData;
import ru.levelp.srv.person.profile.api.data.PersonMessengerData;
import ru.levelp.srv.person.profile.api.data.PersonMessengerListResponse;
import ru.levelp.srv.person.profile.model.PersonMessenger;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMessengerMapper extends UuidToStringMappingStrategy {

    PersonMessengerData toPersonMessengerData(PersonMessenger personMessenger);

    PersonMessenger toPersonMessenger(CreatePersonMessengerData createPersonMessengerData);

    @Mappings({
            @Mapping(source = "createPersonMessengerData.messengerId", target = "messengerId"),
            @Mapping(source = "createPersonMessengerData.nickname", target = "nickname")
    })
    PersonMessenger toPersonMessenger(String personId, CreatePersonMessengerData createPersonMessengerData);

    @Mappings({
            @Mapping(source = "personMessengers", target = "data"),
            @Mapping(source = "limit", target = "meta.pagination.limit"),
            @Mapping(source = "offset", target = "meta.pagination.offset"),
            @Mapping(source = "count", target = "meta.pagination.totalCount"),
    })
    PersonMessengerListResponse toPersonMessengerListResponse(Integer limit, Integer offset, Integer count, List<PersonMessenger> personMessengers);
}
