package ru.levelp.srv.person.profile.repository;

import ru.levelp.srv.person.profile.model.PersonSocialNetwork;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonSocialNetworkRepository {

    void create(PersonSocialNetwork personSocialNetwork);

    List<PersonSocialNetwork> getAll(String personId, Integer limit, Integer offset, Optional<List<String>> socialNetworks);

    Integer count(String personId, Optional<List<String>> socialNetworks);

    Optional<PersonSocialNetwork> getPersonSocialNetwork(String personId, String socialNetworkId);

    void delete(UUID socialNetworkId);
}
