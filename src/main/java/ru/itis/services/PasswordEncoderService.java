package ru.itis.services;

public interface PasswordEncoderService {
    String encode(String password);
    boolean match(String hash, String password);
}
