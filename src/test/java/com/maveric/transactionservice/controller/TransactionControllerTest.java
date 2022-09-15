package com.maveric.transactionservice.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maveric.transactionservice.constants.Type;
import com.maveric.transactionservice.dto.TransactionDto;
import com.maveric.transactionservice.repository.TransactionRepository;
import com.maveric.transactionservice.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.ws.rs.core.MediaType;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    ObjectMapper map;
    @MockBean
    private TransactionService mockService;
    @MockBean
    private TransactionRepository mockRepo;


    @Test
    void shouldGetTransactionsWhenRequestMadeToGetTransactions() throws Exception {
        mvc.perform(get("api/v1/accounts/"+"12"+"/transactions").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void ShouldCreateTransactionWhenPostTransactionIsInvoked() throws Exception {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.set_id("1");
        transactionDto.setAccountId("12");
        transactionDto.setType(Type.CREDIT);
        transactionDto.setAmount(500);
        transactionDto.setCreatedAt(LocalDateTime.now());

        mvc.perform(post("/api/v1/accounts/" + "1" + "/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(map.writeValueAsBytes(transactionDto)))
                .andExpect(status().isCreated())
                .andDo(print());

    }

    @Test
    void shouldGetTransactionDetailsWhenGetTransactionByIdInvoked() throws Exception {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.set_id("1");
        transactionDto.setAccountId("12");
        transactionDto.setAmount(500);
        transactionDto.setType(Type.CREDIT);
        transactionDto.setCreatedAt(LocalDateTime.now());

        String url = "/api/v1/accounts/" + "12" + "/transactions/" + "1";
        mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void ShouldDeleteAccountWhenDeleteTransactionByIdRequestIsMade() throws Exception {

        mvc.perform(delete("/api/v1/accounts/" + "13" + "/transactions/" + "12").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    void shouldgetMethodNotAllowedForIncorrectUrl() throws Exception {

        mvc.perform(delete("/api/v1/accounts/" + "10 "+ "/transactions/"))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }
    private String mapToJson(Object object) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
