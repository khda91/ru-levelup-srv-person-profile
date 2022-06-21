package ru.levelp.srv.person.profile.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.levelp.srv.person.profile.api.data.CreatePersonSocialNetworkData;
import ru.levelp.srv.person.profile.api.data.PersonSocialNetworkData;
import ru.levelp.srv.person.profile.api.data.PersonSocialNetworkListResponse;
import ru.levelp.srv.person.profile.model.PersonSocialNetwork;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonSocialNetworkMapper extends UuidToStringMappingStrategy {

    PersonSocialNetworkData toPersonSocialNetworkData(PersonSocialNetwork personSocialNetwork);

    PersonSocialNetwork toPersonSocialNetwork(CreatePersonSocialNetworkData createPersonSocialNetworkData);

    @Mappings({
            @Mapping(source = "createPersonSocialNetworkData.socialNetworkId", target = "socialNetworkId"),
            @Mapping(source = "createPersonSocialNetworkData.link", target = "link")
    })
    PersonSocialNetwork toPersonSocialNetwork(String personId, CreatePersonSocialNetworkData createPersonSocialNetworkData);

    @Mappings({
            @Mapping(source = "personSocialNetworks", target = "data"),
            @Mapping(source = "limit", target = "meta.pagination.limit"),
            @Mapping(source = "offset", target = "meta.pagination.offset"),
            @Mapping(source = "count", target = "meta.pagination.totalCount"),
    })
    PersonSocialNetworkListResponse toPersonSocialNetworkListResponse(Integer limit, Integer offset, Integer count, List<PersonSocialNetwork> personSocialNetworks);
}
