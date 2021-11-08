package ru.itis.services.impl;

import ru.itis.dao.UserRepository;
import ru.itis.dto.SignInForm;
import ru.itis.dto.UserDto;
import ru.itis.models.User;
import ru.itis.services.PasswordEncoderService;
import ru.itis.services.SignInService;

import java.util.Optional;

public class SignInServiceImpl implements SignInService {
    UserRepository userRepository;
    PasswordEncoderService passwordEncoderService;

    public SignInServiceImpl(UserRepository userRepository, PasswordEncoderService passwordEncoderService) {
        this.userRepository = userRepository;
        this.passwordEncoderService = passwordEncoderService;
    }

    @Override
    public UserDto signIn(SignInForm signInForm) {
        Optional<User> user = userRepository.findByEmail(signInForm.getEmail());

        if(user.isEmpty()) return null;
        if(!passwordEncoderService.match(user.get().getHashPassword(), signInForm.getPassword())) return null;

        return UserDto.fromUser(user.get());
    }
}
