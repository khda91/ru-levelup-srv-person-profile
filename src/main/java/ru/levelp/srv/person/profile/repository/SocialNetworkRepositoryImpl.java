package ru.levelp.srv.person.profile.repository;

import lombok.RequiredArgsConstructor;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateCrud;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.levelp.srv.person.profile.model.SocialNetwork;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SocialNetworkRepositoryImpl implements SocialNetworkRepository {

    private static final String SELECT_ALL = "SELECT id FROM social_network ORDER BY id LIMIT :limit OFFSET :offset";
    private static final String SELECT_ALL_COUNT = "SELECT count(*) FROM social_network";

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private JdbcTemplateCrud<SocialNetwork, String> socialNetworkTypeCrud;

    private RowMapper<SocialNetwork> socialNetworkRowMapper;

    @PostConstruct
    private void init() {
        this.socialNetworkTypeCrud = JdbcTemplateMapperFactory.newInstance()
                .crud(SocialNetwork.class, String.class).to(jdbcTemplate, "social_network");
        this.socialNetworkRowMapper = JdbcTemplateMapperFactory.newInstance()
                .newRowMapper(SocialNetwork.class);
    }

    @Override
    public Optional<SocialNetwork> get(String socialNetwork) {
        return Optional.ofNullable(socialNetworkTypeCrud.read(socialNetwork));
    }

    @Override
    public SocialNetwork create(SocialNetwork socialNetwork) {
        socialNetworkTypeCrud.create(socialNetwork);
        return socialNetwork;
    }

    @Override
    public void delete(String socialNetwork) {
        socialNetworkTypeCrud.delete(socialNetwork);
    }

    @Override
    public List<SocialNetwork> getAll(Integer limit, Integer offset) {
        var namedParams = new MapSqlParameterSource()
                .addValue("limit", limit)
                .addValue("offset", offset);
        return namedParameterJdbcTemplate.query(SELECT_ALL, namedParams, socialNetworkRowMapper);
    }

    @Override
    public Integer getTotalCount() {
        return jdbcTemplate.queryForObject(SELECT_ALL_COUNT, Integer.class);
    }
}
