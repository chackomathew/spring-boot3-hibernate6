package com.henry.integration;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.henry.Spring3Hibernate6Application;
import com.henry.model.User;
import com.henry.repository.UserRepository;
import com.henry.service.UserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Spring3Hibernate6Application.class)
@ActiveProfiles("integration-tests")
@Tag("integration-test")
@Testcontainers
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserServiceIntegrationTest {

    @Container
    private static final PostgreSQLContainer POSTGRES_SQL_CONTAINER = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("password");

    @BeforeAll
    public static void startContainer() {
        POSTGRES_SQL_CONTAINER.start();
    }

    @BeforeEach
    public void deleteAllData() {
        userRepository.deleteAllInBatch();
    }


    @AfterAll
    public static void stopContainer() {
        POSTGRES_SQL_CONTAINER.stop();
    }

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    // ERROR Testcase org.springframework.dao.InvalidDataAccessResourceUsageException: JDBC exception executing SQL [select u1_0.id,u1_0.deleted,u1_0.first_name,u1_0.last_name from users u1_0 where not(u1_0.deleted)
    // Caused by: org.postgresql.util.PSQLException: ERROR: argument of NOT must be type boolean, not type character
    @Test
    public void testBooleanAttributeConverterIssue_shouldReturnAllActiveUsers() {
        //given
        User user = new User(null, "FirstName", "LastName", false);
        User savedUser = userService.save(user);

        //when
        List<User> users = userService.findActiveUsers();

        //then
        Assert.assertEquals("User count doesn't match", 1, users.size());
        Assert.assertEquals("Active user is not returned", savedUser, users.stream().findAny().get());

    }
    
}
