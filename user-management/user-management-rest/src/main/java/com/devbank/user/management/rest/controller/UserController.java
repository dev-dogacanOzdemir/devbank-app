package com.devbank.user.management.rest.controller;

import com.devbank.error.management.exception.UserNotFoundException;
import com.devbank.user.management.api.DTO.AuthenticationRequest;
import com.devbank.user.management.api.DTO.AuthenticationResponse;
import com.devbank.user.management.api.DTO.LoginInfoDTO;
import com.devbank.user.management.api.DTO.UserDTO;
import com.devbank.user.management.api.service.LoginInfoService;
import com.devbank.user.management.api.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {


    private final UserService userService;
    private final LoginInfoService loginInfoService;

    public UserController(UserService userService, LoginInfoService loginInfoService) {
        this.userService = userService;
        this.loginInfoService = loginInfoService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO registeredUser = userService.registerUser(userDTO);
        return ResponseEntity.ok(registeredUser);
    }


    @GetMapping("/{tcNumber}")
    public ResponseEntity<UserDTO> getUserByTcNumber(@PathVariable String tcNumber) {
        return userService.findByTcNumber(tcNumber)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException("User not found with T.C. Number : " + tcNumber));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> authenticateUser(
            @RequestBody AuthenticationRequest authRequest,
            HttpServletRequest request) {
        return userService.authenticateUser(authRequest, request)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException("Invalid login credentials"));
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/password-reset/request")
    public ResponseEntity<String> requestPasswordReset(@RequestParam String tcNumber, @RequestParam String phoneNumber) {
        boolean success = userService.requestPasswordReset(tcNumber, phoneNumber);
        if (success) {
            return ResponseEntity.ok("Password reset request successful. Please check your phone.");
        } else {
            return ResponseEntity.badRequest().body("Invalid credentials.");
        }
    }

    @PostMapping("/password-reset/confirm")
    public ResponseEntity<String> resetPassword(@RequestParam String tcNumber, @RequestParam String newPassword) {
        boolean success = userService.resetPassword(tcNumber, newPassword);
        if (success) {
            return ResponseEntity.ok("Password successfully reset.");
        } else {
            return ResponseEntity.badRequest().body("Failed to reset password.");
        }
    }

    @GetMapping("/login-info/{userId}")
    public ResponseEntity<List<LoginInfoDTO>> getLoginInfoByUserId(@PathVariable String userId) {
        List<LoginInfoDTO> loginInfoList = loginInfoService.getLoginInfoByUserId(userId);

        if (loginInfoList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(loginInfoList);
    }

    @GetMapping("/validate/{userId}")
    public ResponseEntity<?> validateUser(@PathVariable String userId) {
        AuthenticationResponse response = userService.validateUser(userId);
        return ResponseEntity.ok(response);
    }

}
