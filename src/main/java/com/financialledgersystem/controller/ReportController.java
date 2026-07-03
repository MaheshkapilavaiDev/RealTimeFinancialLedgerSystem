package com.financialledgersystem.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.financialledgersystem.dto.TransactionReportResponse;
import com.financialledgersystem.service.TransactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

	@Autowired
    private  TransactionService transactionService;

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionReportResponse>> getAccountStatement(
            @PathVariable Long accountId) {

        return ResponseEntity.ok(
                transactionService.getAccountStatement(accountId));
    }

    @GetMapping("/credits")
    public ResponseEntity<List<TransactionReportResponse>> getCredits() {

        return ResponseEntity.ok(
                transactionService.getCreditTransactions());
    }

    @GetMapping("/debits")
    public ResponseEntity<List<TransactionReportResponse>> getDebits() {

        return ResponseEntity.ok(
                transactionService.getDebitTransactions());
    }

    @GetMapping("/between")
    public ResponseEntity<List<TransactionReportResponse>> getBetween(
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to) {

        return ResponseEntity.ok(
                transactionService.getTransactionsBetween(from, to));
    }
}
