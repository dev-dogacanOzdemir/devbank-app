package com.devbank.user.management.impl.mongo;

import com.devbank.error.management.exception.UserNotFoundException;
import com.devbank.user.management.api.DTO.AuthenticationRequest;
import com.devbank.user.management.api.DTO.LoginInfoDTO;
import com.devbank.user.management.api.DTO.UserDTO;
import com.devbank.user.management.api.enums.Role;
import com.devbank.user.management.api.service.LoginInfoService;
import com.devbank.user.management.impl.mongo.mapper.LoginInfoMapper;
import com.devbank.user.management.impl.mongo.mapper.UserMapper;
import com.devbank.user.management.impl.mongo.document.LoginInfoDocument;
import com.devbank.user.management.impl.mongo.document.UserDocument;
import com.devbank.user.management.impl.mongo.repository.LoginInfoRepository;
import com.devbank.user.management.impl.mongo.repository.UserRepository;
import com.devbank.user.management.impl.mongo.service.LoginInfoServiceImpl;
import com.devbank.user.management.impl.mongo.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;

import static com.devbank.user.management.api.enums.Role.ROLE_CUSTOMER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDocumentServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @Mock
    private LoginInfoServiceImpl loginInfoService;

    @Mock
    private LoginInfoRepository loginInfoRepository;


    @Mock
    private LoginInfoMapper loginInfoMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Ahmet");
        userDTO.setSurname("Yılmaz");
        userDTO.setTcNumber("12345678901");
        userDTO.setPhoneNumber("5551234567");
        userDTO.setPassword("password");

        UserDocument user = new UserDocument();
        user.setId("1");
        user.setName("Ahmet");
        user.setSurname("Yılmaz");
        user.setTcNumber("12345678901");
        user.setPhoneNumber("5551234567");
        user.setPasswordHash("hashedPassword");
        user.setCreatedAt(new Date());

        when(passwordEncoder.encode("password")).thenReturn("hashedPassword");
        when(userRepository.save(any(UserDocument.class))).thenReturn(user);

        UserDTO result = userService.registerUser(userDTO);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Ahmet", result.getName());
        verify(userRepository, times(1)).save(any(UserDocument.class));
    }

    @Test
    void testFindByTcNumber() {
        UserDocument user = new UserDocument();
        user.setId("1");
        user.setName("Ahmet");
        user.setSurname("Yılmaz");
        user.setTcNumber("12345678901");
        user.setPhoneNumber("5551234567");

        when(userRepository.findByTcNumber("12345678901")).thenReturn(Optional.of(user));

        Optional<UserDTO> result = userService.findByTcNumber("12345678901");

        assertTrue(result.isPresent());
        assertEquals("Ahmet", result.get().getName());
        verify(userRepository, times(1)).findByTcNumber("12345678901");
    }
    @Test
    void testAuthenticateUser_Success() {
        // Giriş isteği oluştur
        AuthenticationRequest authRequest = new AuthenticationRequest();
        authRequest.setTcNumber("12345678901");
        authRequest.setPhoneNumber("5551234567");
        authRequest.setPassword("password");

        // Mock kullanıcı belgesi
        UserDocument mockUser = new UserDocument();
        mockUser.setId("1");
        mockUser.setTcNumber("12345678901");
        mockUser.setPhoneNumber("5551234567");
        mockUser.setPasswordHash("encodedPassword"); // Encode edilmiş şifreyi manuel ata
        mockUser.setName("John");
        mockUser.setSurname("Doe");
        mockUser.setRole(ROLE_CUSTOMER);

        // Repository davranışını tanımla
        when(userRepository.findByTcNumberAndPhoneNumber("12345678901", "5551234567"))
                .thenReturn(Optional.of(mockUser));

        // PasswordEncoder davranışını tanımla
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        // Mapper davranışını tanımla
        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setId("1");
        mockUserDTO.setTcNumber("12345678901");
        mockUserDTO.setPhoneNumber("5551234567");
        mockUserDTO.setName("John");
        mockUserDTO.setSurname("Doe");
        mockUserDTO.setRole(ROLE_CUSTOMER);

        when(userMapper.toDto(mockUser)).thenReturn(mockUserDTO);

        // Mock HttpServletRequest
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getRemoteAddr()).thenReturn("192.168.1.1");

        // Mock LoginInfoMapper
        LoginInfoDTO mockLoginInfoDTO = new LoginInfoDTO("1", "192.168.1.1", LocalDateTime.now());
        when(loginInfoMapper.toDto(mockUser, "192.168.1.1")).thenReturn(mockLoginInfoDTO);

        // Mock LoginInfoService
        doNothing().when(loginInfoService).saveLoginInfo(mockLoginInfoDTO);

        // Test edilen metot
        Optional<UserDTO> result = userService.authenticateUser(authRequest, mockRequest);

        // Doğrulamalar
        assertTrue(result.isPresent());
        assertEquals("12345678901", result.get().getTcNumber());
        assertEquals("John", result.get().getName());
        assertEquals("Doe", result.get().getSurname());
        assertEquals(ROLE_CUSTOMER, result.get().getRole());

        // LoginInfo kaydının çağrıldığını doğrula
        verify(loginInfoService, times(1)).saveLoginInfo(mockLoginInfoDTO);
    }



    @Test
    void testAuthenticateUser_Failure() {
        // Kullanıcı bulunamadığı durum için repository davranışını mockla
        when(userRepository.findByTcNumberAndPhoneNumber("12345678901", "5551234567"))
                .thenReturn(Optional.empty());

        // Giriş isteği oluştur
        AuthenticationRequest authRequest = new AuthenticationRequest();
        authRequest.setTcNumber("12345678901");
        authRequest.setPhoneNumber("5551234567");
        authRequest.setPassword("password");

        // Mock HttpServletRequest
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);

        // Test edilen metot
        Optional<UserDTO> result = userService.authenticateUser(authRequest, mockRequest);

        // Doğrulamalar
        assertFalse(result.isPresent()); // Kullanıcı doğrulama başarısız olmalı
        verify(userRepository, times(1)).findByTcNumberAndPhoneNumber("12345678901", "5551234567"); // Repository çağrılmış olmalı
        verify(loginInfoService, never()).saveLoginInfo(any()); // LoginInfo kaydı yapılmamalı
    }


    @Test
    void testUpdateUser_Success() {
        // Mock mevcut kullanıcı
        UserDocument mockUser = new UserDocument();
        mockUser.setId("1001");
        mockUser.setName("Ahmet");
        mockUser.setSurname("Yılmaz");
        mockUser.setPhoneNumber("5551234567");

        // Güncellenmiş kullanıcı bilgisi
        UserDTO updatedDTO = new UserDTO();
        updatedDTO.setName("Mehmet");
        updatedDTO.setSurname("Demir");
        updatedDTO.setPhoneNumber("5559876543");

        // Mock güncellenmiş kullanıcı
        UserDocument updatedUser = new UserDocument();
        updatedUser.setId("1001");
        updatedUser.setName("Mehmet");
        updatedUser.setSurname("Demir");
        updatedUser.setPhoneNumber("5559876543");

        // Mock repository davranışları
        when(userRepository.findById("1001")).thenReturn(Optional.of(mockUser)); // Kullanıcıyı döndürür
        when(userRepository.save(any(UserDocument.class))).thenReturn(updatedUser); // Güncellenmiş kullanıcıyı döndürür
        when(userMapper.toDto(updatedUser)).thenReturn(updatedDTO); // DTO dönüşümü

        // Metot çağrısı
        UserDTO result = userService.updateUser("1001", updatedDTO);

        // Doğrulamalar
        assertNotNull(result);
        assertEquals("Mehmet", result.getName());
        assertEquals("Demir", result.getSurname());
        assertEquals("5559876543", result.getPhoneNumber());
        verify(userRepository, times(1)).findById("1001");
        verify(userRepository, times(1)).save(any(UserDocument.class));
    }



    @Test
    void testUpdateUser_UserNotFound() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Mehmet");

        when(userRepository.findById("1L")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser("1L", userDTO));
        verify(userRepository, times(1)).findById("1L");
    }

    @Test
    void testRequestPasswordReset_Success() {
        // Arrange
        String tcNumber = "12345678901";
        String phoneNumber = "5551234567";
        UserDocument user = new UserDocument();
        user.setTcNumber(tcNumber);
        user.setPhoneNumber(phoneNumber);

        when(userRepository.findByTcNumberAndPhoneNumber(tcNumber, phoneNumber)).thenReturn(Optional.of(user));

        // Act
        boolean result = userService.requestPasswordReset(tcNumber, phoneNumber);

        // Assert
        assertTrue(result);
        verify(userRepository, times(1)).findByTcNumberAndPhoneNumber(tcNumber, phoneNumber);
    }

    @Test
    void testRequestPasswordReset_Failure() {
        // Arrange
        String tcNumber = "12345678901";
        String phoneNumber = "5551234567";

        when(userRepository.findByTcNumberAndPhoneNumber(tcNumber, phoneNumber)).thenReturn(Optional.empty());

        // Act
        boolean result = userService.requestPasswordReset(tcNumber, phoneNumber);

        // Assert
        assertFalse(result);
        verify(userRepository, times(1)).findByTcNumberAndPhoneNumber(tcNumber, phoneNumber);
    }

    @Test
    void testResetPassword_Success() {
        // Arrange
        String tcNumber = "12345678901";
        String newPassword = "newPassword123";
        UserDocument user = new UserDocument();
        user.setTcNumber(tcNumber);

        when(userRepository.findByTcNumber(tcNumber)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(newPassword)).thenReturn("hashedPassword");

        // Act
        boolean result = userService.resetPassword(tcNumber, newPassword);

        // Assert
        assertTrue(result);
        verify(userRepository, times(1)).findByTcNumber(tcNumber);
        verify(userRepository, times(1)).save(user);
        assertEquals("hashedPassword", user.getPasswordHash());
    }

    @Test
    void testResetPassword_Failure() {
        // Arrange
        String tcNumber = "12345678901";
        String newPassword = "newPassword123";

        when(userRepository.findByTcNumber(tcNumber)).thenReturn(Optional.empty());

        // Act
        boolean result = userService.resetPassword(tcNumber, newPassword);

        // Assert
        assertFalse(result);
        verify(userRepository, times(1)).findByTcNumber(tcNumber);
        verify(userRepository, never()).save(any(UserDocument.class));
    }

}
