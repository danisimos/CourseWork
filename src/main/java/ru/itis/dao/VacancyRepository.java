package ru.itis.dao;

import ru.itis.dao.base.CrudRepository;
import ru.itis.models.Vacancy;

import java.util.List;

public interface VacancyRepository extends CrudRepository<Vacancy, Integer> {
    List<Vacancy> findByAuthorId(Integer id);
}
