package ru.levelp.srv.person.profile.repository;

import ru.levelp.srv.person.profile.model.SocialNetwork;

import java.util.List;
import java.util.Optional;

public interface SocialNetworkRepository {

    Optional<SocialNetwork> get(String socialNetwork);

    SocialNetwork create(SocialNetwork socialNetwork);

    void delete(String socialNetwork);

    List<SocialNetwork> getAll(Integer limit, Integer offset);

    Integer getTotalCount();
}
