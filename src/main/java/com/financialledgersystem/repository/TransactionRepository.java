package com.financialledgersystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financialledgersystem.entity.LedgerAccount;
import com.financialledgersystem.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	List<Transaction> findByLedgerAccount(LedgerAccount account);

}
