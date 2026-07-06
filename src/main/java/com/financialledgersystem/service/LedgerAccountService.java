package com.financialledgersystem.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.financialledgersystem.audit.Audit;
import com.financialledgersystem.dto.LedgerAccountRequest;
import com.financialledgersystem.dto.LedgerAccountResponse;
import com.financialledgersystem.entity.LedgerAccount;
import com.financialledgersystem.repository.LedgerAccountRepository;

@Service
public class LedgerAccountService {

	@Autowired
	private LedgerAccountRepository repository;

	@Audit(operation = "CREATE ACCOUNT")
	public LedgerAccountResponse createAccount(LedgerAccountRequest request) {

		if (repository.existsByAccountNumber(request.getAccountNumber())) {
			throw new RuntimeException("Account already exits");
		}

		LedgerAccount account = new LedgerAccount();

		account.setAccountNumber(request.getAccountNumber());
		account.setAccountName(request.getAccountName());
		account.setBalance(request.getBalance());
		LedgerAccount saved = repository.save(account);
		return new LedgerAccountResponse(

				saved.getId(), saved.getAccountNumber(), saved.getAccountName(), saved.getBalance());
	}

	public List<LedgerAccountResponse> getAllAccounts() {

		return repository.findAll().stream().map(acc -> new LedgerAccountResponse(

				acc.getId(), acc.getAccountNumber(), acc.getAccountName(), acc.getBalance()))
				.collect(Collectors.toList());

	}

	@Cacheable(value = "accounts", key = "#id")
	public LedgerAccountResponse getAccount(Long id) {

		 System.out.println("Fetching From Database...");

		LedgerAccount account = repository.findById(id).orElseThrow(() -> new RuntimeException(""));

		return new LedgerAccountResponse(account.getId(), account.getAccountNumber(), account.getAccountName(),
				account.getBalance());
	}

	@CacheEvict(value = "accounts", key = "#id")
	public String deleteAccount(Long id) {

		LedgerAccount account = repository.findById(id).orElseThrow(() -> new RuntimeException("Account Not Found"));

		repository.delete(account);

		return "Account Deleted Successfully";
	}
}
