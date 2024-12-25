package com.devbank.user.management.api.service;

import com.devbank.user.management.api.DTO.AuthenticationRequest;
import com.devbank.user.management.api.DTO.UserDTO;

import java.util.Optional;

public interface UserService {

    // Yeni kullanıcı kaydı
    UserDTO registerUser(UserDTO userDTO);

    // T.C. kimlik numarası ile kullanıcı arama
    Optional<UserDTO> findByTcNumber(String tcNumber);

    // Giriş işlemleri için kullanıcı kontrolü
    Optional<UserDTO> authenticateUser(AuthenticationRequest authRequest);

    // Kullanıcı bilgilerini güncelleme
    UserDTO updateUser(String id, UserDTO userDTO);

    // Parola sıfırlama isteği
    boolean requestPasswordReset(String tcNumber, String phoneNumber);

    // Parola sıfırlama
    boolean resetPassword(String tcNumber, String newPassword);

}
