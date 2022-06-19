package ru.levelp.srv.person.profile.repository;

import lombok.RequiredArgsConstructor;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateCrud;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.levelp.srv.person.profile.model.PersonSocialNetwork;
import ru.levelp.srv.person.profile.utils.SqlUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PersonSocialNetworkRepositoryImpl implements PersonSocialNetworkRepository {

    private static final String ALL_FIELDS = "id, person_id, social_network_id, link";

    private static final String SELECT_ALL = "SELECT " + ALL_FIELDS + " FROM person_social_network";

    private static final String SELECT_COUNT = "SELECT count(id) FROM person_social_network";

    private static final String SELECT_PERSON_SOCIAL_NETWORK = SELECT_ALL + " WHERE person_id = uuid(:personId) AND social_network_id = :socialNetworkId";

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private RowMapper<PersonSocialNetwork> rowMapper;

    private JdbcTemplateCrud<PersonSocialNetwork, UUID> personSocialNetworkCrud;

    @PostConstruct
    private void init() {
        this.personSocialNetworkCrud = JdbcTemplateMapperFactory.newInstance()
                .crud(PersonSocialNetwork.class, UUID.class).to(jdbcTemplate, "person_social_network");
        this.rowMapper = JdbcTemplateMapperFactory.newInstance().newRowMapper(PersonSocialNetwork.class);
    }

    @Override
    public void create(PersonSocialNetwork personSocialNetwork) {
        personSocialNetworkCrud.create(personSocialNetwork);
    }

    @Override
    public List<PersonSocialNetwork> getAll(String personId, Integer limit, Integer offset, Optional<List<String>> socialNetworks) {
        var sql = new StringBuilder(SELECT_ALL);
        var namedParams = new MapSqlParameterSource()
                .addValue("personId", personId)
                .addValue("limit", limit)
                .addValue("offset", offset);

        sql.append(" WHERE person_id = uuid(:personId)");

        if (socialNetworks.isPresent()) {
            sql.append(" AND social_network_id IN (:socialNetworks)");
            namedParams.addValue("socialNetworks", socialNetworks.get());
        }

        sql.append(" ORDER BY id DESC");
        sql.append(" LIMIT :limit OFFSET :offset");

        return namedParameterJdbcTemplate.query(sql.toString(), namedParams, rowMapper);
    }

    @Override
    public Integer count(String personId, Optional<List<String>> socialNetworks) {
        var sql = new StringBuilder(SELECT_COUNT);
        var namedParams = new MapSqlParameterSource()
                .addValue("personId", personId);

        sql.append(" WHERE person_id = uuid(:personId)");

        if (socialNetworks.isPresent()) {
            sql.append(" AND social_network_id IN (:socialNetworks)");
            namedParams.addValue("socialNetworks", socialNetworks.get());
        }

        return namedParameterJdbcTemplate.queryForObject(sql.toString(), namedParams, Integer.class);
    }

    @Override
    public Optional<PersonSocialNetwork> getPersonSocialNetwork(String personId, String socialNetworkId) {
        var namedParams = new MapSqlParameterSource()
                .addValue("personId", personId)
                .addValue("socialNetworkId", socialNetworkId);
        return SqlUtils.emptyResultExceptionHelper(() -> namedParameterJdbcTemplate.queryForObject(SELECT_PERSON_SOCIAL_NETWORK,
                namedParams, rowMapper));
    }

    @Override
    public void delete(UUID socialNetworkId) {
        personSocialNetworkCrud.delete(socialNetworkId);
    }
}
