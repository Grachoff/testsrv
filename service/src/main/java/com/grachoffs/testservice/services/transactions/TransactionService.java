package com.grachoffs.testservice.services.transactions;

import dtos.transactions.TransactionDto;

import java.util.List;

public interface TransactionService {
    void save(List<TransactionDto> transactionsForProcessing);
}
