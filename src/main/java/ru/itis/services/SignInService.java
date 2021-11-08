package ru.itis.services;

import ru.itis.dto.SignInForm;
import ru.itis.dto.UserDto;
import ru.itis.models.User;

public interface SignInService {
    UserDto signIn(SignInForm signInForm);
}
