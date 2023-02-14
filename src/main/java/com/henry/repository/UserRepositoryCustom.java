package com.henry.repository;

import java.util.Optional;

import com.henry.model.User;

public interface UserRepositoryCustom {

    public Integer getSum(int a, int b);

    Optional<User> findByFirstName(String firstName);
}
