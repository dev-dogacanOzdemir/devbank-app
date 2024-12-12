package com.devbank.user.management.impl.mongo;


import com.devbank.user.management.api.DTO.UserDTO;
import com.devbank.user.management.impl.mongo.document.UserDocument;
import com.devbank.user.management.impl.mongo.repository.UserRepository;
import com.devbank.user.management.impl.mongo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class UserServiceTest {
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void testRegisterUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setPassword("password");
        userDTO.setRole("USER");

        UserDocument mockUser = new UserDocument();
        mockUser.setId("1");
        mockUser.setUsername("testuser");
        mockUser.setPassword(passwordEncoder.encode("password"));
        mockUser.setRole("USER");

        Mockito.when(userRepository.save(any(UserDocument.class))).thenReturn(mockUser);

        UserDTO result = userService.registerUser(userDTO);

        assertNotNull(result.getId());
        assertEquals("testuser", result.getUsername());
        assertEquals("USER", result.getRole());
    }

    @Test
    void testAuthenticateUser_Success() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setPassword("password");

        UserDocument mockUser = new UserDocument();
        mockUser.setUsername("testuser");
        mockUser.setPassword(passwordEncoder.encode("password"));

        Mockito.when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));

        String result = userService.authenticateUser(userDTO);

        assertEquals("Authentication successful", result);
    }

    @Test
    void testAuthenticateUser_InvalidCredentials() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setPassword("wrongpassword");

        UserDocument mockUser = new UserDocument();
        mockUser.setUsername("testuser");
        mockUser.setPassword(passwordEncoder.encode("password"));

        Mockito.when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));

        Exception exception = assertThrows(RuntimeException.class, () -> userService.authenticateUser(userDTO));
        assertEquals("Invalid credentials", exception.getMessage());
    }

    @Test
    void testGetUserById_UserFound() {
        String userId = "1";

        UserDocument mockUser = new UserDocument();
        mockUser.setId(userId);
        mockUser.setUsername("testuser");
        mockUser.setRole("USER");

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        UserDTO result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("testuser", result.getUsername());
        assertEquals("USER", result.getRole());
    }

    @Test
    void testGetUserById_UserNotFound() {
        String userId = "1";

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.getUserById(userId));
        assertEquals("User not found", exception.getMessage());
    }
}
