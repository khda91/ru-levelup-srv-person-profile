package ru.levelp.srv.person.profile.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.levelp.srv.person.profile.api.data.CreatePersonSocialNetworkData;
import ru.levelp.srv.person.profile.api.data.PersonSocialNetworkListResponse;
import ru.levelp.srv.person.profile.api.mapper.PersonSocialNetworkMapper;
import ru.levelp.srv.person.profile.exception.EntityNotFoundProblemException;
import ru.levelp.srv.person.profile.model.PersonSocialNetwork;
import ru.levelp.srv.person.profile.repository.PersonSocialNetworkRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class PeopleSocialNetworkService {

    private final PeopleService peopleService;

    private final PersonSocialNetworkRepository personSocialNetworkRepository;

    private final PersonSocialNetworkMapper personSocialNetworkMapper;

    @Transactional
    public void addSocialNetwork(String personId, CreatePersonSocialNetworkData body) {
        peopleService.get(personId);
        UUID uuid = UUID.randomUUID();

        var personSocialNetwork = personSocialNetworkMapper.toPersonSocialNetwork(personId, body);
        personSocialNetwork.setId(uuid);

        personSocialNetworkRepository.create(personSocialNetwork);
    }

    @Transactional(readOnly = true)
    public PersonSocialNetworkListResponse getAll(String personId, Integer limit, Integer offset, List<String> socialNetwork) {
        Optional<List<String>> socialNetworksCondition = CollectionUtils.isEmpty(socialNetwork) ? Optional.empty() : Optional.of(socialNetwork);

        var socialNetworks = personSocialNetworkRepository.getAll(personId, limit, offset, socialNetworksCondition);
        var count = personSocialNetworkRepository.count(personId, socialNetworksCondition);
        return personSocialNetworkMapper.toPersonSocialNetworkListResponse(limit, offset, count, socialNetworks);
    }

    @Transactional
    public void removeSocialNetwork(String personId, String socialNetworkId) {
        var personSocialNetwork = personSocialNetworkRepository.getPersonSocialNetwork(personId, socialNetworkId)
                .orElseThrow(() -> new EntityNotFoundProblemException(PersonSocialNetwork.class, Map.of("personId", personId, "socialNetworkId", socialNetworkId)));
        var personSocialNetworkId = personSocialNetwork.getId();
        personSocialNetworkRepository.delete(personSocialNetworkId);
    }
}
