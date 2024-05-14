package com.example.cqrs.api.controller;

import com.example.cqrs.api.domain.TransactionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deposit() throws Exception {
        // Given
        BigDecimal amount = BigDecimal.valueOf(100);
        TransactionRequest request = new TransactionRequest();
        request.setAmount(amount);

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/transactions/deposit/{accountId}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isAccepted())
                .andReturn();


    }

    @Test
    void badRequest() throws Exception {

        TransactionRequest request = new TransactionRequest();
        request.setAmount(null);

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/transactions/deposit/{accountId}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void withdraw() throws Exception {
        // Given
        BigDecimal amount = BigDecimal.valueOf(50);
        TransactionRequest request = new TransactionRequest();
        request.setAmount(amount);

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/transactions/withdraw/{accountId}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isAccepted())
                .andReturn();


    }

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}

