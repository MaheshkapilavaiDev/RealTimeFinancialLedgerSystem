package com.financialledgersystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financialledgersystem.entity.LedgerAccount;

@Repository
public interface LedgerAccountRepository extends JpaRepository<LedgerAccount, Long> {

	Optional<LedgerAccount> findByAccountNumber(String accountNumber);
	
	boolean existsByAccountNumber(String accountNumber);

}
