package ru.itis.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.dao.VacancyRepository;
import ru.itis.models.User;
import ru.itis.models.Vacancy;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class VacancyRepositoryImpl implements VacancyRepository {
    private final static String SQL_INSERT = "insert into vacancy(content, author_id) values (?, ?)";
    private final static String SQL_UPDATE = "update vacancy set content = ?, author_id = ? where id = ?";
    private final static String SQL_SELECT_BY_ID = "select *, v.id as vacancy_id, u.id as user_id from vacancy v left join users u on u.id = v.author_id where v.id = ?";
    private final static String SQL_SELECT_ALL = "select *, v.id as vacancy_id, u.id as user_id from vacancy v left join users u on u.id = v.author_id";
    private final static String SQL_DELETE_BY_ID = "delete from vacancy where id = ?";
    private final static String SQL_SELECT_BY_AUTHOR = "select *, v.id as vacancy_id, u.id as user_id  from vacancy v left join users u on u.id = v.author_id where author_id = ?";

    private final RowMapper<Vacancy> rowMapper = (row, rowNumber) ->
            Vacancy.builder()
                    .id(row.getInt("vacancy_id"))
                    .content(row.getString("content"))
                    .author(User.builder()
                            .id(row.getInt("user_id"))
                            .firstName(row.getString("first_name"))
                            .lastName(row.getString("last_name"))
                            .hashPassword(row.getString("hash_password"))
                            .email(row.getString("email"))
                            .avatarId(row.getInt("avatar_id") == 0 ? null : row.getInt("avatar_id"))
                            .build())
                    .build();

    private final JdbcTemplate jdbcTemplate;

    public VacancyRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Vacancy> findByAuthorId(Integer id) {
        return jdbcTemplate.query(SQL_SELECT_BY_AUTHOR, rowMapper, id);
    }

    @Override
    public Optional<Vacancy> findById(Integer id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, rowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Vacancy> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }

    @Override
    public Vacancy save(Vacancy item) {
        if(item.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[]{"id"});
                statement.setString(1, item.getContent());
                statement.setInt(2, item.getAuthor().getId());

                return statement;
            }, keyHolder);
            if (keyHolder.getKey() != null) {
                item.setId(keyHolder.getKey().intValue());
            }
        } else {
            jdbcTemplate.update(SQL_UPDATE,
                    item.getContent(),
                    item.getAuthor().getId(),
                    item.getId()
            );
        }
        return item;
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }
}
