package ru.levelp.srv.person.profile.repository;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateCrud;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.levelp.srv.person.profile.model.Person;
import ru.levelp.srv.person.profile.utils.SqlUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepository {

    private static final String ALL_FIELDS = "id, email, phone_number, role, first_name, last_name, middle_name, " +
            "gender, date_of_birth, place_of_birth, place_of_work, passport_series, passport_number, place_of_issue," +
            " date_of_issue, department_code, street, house_number, house_building, house_letter, flat, " +
            "city, postal_code";

    private static final String SELECT_ALL = "SELECT " + ALL_FIELDS + " FROM person";

    private static final String SELECT_ALL_LIMIT_OFFSET = "SELECT " + ALL_FIELDS + " FROM person " +
            "ORDER BY id LIMIT :limit OFFSET :offset";

    private static final String SELECT_BY_EMAIL = "SELECT " + ALL_FIELDS + " FROM person WHERE email = :email";

    private static final String SELECT_ALL_COUNT = "SELECT count(*) FROM person";

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private JdbcTemplateCrud<Person, UUID> personCrud;

    private RowMapper<Person> personRowMapper;

    @PostConstruct
    private void init() {
        this.personCrud = JdbcTemplateMapperFactory.newInstance()
                .crud(Person.class, UUID.class).to(jdbcTemplate, "person");
        this.personRowMapper = JdbcTemplateMapperFactory.newInstance().newRowMapper(Person.class);
    }

    @Override
    public Optional<Person> get(UUID id) {
        return Optional.of(personCrud.read(id));
    }

    @Override
    public Person save(Person person) {
        personCrud.create(person, person::setId);
        return person;
    }

    @Override
    public List<Person> getAll(String email, List<String> roles, Integer limit, Integer offset) {
        var sql = new StringBuilder(SELECT_ALL);
        var namedParams = new MapSqlParameterSource();
        sql.append(" WHERE TRUE");

        if (email != null) {
            sql.append(" AND email = :email");
            namedParams.addValue("email", email);
        }

        if (CollectionUtils.isNotEmpty(roles)) {
            sql.append(" AND role IN (:role)");
            namedParams.addValue("role", roles);
        }

        sql.append(" LIMIT :limit OFFSET :offset ");
        namedParams
                .addValue("limit", limit)
                .addValue("offset", offset);

        return namedParameterJdbcTemplate.query(sql.toString(), namedParams, personRowMapper);
    }

    @Override
    public Integer getTotalPeopleCount() {
        return jdbcTemplate.queryForObject(SELECT_ALL_COUNT, Integer.class);
    }

    @Override
    public Optional<Person> getByEmail(String email) {
        var namedParams = new MapSqlParameterSource()
                .addValue("email", email);
        return SqlUtils.emptyResultExceptionHelper(() -> namedParameterJdbcTemplate.queryForObject(
                SELECT_BY_EMAIL, namedParams, personRowMapper));
    }
}
