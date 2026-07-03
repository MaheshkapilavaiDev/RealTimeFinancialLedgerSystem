package com.financialledgersystem.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.financialledgersystem.enums.TransactionType;

public class TransactionReportResponse {

	private String transactionId;
	private TransactionType transactionType;
	private BigDecimal amount;
	private LocalDateTime transactionDate;

	private Long accountId;
	private String accountNumber;
	private String accountName;
    private BigDecimal currentBalance;

	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}

	public BigDecimal getAccountamount() {
		return currentBalance;
	}

	public void setAccountamount(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}

	public TransactionReportResponse() {
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
	    this.transactionType = transactionType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	

}
