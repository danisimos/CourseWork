package ru.itis.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.dao.FilesRepository;
import ru.itis.models.FileInfo;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class FilesRepositoryImpl implements FilesRepository {

    private final static String SQL_INSERT = "insert into file_info(storage_file_name, original_file_name, type, size) " +
            "values (?, ?, ?, ?)";
    private final static String SQL_UPDATE = "update file_info set storage_file_name = ?, original_file_name = ?, type = ?, size = ? where id = ?";
    private final static String SQL_SELECT_BY_ID = "select * from file_info where id = ?";
    private final static String SQL_SELECT_ALL = "select * from file_info";
    private final static String SQL_DELETE_BY_ID = "delete from file_info where id = ?";

    private JdbcTemplate jdbcTemplate;

    public FilesRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<FileInfo> rowMapper = (row, rowNumber) ->
        FileInfo.builder()
                .id(row.getInt("id"))
                .originalFileName(row.getString("original_file_name"))
                .storageFileName(row.getString("storage_file_name"))
                .size(row.getLong("size"))
                .type(row.getString("type"))
                .build();


    @Override
    public FileInfo save(FileInfo entity) {
        if(entity.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[]{"id"});
                statement.setString(1, entity.getStorageFileName());
                statement.setString(2, entity.getOriginalFileName());
                statement.setString(3, entity.getType());
                statement.setLong(4, entity.getSize());
                return statement;
            }, keyHolder);
            entity.setId(keyHolder.getKey().intValue());
        } else {
            jdbcTemplate.update(SQL_UPDATE,
                    entity.getStorageFileName(),
                    entity.getOriginalFileName(),
                    entity.getType(),
                    entity.getSize(),
                    entity.getId()
            );
        }
        return entity;
    }

    @Override
    public Optional<FileInfo> findById(Integer id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, rowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<FileInfo> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }
}
