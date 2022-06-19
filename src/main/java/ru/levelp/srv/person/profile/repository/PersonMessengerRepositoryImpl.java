package ru.levelp.srv.person.profile.repository;

import lombok.RequiredArgsConstructor;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateCrud;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.levelp.srv.person.profile.model.PersonMessenger;
import ru.levelp.srv.person.profile.utils.SqlUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PersonMessengerRepositoryImpl implements PersonMessengerRepository {

    private static final String ALL_FIELDS = "id, person_id, messenger_id, nickname";

    private static final String SELECT_ALL = "SELECT " + ALL_FIELDS + " FROM person_messenger";

    private static final String SELECT_COUNT = "SELECT count(id) FROM person_messenger";

    private static final String SELECT_PERSON_MESSENGER = SELECT_ALL + " WHERE person_id = uuid(:personId) AND messenger_id = :messengerId";

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private RowMapper<PersonMessenger> rowMapper;

    private JdbcTemplateCrud<PersonMessenger, UUID> personMessengerCrud;

    @PostConstruct
    private void init() {
        this.personMessengerCrud = JdbcTemplateMapperFactory.newInstance()
                .crud(PersonMessenger.class, UUID.class).to(jdbcTemplate, "person_messenger");
        this.rowMapper = JdbcTemplateMapperFactory.newInstance().newRowMapper(PersonMessenger.class);
    }

    @Override
    public void create(PersonMessenger personMessenger) {
        personMessengerCrud.create(personMessenger);
    }

    @Override
    public List<PersonMessenger> getAll(String personId, Integer limit, Integer offset, Optional<List<String>> messengers) {
        var sql = new StringBuilder(SELECT_ALL);
        var namedParams = new MapSqlParameterSource()
                .addValue("personId", personId)
                .addValue("limit", limit)
                .addValue("offset", offset);

        sql.append(" WHERE person_id = uuid(:personId)");

        if (messengers.isPresent()) {
            sql.append(" AND messenger_id IN (:messengers)");
            namedParams.addValue("messengers", messengers.get());
        }

        sql.append(" ORDER BY id DESC");
        sql.append(" LIMIT :limit OFFSET :offset");

        return namedParameterJdbcTemplate.query(sql.toString(), namedParams, rowMapper);
    }

    @Override
    public Integer count(String personId, Optional<List<String>> messengers) {
        var sql = new StringBuilder(SELECT_COUNT);
        var namedParams = new MapSqlParameterSource()
                .addValue("personId", personId);

        sql.append(" WHERE person_id = uuid(:personId)");

        if (messengers.isPresent()) {
            sql.append(" AND messenger_id IN (:messengers)");
            namedParams.addValue("messengers", messengers.get());
        }

        return namedParameterJdbcTemplate.queryForObject(sql.toString(), namedParams, Integer.class);
    }

    @Override
    public Optional<PersonMessenger> getPersonMessenger(String personId, String messengerId) {
        var namedParams = new MapSqlParameterSource()
                .addValue("personId", personId)
                .addValue("messengerId", messengerId);
        return SqlUtils.emptyResultExceptionHelper(() -> namedParameterJdbcTemplate.queryForObject(SELECT_PERSON_MESSENGER,
                namedParams, rowMapper));
    }

    @Override
    public void delete(UUID messengerId) {
        personMessengerCrud.delete(messengerId);
    }
}
