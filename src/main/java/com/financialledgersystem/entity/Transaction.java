package com.financialledgersystem.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.financialledgersystem.enums.TransactionType;

import jakarta.persistence.*;

@Entity
@Table(name = "transactions")

public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String transactionId;

	@ManyToOne
	@JoinColumn(name = "account_id")
	private LedgerAccount ledgerAccount;

	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;

	@Column(nullable = false)
	private BigDecimal amount;

	private String description;
	
	private BigDecimal balanceAfterTransaction;


	private LocalDateTime createdAt;

	@PrePersist
	public void prePersist() {
		createdAt = LocalDateTime.now();
	}

	public Transaction() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public LedgerAccount getLedgerAccount() {
		return ledgerAccount;
	}
	
	public void setLedgerAccount(LedgerAccount ledgerAccount) {
		this.ledgerAccount = ledgerAccount;
	}

	public BigDecimal getBalanceAfterTransaction() {
		return balanceAfterTransaction;
	}

	public void setBalanceAfterTransaction(BigDecimal balanceAfterTransaction) {
		this.balanceAfterTransaction = balanceAfterTransaction;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public LedgerAccount getAccount() {
		return ledgerAccount;
	}

	public void setAccount(LedgerAccount account) {
		this.ledgerAccount = account;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
 
}
