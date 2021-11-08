package ru.itis.services.impl;

import ru.itis.services.PasswordEncoderService;

public class PasswordEncoderServiceImpl implements PasswordEncoderService {
    @Override
    public String encode(String password) {
        int i = 0;

        for(char c: password.toCharArray()) {
            i+=c;
        }

        i = (i + password.length()) * password.length();

        return String.valueOf(i);
    }

    @Override
    public boolean match(String hash, String password) {
        return hash.equals(encode(password));
    }
}
