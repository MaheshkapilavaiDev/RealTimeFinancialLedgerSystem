package com.financialledgersystem.dto;

import java.math.BigDecimal;

public class LedgerAccountResponse {

	private Long id;
	private String accountNumber;
	private String accountName;
	private BigDecimal balance;

	public LedgerAccountResponse() {
	}

	public LedgerAccountResponse(Long id, String accountNumber, String accountName, BigDecimal balance) {

		this.id = id;
		this.accountNumber = accountNumber;
		this.accountName = accountName;
		this.balance = balance;
	}

	public Long getId() {
		return id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getAccountName() {
		return accountName;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
}