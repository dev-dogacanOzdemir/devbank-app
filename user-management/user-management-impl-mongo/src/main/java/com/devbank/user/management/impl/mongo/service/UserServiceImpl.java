package com.devbank.user.management.impl.mongo.service;

import com.devbank.error.management.exception.UserNotFoundException;
import com.devbank.user.management.api.DTO.AuthenticationRequest;
import com.devbank.user.management.api.DTO.UserDTO;
import com.devbank.user.management.impl.mongo.mapper.UserMapper;
import com.devbank.user.management.api.service.UserService;
import com.devbank.user.management.impl.mongo.document.UserDocument;
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
        UserDocument user = new UserDocument();
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setTcNumber(userDTO.getTcNumber());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setRole(userDTO.getRole());
        user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword())); // Şifreyi hashle
        user.setCreatedAt(new Date());

        // Log: Hashlenmiş şifreyi ve kullanıcı detaylarını kontrol edin
        System.out.println("Kayıt edilen kullanıcı: " + user);
        System.out.println("Hashlenmiş şifre: " + user.getPasswordHash());

        UserDocument savedUser = userRepository.save(user);

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
    public Optional<UserDTO> authenticateUser(AuthenticationRequest authRequest) {
        System.out.println("İstek logu: " + authRequest);
        // Kullanıcıyı veritabanında bul
        Optional<UserDocument> user = userRepository.findByTcNumberAndPhoneNumber(
                authRequest.getTcNumber(),
                authRequest.getPhoneNumber()
        );
        System.out.println("Kullanıcı sorgu sonucu: " + user);

        // Log: Kullanıcı bulunup bulunmadığını kontrol et
        if (user.isEmpty()) {
            System.out.println("Kullanıcı bulunamadı. T.C. Kimlik No: " + authRequest.getTcNumber()
                    + ", Telefon No: " + authRequest.getPhoneNumber());
            return Optional.empty();
        } else {
            System.out.println("Kullanıcı bulundu: " + user.get());
        }

        // Şifre doğrulama
        boolean isPasswordValid = passwordEncoder.matches(authRequest.getPassword(), user.get().getPasswordHash());
        System.out.println("Şifre doğrulama sonucu: " + isPasswordValid);

        if (!isPasswordValid) {
            System.out.println("Şifre hatalı.");
            return Optional.empty();
        }

        UserDTO userDTO = userMapper.toDto(user.get());
        System.out.println("UserDTO dönüşümü başarılı: " + userDTO);

        return Optional.of(userDTO);
    }

    @Override
    public UserDTO updateUser(String id, UserDTO userDTO) {
        UserDocument user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı: " + id));

        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setPhoneNumber(userDTO.getPhoneNumber());

        UserDocument updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    @Override
    public boolean requestPasswordReset(String tcNumber, String phoneNumber) {
        Optional<UserDocument> userOptional = userRepository.findByTcNumberAndPhoneNumber(tcNumber, phoneNumber);
        if (userOptional.isPresent()) {
            // TODO: Şifre sıfırlama işlemi için gerekli işlemler burada tanımlacak.
            // Örneğin: Kullanıcıya SMS veya e-posta ile doğrulama kodu gönderilebilri.
            return true;
        }
        return false;
    }

    @Override
    public boolean resetPassword(String tcNumber, String newPassword) {
        Optional<UserDocument> userOptional = userRepository.findByTcNumber(tcNumber);
        if (userOptional.isPresent()) {
            UserDocument user = userOptional.get();
            user.setPasswordHash(passwordEncoder.encode(newPassword)); // Yeni şifreyi hashle ve kaydet
            userRepository.save(user);
            return true;
        }
        return false;
    }

}
