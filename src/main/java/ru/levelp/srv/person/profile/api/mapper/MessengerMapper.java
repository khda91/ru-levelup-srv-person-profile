package ru.levelp.srv.person.profile.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.levelp.srv.person.profile.api.data.MessengerData;
import ru.levelp.srv.person.profile.api.data.MessengerListResponse;
import ru.levelp.srv.person.profile.model.Messenger;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessengerMapper {

    default Messenger toMessenger(String messengerId) {
        return Messenger.builder().id(messengerId).build();
    }

    MessengerData toMessengerData(Messenger messenger);

    @Mappings({
            @Mapping(source = "messengers", target = "data"),
            @Mapping(source = "limit", target = "meta.pagination.limit"),
            @Mapping(source = "offset", target = "meta.pagination.offset"),
            @Mapping(source = "totalCount", target = "meta.pagination.totalCount")
    })
    MessengerListResponse toMessengerListResponse(Integer limit, Integer offset, Integer totalCount, List<MessengerData> messengers);
}
