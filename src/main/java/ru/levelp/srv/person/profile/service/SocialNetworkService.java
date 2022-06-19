package ru.levelp.srv.person.profile.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.levelp.srv.person.profile.api.data.SocialNetworkListResponse;
import ru.levelp.srv.person.profile.api.mapper.SocialNetworkMapper;
import ru.levelp.srv.person.profile.repository.SocialNetworkRepository;
import ru.levelp.srv.person.profile.validation.social.network.SocialNetworkValidationAggregator;

import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class SocialNetworkService {

    private final SocialNetworkRepository socialNetworkRepository;

    private final SocialNetworkMapper socialNetworkMapper;

    private final SocialNetworkValidationAggregator socialNetworkValidationAggregator;

    @Transactional(readOnly = true)
    public SocialNetworkListResponse getSocialNetworks(Integer limit, Integer offset) {
        var socialNetworks = socialNetworkRepository.getAll(limit, offset);
        var count = socialNetworkRepository.getTotalCount();

        return socialNetworkMapper.toSocialNetworkListResponse(limit, offset, count,
                socialNetworks.stream()
                        .map(socialNetworkMapper::toSocialNetworkData)
                        .collect(Collectors.toList()));
    }

    @Transactional
    public void removeSocialNetwork(String socialNetworkId) {
        socialNetworkRepository.delete(socialNetworkId);
    }

    @Transactional
    public void createSocialNetwork(String socialNetworkId) {
        socialNetworkValidationAggregator.validate(socialNetworkId);

        var socialNetwork = socialNetworkMapper.toSocialNetwork(socialNetworkId);
        socialNetworkRepository.create(socialNetwork);
    }

    public boolean exists(String socialNetworkId) {
        return socialNetworkRepository.get(socialNetworkId).isPresent();
    }
}
