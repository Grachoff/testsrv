package com.grachoffs.testservice.services.transactions;

import common.RestResult;
import dtos.transactions.TransactionDto;

public interface TransactionProcessorService {
    RestResult receiveData(TransactionDto transactionDto);
}
