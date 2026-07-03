package com.financialledgersystem.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financialledgersystem.entity.Transaction;
import com.financialledgersystem.enums.TransactionType;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	//List<Transaction> findByAccountId(Long accountId);
	
	//List<Transaction> findByTransactionId(String transactionId);
	
	List<Transaction> findByLedgerAccountId(Long accountId);
	

	List<Transaction> findByTransactionType(TransactionType transactionType);

    List<Transaction> findByCreatedAtBetween(
            LocalDateTime from,
            LocalDateTime to);
	

}
