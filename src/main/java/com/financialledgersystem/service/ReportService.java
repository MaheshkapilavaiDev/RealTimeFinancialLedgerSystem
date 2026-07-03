package com.financialledgersystem.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.financialledgersystem.dto.TransactionReportResponse;
import com.financialledgersystem.entity.Transaction;
import com.financialledgersystem.enums.TransactionType;
import com.financialledgersystem.exception.ResourceNotFoundException;
import com.financialledgersystem.repository.LedgerAccountRepository;
import com.financialledgersystem.repository.TransactionRepository;

@Service
public class ReportService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private LedgerAccountRepository ledgerAccountRepository;

    
    public List<TransactionReportResponse> getAccountStatement(Long accountId) {

        ledgerAccountRepository.findById(accountId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account Not Found"));

        List<Transaction> transactions =
                transactionRepository.findByLedgerAccountId(accountId);

        return transactions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    
    public List<TransactionReportResponse> getCreditTransactions() {

        List<Transaction> transactions =
                transactionRepository.findByTransactionType(TransactionType.CREDIT);

        return transactions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    
    public List<TransactionReportResponse> getDebitTransactions() {

        List<Transaction> transactions =
                transactionRepository.findByTransactionType(TransactionType.DEBIT);

        return transactions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    
    public List<TransactionReportResponse> getTransactionsBetween(
            LocalDateTime from,
            LocalDateTime to) {

        List<Transaction> transactions =
                transactionRepository.findByCreatedAtBetween(from, to);

        return transactions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    
    private TransactionReportResponse mapToResponse(Transaction transaction) {

        TransactionReportResponse response =
                new TransactionReportResponse();

        response.setTransactionId(transaction.getTransactionId());

        response.setTransactionType(transaction.getTransactionType());

       // response.setAccountamount(transaction.getAmount());

        response.setTransactionDate(transaction.getCreatedAt());

        response.setAccountId(
                transaction.getAccount().getId());

        response.setAccountName(
                transaction.getAccount().getAccountName());

        response.setAccountNumber(
                transaction.getAccount().getAccountNumber());

        response.setCurrentBalance(transaction.getAccount().getBalance());
       
        return response;
    }

}