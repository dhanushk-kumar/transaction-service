package com.maveric.transactionservice.service;

import com.maveric.transactionservice.dto.TransactionDto;

import java.util.List;

public interface TransactionService {
    public TransactionDto createTransaction(TransactionDto transaction);
    public List<TransactionDto> getTransactions();
}
