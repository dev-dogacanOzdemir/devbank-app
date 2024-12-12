package com.devbank.user.management.impl.mongo.service;


import com.devbank.user.management.api.DTO.UserDTO;
import com.devbank.user.management.impl.mongo.document.UserDocument;
import com.devbank.user.management.impl.mongo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO registerUser(UserDTO userDTO) {
        UserDocument user = new UserDocument();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        UserDocument savedUser = userRepository.save(user);
        userDTO.setId(savedUser.getId());
        return userDTO;
    }

    public String authenticateUser(UserDTO userDTO) {
        UserDocument user = userRepository.findByUsername(userDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return "Authentication successful";
    }

    public UserDTO getUserById(String userId) {
        UserDocument user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

}
