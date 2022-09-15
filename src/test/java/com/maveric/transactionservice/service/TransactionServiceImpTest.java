package com.maveric.transactionservice.service;
import com.maveric.transactionservice.constants.Type;
import com.maveric.transactionservice.dto.TransactionDto;
import com.maveric.transactionservice.model.Transaction;
import com.maveric.transactionservice.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.maveric.transactionservice.constants.Type.CREDIT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImpTest {
    @Mock
    private TransactionRepository transactionRepository;
    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void shouldReturnTransactionWhenCreateAccountInvoked() {

        when(transactionRepository.save(any())).thenReturn(getTransaction());

        TransactionDto transaction = transactionService.createTransaction(getTransactionDto());

        assertNotNull(transaction);
        assertSame(transaction.getType(),getTransaction().getType());
    }
    @Test
    void ShouldReturnTransactionDetailswhenTransactionByIdInvoked() {
        when(transactionRepository.findById(any(String.class))).thenReturn(Optional.of(getTransaction()));

        TransactionDto transaction = transactionService.getTransactionById("123");

        assertNotNull(transaction);
        assertSame(transaction.getType(), getTransaction().getType());
    }
    @Test
    void shouldDeleteTransactionwhenDeleteTransactionInvoked(){

        transactionRepository.deleteById("123");
        verify(transactionRepository,atLeastOnce()).deleteById("123");
    }
    @Test
    void shouldReturnTransactionswhenAccountsnotemptyindb(){
        List<Transaction> transaction = new ArrayList<Transaction>();
        transaction.add(getTransaction());
        when(transactionRepository.findAll()).thenReturn(transaction);
        assertFalse(transactionService.getTransactions().isEmpty());

    }

    private  Transaction getTransaction() {
        Transaction transaction = new Transaction();
        transaction.set_id("1");
        transaction.setAccountId("233");
        transaction.setAmount(100);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setType(CREDIT);
        return transaction;
    }

    private  TransactionDto getTransactionDto() {
        TransactionDto transaction = new TransactionDto();
        transaction.set_id("1");
        transaction.setAccountId("233");
        transaction.setAmount(100);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setType(CREDIT);
        return transaction;
    }
}
