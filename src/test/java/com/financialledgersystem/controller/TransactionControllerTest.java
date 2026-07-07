package com.financialledgersystem.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.financialledgersystem.dto.CreditRequest;
import com.financialledgersystem.dto.DebitRequest;
import com.financialledgersystem.dto.TransactionResponse;
import com.financialledgersystem.dto.TransferRequest;
import com.financialledgersystem.service.TransactionService;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    void creditAmount() throws Exception {

        CreditRequest request = new CreditRequest();
        request.setAccountId(1L);
        request.setAmount(new BigDecimal("5000"));
        request.setDescription("Salary");

        TransactionResponse response = new TransactionResponse();
        response.setTransactionId("TXN123");
        response.setTransactionType("CREDIT");
        response.setAmount(new BigDecimal("5000"));
        response.setBalance(new BigDecimal("15000"));
        response.setAccountNumber("ACC1001");
        response.setDescription("Salary");
        response.setCreatedAt(LocalDateTime.now());

        when(transactionService.creditAmount(any())).thenReturn(response);

        mockMvc.perform(post("/api/transactions/credit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionType").value("CREDIT"));
    }

    @Test
    void debitAmount() throws Exception {

        DebitRequest request = new DebitRequest();
        request.setAccountId(1L);
        request.setAmount(new BigDecimal("2000"));
        request.setDescription("Shopping");

        TransactionResponse response = new TransactionResponse();
        response.setTransactionId("TXN456");
        response.setTransactionType("DEBIT");
        response.setAmount(new BigDecimal("2000"));
        response.setBalance(new BigDecimal("8000"));
        response.setAccountNumber("ACC1001");
        response.setDescription("Shopping");
        response.setCreatedAt(LocalDateTime.now());

        when(transactionService.debitAmount(any())).thenReturn(response);

        mockMvc.perform(post("/api/transactions/debit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionType").value("DEBIT"));
    }

    @Test
    void transferFunds() throws Exception {

        TransferRequest request = new TransferRequest();
        request.setFromAccountId(1L);
        request.setToAccountId(2L);
        request.setAmount(new BigDecimal("3000"));

        when(transactionService.transferFunds(any()))
                .thenReturn("Fund Transfer Successful");

        mockMvc.perform(post("/api/transactions/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Fund Transfer Successful"));
    }
}