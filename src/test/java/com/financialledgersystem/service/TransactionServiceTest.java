package com.financialledgersystem.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.financialledgersystem.dto.CreditRequest;
import com.financialledgersystem.dto.DebitRequest;
import com.financialledgersystem.dto.TransferRequest;
import com.financialledgersystem.entity.LedgerAccount;
import com.financialledgersystem.entity.Transaction;
import com.financialledgersystem.repository.LedgerAccountRepository;
import com.financialledgersystem.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private LedgerAccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private KafkaProducerService producer;

    @InjectMocks
    private TransactionService transactionService;

    private LedgerAccount account;

    @BeforeEach
    void setUp() {

        account = new LedgerAccount();

        account.setId(1L);
        account.setAccountNumber("ACC1001");
        account.setAccountName("Cash Account");
        account.setBalance(new BigDecimal("10000"));
    }

    @Test
    void creditAmount_ShouldCreditSuccessfully() {

        CreditRequest request = new CreditRequest();
        request.setAccountId(1L);
        request.setAmount(new BigDecimal("5000"));
        request.setDescription("Salary");

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(i -> i.getArgument(0));

        transactionService.creditAmount(request);

        assertEquals(new BigDecimal("15000"), account.getBalance());

        verify(accountRepository).save(account);
        verify(transactionRepository).save(any(Transaction.class));
        verify(producer).publish(any());
    }

    @Test
    void creditAmount_ShouldThrowException_WhenAccountNotFound() {

        CreditRequest request = new CreditRequest();

        request.setAccountId(10L);

        when(accountRepository.findById(10L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> transactionService.creditAmount(request));

        assertEquals("Account Not Found", ex.getMessage());
    }

    @Test
    void debitAmount_ShouldDebitSuccessfully() {

        DebitRequest request = new DebitRequest();

        request.setAccountId(1L);
        request.setAmount(new BigDecimal("3000"));

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(i -> i.getArgument(0));

        transactionService.debitAmount(request);

        assertEquals(new BigDecimal("7000"), account.getBalance());

        verify(accountRepository).save(account);
        verify(transactionRepository).save(any(Transaction.class));
        verify(producer).publish(any());
    }

    @Test
    void debitAmount_ShouldThrowException_WhenBalanceInsufficient() {

        DebitRequest request = new DebitRequest();

        request.setAccountId(1L);
        request.setAmount(new BigDecimal("20000"));

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> transactionService.debitAmount(request));

        assertEquals("Insufficient Balance", ex.getMessage());
    }

    @Test
    void debitAmount_ShouldThrowException_WhenAccountMissing() {

        DebitRequest request = new DebitRequest();

        request.setAccountId(100L);

        when(accountRepository.findById(100L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> transactionService.debitAmount(request));

        assertEquals("Account Not Found", ex.getMessage());
    }

    @Test
    void transferFunds_ShouldTransferSuccessfully() {

        LedgerAccount to = new LedgerAccount();

        to.setId(2L);
        to.setAccountNumber("ACC2001");
        to.setBalance(new BigDecimal("5000"));

        TransferRequest request = new TransferRequest();

        request.setFromAccountId(1L);
        request.setToAccountId(2L);
        request.setAmount(new BigDecimal("4000"));

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(accountRepository.findById(2L))
                .thenReturn(Optional.of(to));

        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(i -> i.getArgument(0));

        String result = transactionService.transferFunds(request);

        assertEquals("Fund Transfer Successful", result);

        assertEquals(new BigDecimal("6000"), account.getBalance());
        assertEquals(new BigDecimal("9000"), to.getBalance());

        verify(accountRepository, times(2)).save(any());
        verify(transactionRepository, times(2)).save(any());
        verify(producer, times(2)).publish(any());
    }

    @Test
    void transferFunds_ShouldThrowException_WhenSourceMissing() {

        TransferRequest request = new TransferRequest();

        request.setFromAccountId(10L);
        request.setToAccountId(2L);

        when(accountRepository.findById(10L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> transactionService.transferFunds(request));

        assertEquals("Source Account Not Found", ex.getMessage());
    }

    @Test
    void transferFunds_ShouldThrowException_WhenDestinationMissing() {

        TransferRequest request = new TransferRequest();

        request.setFromAccountId(1L);
        request.setToAccountId(2L);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(accountRepository.findById(2L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> transactionService.transferFunds(request));

        assertEquals("Destination Account Not Found", ex.getMessage());
    }

    @Test
    void transferFunds_ShouldThrowException_WhenSameAccount() {

        TransferRequest request = new TransferRequest();

        request.setFromAccountId(1L);
        request.setToAccountId(1L);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> transactionService.transferFunds(request));

        assertEquals("Cannot transfer to same account", ex.getMessage());
    }

    @Test
    void transferFunds_ShouldThrowException_WhenInsufficientBalance() {

        LedgerAccount to = new LedgerAccount();

        to.setId(2L);
        to.setBalance(new BigDecimal("1000"));

        TransferRequest request = new TransferRequest();

        request.setFromAccountId(1L);
        request.setToAccountId(2L);
        request.setAmount(new BigDecimal("50000"));

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(accountRepository.findById(2L))
                .thenReturn(Optional.of(to));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> transactionService.transferFunds(request));

        assertEquals("Insufficient Balance", ex.getMessage());
    }

}
