package ru.itis.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.dao.UserRepository;
import ru.itis.models.User;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private final static String SQL_INSERT = "insert into users(first_name, last_name, email, hash_password, avatar_id) " +
            "values (?, ?, ?, ?, ?)";
    private final static String SQL_UPDATE = "update users set first_name = ?, last_name = ?, email = ?, password_hash = ?, avatar_id = ? where id = ?";
    private final static String SQL_UPDATE_AVATAR = "update users set avatar_id = ? where id = ?";
    private final static String SQL_SELECT_BY_ID = "select * from users where id = ?";
    private final static String SQL_SELECT_BY_EMAIL = "select * from users where email = ?";
    private final static String SQL_SELECT_ALL = "select * from users";
    private final static String SQL_DELETE_BY_ID = "delete from users where id = ?";

    private final RowMapper<User> rowMapper = (row, rowNumber) ->
            User.builder()
                    .id(row.getInt("id"))
                    .firstName(row.getString("first_name"))
                    .lastName(row.getString("last_name"))
                    .hashPassword(row.getString("hash_password"))
                    .email(row.getString("email"))
                    .avatarId(row.getInt("avatar_id") == 0 ? null : row.getInt("avatar_id"))
                    .build();

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_EMAIL, rowMapper, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void updateAvatarForUser(Integer userId, Integer fileId) {
        jdbcTemplate.update(SQL_UPDATE_AVATAR, fileId, userId);
    }

    @Override
    public Optional<User> findById(Integer id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, rowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }

    @Override
    public User save(User item) {
        if(item.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[]{"id"});
                statement.setString(1, item.getFirstName());
                statement.setString(2, item.getLastName());
                statement.setString(3, item.getEmail());
                statement.setString(4, item.getHashPassword());

                if(item.getAvatarId() != null) {
                    statement.setLong(5, item.getAvatarId());
                } else {
                    statement.setNull(5, Types.NULL);
                }
                return statement;
            }, keyHolder);
            if (keyHolder.getKey() != null) {
                item.setId(keyHolder.getKey().intValue());
            }
        } else {
            jdbcTemplate.update(SQL_UPDATE,
                    item.getFirstName(),
                    item.getLastName(),
                    item.getEmail(),
                    item.getHashPassword(),
                    item.getAvatarId(),
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
