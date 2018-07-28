package com.grachoffs.testservice.controllers;

import com.grachoffs.testservice.controllers.common.BaseController;
import com.grachoffs.testservice.services.transactions.TransactionProcessorService;
import common.RestResult;
import dtos.transactions.TransactionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/transaction")
@Slf4j
public class TransactionController extends BaseController {
    @Autowired
    TransactionProcessorService transactionProcessorService;

    @PostMapping
    public RestResult receiveData(@Valid @RequestBody TransactionDto transactionDto) {
        return transactionProcessorService.receiveData(transactionDto);
    }
}
