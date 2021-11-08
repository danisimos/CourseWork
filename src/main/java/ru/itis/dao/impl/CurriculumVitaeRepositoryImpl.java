package ru.itis.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.dao.CurriculumVitaeRepository;
import ru.itis.models.CurriculumVitae;
import ru.itis.models.User;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class CurriculumVitaeRepositoryImpl implements CurriculumVitaeRepository {
    private final static String SQL_INSERT = "insert into curriculum_vitae(content, author_id) values (?, ?)";
    private final static String SQL_UPDATE = "update curriculum_vitae set content = ?, author_id = ? where id = ?";
    private final static String SQL_SELECT_BY_ID = "select *, c.id as cv_id, u.id as user_id from curriculum_vitae c left join users u on u.id = c.author_id where c.id = ?";
    private final static String SQL_SELECT_ALL = "select *, c.id as cv_id, u.id as user_id from curriculum_vitae c left join users u on u.id = c.author_id";
    private final static String SQL_DELETE_BY_ID = "delete from curriculum_vitae where id = ?";
    private final static String SQL_SELECT_BY_AUTHOR = "select *, c.id as cv_id, u.id as user_id from curriculum_vitae c left join users u on u.id = c.author_id where author_id = ?";

    private final RowMapper<CurriculumVitae> rowMapper = (row, rowNumber) ->
            CurriculumVitae.builder()
                    .id(row.getInt("cv_id"))
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

    public CurriculumVitaeRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public List<CurriculumVitae> findByAuthorId(Integer id) {
        return jdbcTemplate.query(SQL_SELECT_BY_AUTHOR, rowMapper, id);
    }

    @Override
    public Optional<CurriculumVitae> findById(Integer id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, rowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<CurriculumVitae> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }

    @Override
    public CurriculumVitae save(CurriculumVitae item) {
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
