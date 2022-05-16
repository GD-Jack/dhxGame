package com.jack.service;

import com.jack.entity.User;

import java.io.IOException;

public interface UserService {
    int login(String email, String password);

    int signUp(User user);

    User selectByEmail(String email);

    int resetPassword(String email, String newPassword);

    void sendSecurityCode(String email) ;

    String getSecurityCode(String email, String type);
}
