package com.financialledgersystem.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financialledgersystem.audit.Audit;
import com.financialledgersystem.dto.CreditRequest;
import com.financialledgersystem.dto.DebitRequest;
import com.financialledgersystem.dto.TransactionReportResponse;
import com.financialledgersystem.dto.TransactionResponse;
import com.financialledgersystem.dto.TransferRequest;
import com.financialledgersystem.entity.LedgerAccount;
import com.financialledgersystem.entity.Transaction;
import com.financialledgersystem.enums.TransactionType;
import com.financialledgersystem.repository.LedgerAccountRepository;
import com.financialledgersystem.repository.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private LedgerAccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    
    @Transactional
    @Audit(operation = "CREDIT")
    public TransactionResponse creditAmount(CreditRequest request) {

        LedgerAccount account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account Not Found"));

        account.setBalance(
                account.getBalance().add(request.getAmount()));

        accountRepository.save(account);

        Transaction transaction = saveTransaction(
                account,
                TransactionType.CREDIT,
                request.getAmount(),
                request.getDescription());

        return mapToResponse(transaction, account);
    }

    
    @Transactional
    public TransactionResponse debitAmount(DebitRequest request) {

        LedgerAccount account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account Not Found"));

        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient Balance");
        }

        account.setBalance(
                account.getBalance().subtract(request.getAmount()));

        accountRepository.save(account);

        Transaction transaction = saveTransaction(
                account,
                TransactionType.DEBIT,
                request.getAmount(),
                request.getDescription());

        return mapToResponse(transaction, account);
    }

    
    @Transactional
    @Audit(operation = "TRANSFER")
    public String transferFunds(TransferRequest request) {

        LedgerAccount fromAccount = accountRepository.findById(request.getFromAccountId())
                .orElseThrow(() -> new RuntimeException("Source Account Not Found"));

        LedgerAccount toAccount = accountRepository.findById(request.getToAccountId())
                .orElseThrow(() -> new RuntimeException("Destination Account Not Found"));

        if (fromAccount.getId().equals(toAccount.getId())) {
            throw new RuntimeException("Cannot transfer to same account");
        }

        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient Balance");
        }

        fromAccount.setBalance(
                fromAccount.getBalance().subtract(request.getAmount()));

        toAccount.setBalance(
                toAccount.getBalance().add(request.getAmount()));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        saveTransaction(
                fromAccount,
                TransactionType.TRANSFER,
                request.getAmount(),
                "Transferred To " + toAccount.getAccountNumber());

        saveTransaction(
                toAccount,
                TransactionType.TRANSFER,
                request.getAmount(),
                "Received From " + fromAccount.getAccountNumber());

        return "Fund Transfer Successful";
    }

    
    
    private Transaction saveTransaction(
            LedgerAccount account,
            TransactionType type,
            BigDecimal amount,
            String description) {

        Transaction transaction = new Transaction();

        transaction.setTransactionId(generateTransactionId());
        transaction.setAccount(account);
        transaction.setTransactionType(type);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setBalanceAfterTransaction(account.getBalance());

        return transactionRepository.save(transaction);
    }

    
    private TransactionResponse mapToResponse(
            Transaction transaction,
            LedgerAccount account) {

        TransactionResponse response = new TransactionResponse();

        response.setTransactionId(transaction.getTransactionId());
        response.setTransactionType(
                transaction.getTransactionType().name());
        response.setAccountNumber(account.getAccountNumber());
        response.setAmount(transaction.getAmount());
        response.setBalance(account.getBalance());
        response.setDescription(transaction.getDescription());
        response.setCreatedAt(transaction.getCreatedAt());

        return response;
    }

    /*
     * UNIQUE TRANSACTION ID
     */
    private String generateTransactionId() {

        return "TXN-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();
    }
    
    public List<TransactionReportResponse> getAccountStatement(Long accountId) {

        List<Transaction> transactions =
                transactionRepository.findByLedgerAccountId(accountId);

        return transactions.stream()
                .map(this::mapToReportResponse)
                .toList();
    }
    
    public List<TransactionReportResponse> getCreditTransactions() {

        List<Transaction> transactions =
                transactionRepository.findByTransactionType(TransactionType.CREDIT);

        return transactions.stream()
                .map(this::mapToReportResponse)
                .toList();
    }
    
    public List<TransactionReportResponse> getDebitTransactions() {

        List<Transaction> transactions =
                transactionRepository.findByTransactionType(TransactionType.DEBIT);

        return transactions.stream()
                .map(this::mapToReportResponse)
                .toList();
    }
    
    public List<TransactionReportResponse> getTransactionsBetween(
            LocalDateTime from,
            LocalDateTime to) {

        List<Transaction> transactions =
                transactionRepository.findByCreatedAtBetween(from, to);

        return transactions.stream()
                .map(this::mapToReportResponse)
                .toList();
    }
    
    private TransactionReportResponse mapToReportResponse(Transaction transaction) {

    	TransactionReportResponse response = new TransactionReportResponse();

    	response.setTransactionId(transaction.getTransactionId());

    	response.setTransactionType(transaction.getTransactionType());

    	// ✅ Set transaction amount

    	response.setAmount(transaction.getAmount());

    	response.setTransactionDate(transaction.getCreatedAt());

    	response.setAccountNumber(

    	transaction.getAccount().getAccountNumber());

    	response.setAccountName(

    	transaction.getAccount().getAccountName());

    	response.setAccountId(

    	transaction.getAccount().getId());

    	// ⚠️ This is CURRENT account balance

    	response.setCurrentBalance(
    	        transaction.getBalanceAfterTransaction());

    	return response;

    	}

}