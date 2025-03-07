package com.devbank.accounting.rest;

import com.devbank.accounting.api.DTO.AccountDTO;
import com.devbank.accounting.api.enums.AccountType;
import com.devbank.accounting.api.service.AccountService;
import com.devbank.accounting.rest.controller.AccountController;
import com.devbank.error.management.exception.AccountNotFoundException;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
@Import(SecurityConfig.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    @WithMockUser(username = "testUser", roles = {"CUSTOMER"})
    void testCreateAccount_Success() throws Exception {
        AccountDTO accountDTO = new AccountDTO("1", "1001", AccountType.CURRENT, 5000.0, "TR1234567891234567", new Date(), null, null);

        when(accountService.createAccount(any(AccountDTO.class))).thenReturn(accountDTO);

        String accountJson = """
        {
            "accountId": "1",
            "customerId": "1001",
            "accountType": "CURRENT",
            "balance": 5000.0,
            "uniqueAccountNumber": "TR1234567891234567",
            "createdAt": "2024-12-20T15:00:00.000+00:00"
        }
        """;

        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value("1"))
                .andExpect(jsonPath("$.uniqueAccountNumber").value("TR1234567891234567"));
    }

    @Test
    void testCreateAccount_InvalidInput() throws Exception {
        String invalidRequest = """
        {
            "accountId": "1",
            "customerId": "1001",
            "accountType": "CURRENT",
            "balance": 5000.0,
            "uniqueAccountNumber": "TR1234567891234567",
            "createdAt": null
        }
        """;

        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details").exists());
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"CUSTOMER"})
    void testGetAccountById_Success() throws Exception {
        AccountDTO accountDTO = new AccountDTO("1", "1001", AccountType.CURRENT, 5000.0, "TR1234567891234567", new Date(), null, null);

        when(accountService.getAccountById("1")).thenReturn(accountDTO);

        mockMvc.perform(get("/api/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value("1"))
                .andExpect(jsonPath("$.uniqueAccountNumber").value("TR1234567891234567"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"CUSTOMER"})
    void testGetAccountById_NotFound() throws Exception {
        when(accountService.getAccountById("1")).thenThrow(new AccountNotFoundException("Account not found"));

        mockMvc.perform(get("/api/accounts/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Account not found"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"CUSTOMER"})
    void testUpdateAccount_Success() throws Exception {
        AccountDTO accountDTO = new AccountDTO("1", "1001", AccountType.CURRENT, 6000.0, "TR1234567891234567", new Date(), null, null);

        when(accountService.updateAccount(eq("1"), any(AccountDTO.class))).thenReturn(accountDTO);

        String accountJson = """
        {
            "accountId": "1",
            "customerId": "1001",
            "accountType": "CURRENT",
            "balance": 6000.0,
            "uniqueAccountNumber": "TR1234567891234567",
            "createdAt": "2024-12-20T15:00:00.000+00:00"
        }
        """;

        mockMvc.perform(put("/api/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(6000.0))
                .andExpect(jsonPath("$.uniqueAccountNumber").value("TR1234567891234567"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"CUSTOMER"})
    void testUpdateAccount_NotFound() throws Exception {
        when(accountService.updateAccount(eq("1"), any(AccountDTO.class)))
                .thenThrow(new AccountNotFoundException("Account not found"));

        String accountJson = """
        {
            "accountId": "1",
            "customerId": "1001",
            "accountType": "CURRENT",
            "balance": 6000.0,
            "uniqueAccountNumber": "TR1234567891234567",
            "createdAt": "2024-12-20T15:00:00.000+00:00"
        }
        """;

        mockMvc.perform(put("/api/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Account not found"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"CUSTOMER"})
    void testDeleteAccount_Success() throws Exception {
        mockMvc.perform(delete("/api/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Account deleted successfully."));
    }

    @Test
    void testDeleteAccount_NotFound() throws Exception {
        doThrow(new AccountNotFoundException("Account not found"))
                .when(accountService).deleteAccount("1");

        mockMvc.perform(delete("/api/accounts/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Account not found"));
    }
}
