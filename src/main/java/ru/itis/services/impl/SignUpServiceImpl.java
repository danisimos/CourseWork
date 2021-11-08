package ru.itis.services.impl;

import ru.itis.dao.UserRepository;
import ru.itis.dto.SignUpForm;
import ru.itis.models.User;
import ru.itis.services.PasswordEncoderService;
import ru.itis.services.SignUpService;

public class SignUpServiceImpl implements SignUpService {
    UserRepository userRepository;
    PasswordEncoderService passwordEncoderService;

    public SignUpServiceImpl(UserRepository userRepository, PasswordEncoderService passwordEncoderService) {
        this.userRepository = userRepository;
        this.passwordEncoderService = passwordEncoderService;
    }

    @Override
    public void signUp(SignUpForm signUpForm) {
        User user = User.builder()
                .firstName(signUpForm.getFirstName())
                .lastName(signUpForm.getLastName())
                .email(signUpForm.getEmail())
                .hashPassword(passwordEncoderService.encode(signUpForm.getPassword()))
                .build();

        userRepository.save(user);
    }
}
