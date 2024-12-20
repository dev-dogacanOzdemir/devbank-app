package com.devbank.user.management.rest;

import com.devbank.error.management.exception.UserNotFoundException;
import com.devbank.user.management.api.DTO.LoginInfoDTO;
import com.devbank.user.management.api.DTO.UserDTO;
import com.devbank.user.management.api.enums.Role;
import com.devbank.user.management.api.service.LoginInfoService;
import com.devbank.user.management.api.service.UserService;
import com.devbank.user.management.rest.config.SecurityConfig;
import com.devbank.user.management.rest.controller.UserController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
public class UserDocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private LoginInfoService loginInfoService;

    @Test
    @WithMockUser(username = "testUser", roles = {"CUSTOMER"})
    void testRegisterUser_Success() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Ahmet");
        userDTO.setSurname("Yılmaz");
        userDTO.setTcNumber("12345678901");
        userDTO.setPhoneNumber("5551234567");
        userDTO.setRole(Role.CUSTOMER);

        when(userService.registerUser(any(UserDTO.class))).thenReturn(userDTO);

        String userJson = """
                {
                    "name": "Ahmet",
                    "surname": "Yılmaz",
                    "tcNumber": "12345678901",
                    "phoneNumber": "5551234567",
                    "role": "CUSTOMER",
                    "password": "1234"
                }
                """;

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ahmet"))
                .andExpect(jsonPath("$.tcNumber").value("12345678901"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"CUSTOMER"})
    void testRegisterUser_BadRequest() throws Exception {
        String userJson = """
            {
                "name": "Ahmet",
                "surname": "Yılmaz"
            }
            """;

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest());
    }


    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testGetUserByTcNumber_Success() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Ahmet");
        userDTO.setSurname("Yılmaz");
        userDTO.setTcNumber("12345678901");
        userDTO.setPhoneNumber("5551234567");
        userDTO.setRole(Role.CUSTOMER);

        when(userService.findByTcNumber("12345678901")).thenReturn(Optional.of(userDTO));

        mockMvc.perform(get("/api/users/12345678901"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ahmet"))
                .andExpect(jsonPath("$.tcNumber").value("12345678901"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testGetUserByTcNumber_NotFound() throws Exception {
        when(userService.findByTcNumber("12345678901")).thenThrow(new UserNotFoundException("User not found with T.C. Number : 12345678901"));

        mockMvc.perform(get("/api/users/12345678901"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found with T.C. Number : 12345678901"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testUpdateUser_Success() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("Mehmet");
        userDTO.setSurname("Yılmaz");
        userDTO.setPhoneNumber("5559876543");

        when(userService.updateUser(eq(1L), any(UserDTO.class))).thenReturn(userDTO);

        String userJson = """
                {
                    "name": "Mehmet",
                    "surname": "Yılmaz",
                    "phoneNumber": "5559876543"
                }
                """;

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mehmet"))
                .andExpect(jsonPath("$.phoneNumber").value("5559876543"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testUpdateUser_NotFound() throws Exception {
        when(userService.updateUser(eq(1L), any(UserDTO.class))).thenThrow(new UserNotFoundException("User not found"));

        String userJson = """
            {
                "name": "Mehmet",
                "surname": "Yılmaz",
                "phoneNumber": "5559876543"
            }
            """;

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    void testRequestPasswordReset_Success() throws Exception {
        when(userService.requestPasswordReset(eq("12345678901"), eq("5551234567"))).thenReturn(true);

        mockMvc.perform(post("/api/users/password-reset/request")
                        .param("tcNumber", "12345678901")
                        .param("phoneNumber", "5551234567"))
                .andExpect(status().isOk())
                .andExpect(content().string("Password reset request successful. Please check your phone."));
    }

    @Test
    void testRequestPasswordReset_Failure() throws Exception {
        when(userService.requestPasswordReset(eq("12345678901"), eq("5551234567"))).thenReturn(false);

        mockMvc.perform(post("/api/users/password-reset/request")
                        .param("tcNumber", "12345678901")
                        .param("phoneNumber", "5551234567"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid credentials."));
    }

    @Test
    void testResetPassword_Success() throws Exception {
        when(userService.resetPassword(eq("12345678901"), eq("newPassword123"))).thenReturn(true);

        mockMvc.perform(post("/api/users/password-reset/confirm")
                        .param("tcNumber", "12345678901")
                        .param("newPassword", "newPassword123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Password successfully reset."));
    }

    @Test
    void testResetPassword_Failure() throws Exception {
        when(userService.resetPassword(eq("12345678901"), eq("newPassword123"))).thenReturn(false);

        mockMvc.perform(post("/api/users/password-reset/confirm")
                        .param("tcNumber", "12345678901")
                        .param("newPassword", "newPassword123"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Failed to reset password."));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"CUSTOMER"})
    void testGetLoginInfo_Success() throws Exception {
        // Test verisi
        List<LoginInfoDTO> loginInfoList = List.of(
                new LoginInfoDTO("192.168.1.1", new Date()),
                new LoginInfoDTO("192.168.1.2", new Date())
        );

        // Mock davranışını tanımla
        when(loginInfoService.getLoginInfoByUserId(1L)).thenReturn(loginInfoList);

        // GET isteğini gerçekleştir ve sonucu doğrula
        mockMvc.perform(get("/api/users/login-info/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2)) // JSON array uzunluğu
                .andExpect(jsonPath("$[0].ipAddress").value("192.168.1.1")) // İlk öğe kontrolü
                .andExpect(jsonPath("$[1].ipAddress").value("192.168.1.2")); // İkinci öğe kontrolü
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"CUSTOMER"})
    void testGetLoginInfo_NotFound() throws Exception {
        // Mock davranışını tanımla
        when(loginInfoService.getLoginInfoByUserId(99L)).thenReturn(List.of());

        // GET isteğini gerçekleştir ve sonucu doğrula
        mockMvc.perform(get("/api/users/login-info/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Not found değil, boş liste dönüyor
                .andExpect(jsonPath("$.length()").value(0)); // JSON array uzunluğu 0 olmalı
    }


}
