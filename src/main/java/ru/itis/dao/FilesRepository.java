package ru.itis.dao;

import ru.itis.dao.base.CrudRepository;
import ru.itis.models.FileInfo;

import java.util.List;
import java.util.Optional;

public interface FilesRepository extends CrudRepository<FileInfo, Integer> {
    FileInfo save(FileInfo fileInfo);
    Optional<FileInfo> findById(Integer id);
    List<FileInfo> findAll();
    void delete(Integer id);
}
