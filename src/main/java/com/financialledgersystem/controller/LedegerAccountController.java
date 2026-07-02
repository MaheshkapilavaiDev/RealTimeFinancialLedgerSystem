package com.financialledgersystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financialledgersystem.dto.ApiResponse;
import com.financialledgersystem.dto.LedgerAccountRequest;
import com.financialledgersystem.dto.LedgerAccountResponse;
import com.financialledgersystem.service.LedgerAccountService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/accounts")
public class LedegerAccountController {
	
	@Autowired
	private LedgerAccountService ledgerAccountService;

	
	@PostMapping("/create")
	public ResponseEntity<LedgerAccountResponse> create(@Valid @RequestBody LedgerAccountRequest request){
		
		return ResponseEntity.ok(ledgerAccountService.createAccount(request));
		
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<LedgerAccountResponse>> getAll(){
		
		return ResponseEntity.ok(ledgerAccountService.getAllAccounts());
	}
	@GetMapping("/{id}")
    public ResponseEntity<LedgerAccountResponse> getById(
            @PathVariable Long id){

        return ResponseEntity.ok(ledgerAccountService.getAccount(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(
            @PathVariable Long id){

        return ResponseEntity.ok(
                new ApiResponse(true, ledgerAccountService.deleteAccount(id)));
    }
}
