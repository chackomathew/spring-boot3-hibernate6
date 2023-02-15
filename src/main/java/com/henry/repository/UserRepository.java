package com.henry.repository;

import com.henry.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>, UserRepositoryCustom {

    Optional<User> findByFirstName(String firstName);

    List<User> findByDeleted(boolean deleted);

    List<User> findByDeletedFalse();
}
