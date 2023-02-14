package com.henry.service;

import com.henry.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);

    List<User> findAll();

    Optional<User> findByFirstName();

    List<User> findActiveUsers();

    Integer getSum(int a, int b);
}
