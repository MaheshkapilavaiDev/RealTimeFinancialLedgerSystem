package com.financialledgersystem.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LedgerAccountRequest {

	@NotBlank(message = "Account Number is required")
	private String accountNumber;

	@NotBlank(message = "Account Name is required")
	private String accountName;

	@NotNull(message = "Initial Balance is required")
	@DecimalMin(value = "0.0", inclusive = true)
	private BigDecimal balance;

	public LedgerAccountRequest() {
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

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
}
