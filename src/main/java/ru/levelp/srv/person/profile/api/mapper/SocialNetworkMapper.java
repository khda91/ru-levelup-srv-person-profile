package ru.levelp.srv.person.profile.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.levelp.srv.person.profile.api.data.SocialNetworkData;
import ru.levelp.srv.person.profile.api.data.SocialNetworkListResponse;
import ru.levelp.srv.person.profile.model.SocialNetwork;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SocialNetworkMapper {

    default SocialNetwork toSocialNetwork(String socialNetworkId) {
        return SocialNetwork.builder().id(socialNetworkId).build();
    }

    SocialNetworkData toSocialNetworkData(SocialNetwork socialNetwork);

    @Mappings({
            @Mapping(source = "socialNetworks", target = "data"),
            @Mapping(source = "limit", target = "meta.pagination.limit"),
            @Mapping(source = "offset", target = "meta.pagination.offset"),
            @Mapping(source = "totalCount", target = "meta.pagination.totalCount")
    })
    SocialNetworkListResponse toSocialNetworkListResponse(Integer limit, Integer offset, Integer totalCount,
                                                          List<SocialNetworkData> socialNetworks);
}
