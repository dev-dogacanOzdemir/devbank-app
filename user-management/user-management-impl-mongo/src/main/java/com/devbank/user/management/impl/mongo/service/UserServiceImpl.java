package com.devbank.user.management.impl.mongo.service;

import com.devbank.error.management.exception.UserNotFoundException;
import com.devbank.user.management.api.DTO.UserDTO;
import com.devbank.user.management.impl.mongo.mapper.UserMapper;
import com.devbank.user.management.api.service.UserService;
import com.devbank.user.management.impl.mongo.model.User;
import com.devbank.user.management.impl.mongo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO registerUser(UserDTO userDTO){
        // Yeni kullanıcı oluştur.
        User user = new User();
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setTcNumber(userDTO.getTcNumber());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setRole(userDTO.getRole());
        user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword())); // Şifreyi hashle
        user.setCreatedAt(new Date());

        User savedUser = userRepository.save(user);

        userDTO.setId(savedUser.getId());
        return userDTO;
    }

    @Override
    public Optional<UserDTO> findByTcNumber(String tcNumber) {
        // T.C. kimlik numarasına göre kullanıcı bul
        return userRepository.findByTcNumber(tcNumber).map(user -> {
            UserDTO dto = new UserDTO();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setSurname(user.getSurname());
            dto.setTcNumber(user.getTcNumber());
            dto.setPhoneNumber(user.getPhoneNumber());
            dto.setRole(user.getRole());
            return dto;
        });
    }

    @Override
    public Optional<UserDTO> authenticateUser(String tcNumber, String phoneNumber, String password) {
        // Kullanıcıyı kimlik bilgilerine göre doğrula
        Optional<User> user = userRepository.findByTcNumberAndPhoneNumber(tcNumber, phoneNumber);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPasswordHash())) {
            UserDTO dto = new UserDTO();
            dto.setId(user.get().getId());
            dto.setName(user.get().getName());
            dto.setSurname(user.get().getSurname());
            dto.setTcNumber(user.get().getTcNumber());
            dto.setPhoneNumber(user.get().getPhoneNumber());
            dto.setRole(user.get().getRole());
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı: " + id));

        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setPhoneNumber(userDTO.getPhoneNumber());

        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    @Override
    public boolean requestPasswordReset(String tcNumber, String phoneNumber) {
        Optional<User> userOptional = userRepository.findByTcNumberAndPhoneNumber(tcNumber, phoneNumber);
        if (userOptional.isPresent()) {
            // TODO: Şifre sıfırlama işlemi için gerekli işlemler burada tanımlacak.
            // Örneğin: Kullanıcıya SMS veya e-posta ile doğrulama kodu gönderilebilri.
            return true;
        }
        return false;
    }

    @Override
    public boolean resetPassword(String tcNumber, String newPassword) {
        Optional<User> userOptional = userRepository.findByTcNumber(tcNumber);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPasswordHash(passwordEncoder.encode(newPassword)); // Yeni şifreyi hashle ve kaydet
            userRepository.save(user);
            return true;
        }
        return false;
    }

}
