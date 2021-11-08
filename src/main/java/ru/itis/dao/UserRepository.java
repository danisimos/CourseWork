package ru.itis.dao;

import ru.itis.dao.base.CrudRepository;
import ru.itis.models.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    void updateAvatarForUser(Integer userId, Integer fileId);
}
