package com.henry.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.henry.model.User;
import com.henry.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public String helloWorld() {

        return  """
                Hello World, 
                multi-line,
                text block.
                """;
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/active")
    public List<User> findActiveUsers() {
        return userService.findActiveUsers();
    }

    @GetMapping("/sum/{a}/{b}")
    public Integer getSum(@PathVariable int a, @PathVariable int b) {
        return userService.getSum(a, b);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody User user) {
        return userService.save(user);
    }

    // @GetMapping("/create")
    // @ResponseStatus(HttpStatus.CREATED)
    // public User createUser() {
    //     User user = User.builder()
    //                     .firstName("Chacko"+ RandomGenerator.getDefault().nextInt())
    //                     .lastName("Mathew"+RandomGenerator.getDefault().nextInt())
    //                     .deleted(false)
    //                     .build();
    //     return userService.save(user);
    // }

}
