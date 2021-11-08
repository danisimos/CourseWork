package ru.itis.dao;

import ru.itis.dao.base.CrudRepository;
import ru.itis.models.CurriculumVitae;

import java.util.List;

public interface CurriculumVitaeRepository extends CrudRepository<CurriculumVitae, Integer> {
    List<CurriculumVitae> findByAuthorId(Integer id);
}
