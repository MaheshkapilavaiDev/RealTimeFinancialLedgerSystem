package com.financialledgersystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.financialledgersystem.dto.CreditRequest;
import com.financialledgersystem.dto.DebitRequest;
import com.financialledgersystem.dto.TransactionResponse;
import com.financialledgersystem.dto.TransferRequest;
import com.financialledgersystem.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/transactions")
@Validated
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    
    @PostMapping("/credit")
    public ResponseEntity<TransactionResponse> creditAmount(
            @Valid @RequestBody CreditRequest request) {

        TransactionResponse response =
                transactionService.creditAmount(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    
    @PostMapping("/debit")
    public ResponseEntity<TransactionResponse> debitAmount(
            @Valid @RequestBody DebitRequest request) {

        TransactionResponse response =
                transactionService.debitAmount(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    
    @PostMapping("/transfer")
    public ResponseEntity<String> transferFunds(
            @Valid @RequestBody TransferRequest request) {

        String response =
                transactionService.transferFunds(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}