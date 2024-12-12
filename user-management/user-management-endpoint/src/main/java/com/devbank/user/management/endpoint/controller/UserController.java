package com.devbank.user.management.endpoint.controller;


import com.devbank.user.management.api.DTO.UserDTO;
import com.devbank.user.management.impl.mongo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserDTO registerUser(@RequestBody UserDTO userDTO) {
        return userService.registerUser(userDTO);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody UserDTO userDTO) {
        return userService.authenticateUser(userDTO);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO getUserById(@PathVariable String userId) {
        return userService.getUserById(userId);
    }
}
