package com.henry.repository;

import com.henry.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>, UserRepositoryCustom, QuerydslPredicateExecutor<User> {

    @Query("select u from User u where u.deleted=false")
    List<User> findByDeletedFalse();
}
