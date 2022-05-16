package com.jack.dao;

import com.jack.entity.User;
import java.util.List;

public interface UserMapper {
    int insert(User record);

    List<User> selectAll();

    User selectByEmail(String email);

    int resetPassword(String email, String newPassword);
}