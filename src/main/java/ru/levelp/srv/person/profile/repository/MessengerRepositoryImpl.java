package ru.levelp.srv.person.profile.repository;

import lombok.RequiredArgsConstructor;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateCrud;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.levelp.srv.person.profile.model.Messenger;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MessengerRepositoryImpl implements MessengerRepository {

    private static final String SELECT_ALL = "SELECT id FROM messenger ORDER BY id LIMIT :limit OFFSET :offset";
    private static final String SELECT_ALL_COUNT = "SELECT count(*) FROM messenger";

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private JdbcTemplateCrud<Messenger, String> messengerTypeCrud;

    private RowMapper<Messenger> messengerRowMapper;

    @PostConstruct
    private void init() {
        this.messengerTypeCrud = JdbcTemplateMapperFactory.newInstance()
                .crud(Messenger.class, String.class).to(jdbcTemplate, "messenger");
        this.messengerRowMapper = JdbcTemplateMapperFactory.newInstance()
                .newRowMapper(Messenger.class);
    }

    @Override
    public Optional<Messenger> get(String messenger) {
        return Optional.ofNullable(messengerTypeCrud.read(messenger));
    }

    @Override
    public Messenger create(Messenger messenger) {
        messengerTypeCrud.create(messenger);
        return messenger;
    }

    @Override
    public void delete(String messenger) {
        messengerTypeCrud.delete(messenger);
    }

    @Override
    public List<Messenger> getAll(Integer limit, Integer offset) {
        var namedParams = new MapSqlParameterSource()
                .addValue("limit", limit)
                .addValue("offset", offset);
        return namedParameterJdbcTemplate.query(SELECT_ALL, namedParams, messengerRowMapper);
    }

    @Override
    public Integer getTotalCount() {
        return jdbcTemplate.queryForObject(SELECT_ALL_COUNT, Integer.class);
    }
}
