package com.example.calender.repository;

import com.example.calender.entity.User;

import java.util.Optional;

public interface UserRepository {
    void saveUser(User user);

    String findUserNameById(Long userId);

    User findByUserName(String userName);

    User findById(Long userId);

    void updateUserName(Long userId, String newUserName);
}
