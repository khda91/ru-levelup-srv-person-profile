package ru.levelp.srv.person.profile.repository;

import lombok.RequiredArgsConstructor;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateCrud;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.levelp.srv.person.profile.model.Person;

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
        return Optional.empty();
    }

    @Override
    public Person save(Person person) {
        return null;
    }

    @Override
    public List<Person> getAll(Integer limit, Integer offset) {
        var namedParams = new MapSqlParameterSource()
                .addValue("limit", limit)
                .addValue("offset", offset);
        return namedParameterJdbcTemplate.query(SELECT_ALL_LIMIT_OFFSET, namedParams, personRowMapper);
    }

    @Override
    public Integer getTotalPeopleCount() {
        return jdbcTemplate.queryForObject(SELECT_ALL_COUNT, Integer.class);
    }
}
