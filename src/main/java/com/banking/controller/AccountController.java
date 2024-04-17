package com.banking.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.banking.model.Account;
import com.banking.repositorty.AccountRepository;

@RestController
public class AccountController {
	
	@Autowired
	AccountRepository accountRepository;
	
	@PostMapping("/save")
	public Account createAccount(@RequestBody Account account) {
		
		return accountRepository.save(account);
	}
	
	 @GetMapping("/getAccountDetails/{id}")
	    public ResponseEntity<?> getAccountDetails(@PathVariable Long id) {
	        Optional<Account> accountOptional = accountRepository.findById(id);
	        if (accountOptional.isPresent()) {
	            Account account = accountOptional.get();
	            return ResponseEntity.ok(account);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
	        }
	    }
	 
	 @PutMapping("/deposit/{id}")
	 public ResponseEntity<String> toDepositAmount(@PathVariable Long id, @RequestBody Map<String, Double>deposits) {
		 Optional<Account> accountOptional= accountRepository.findById(id);
		if(accountOptional.isPresent()){
		Account account=accountOptional.get();
			double amount= deposits.getOrDefault("amount", 0.0);
			 Double currentBalance =account.getBalance();
			 account.setBalance(currentBalance+amount);
			 accountRepository.save(account);
			 return ResponseEntity.ok("Hi "+account.getAccountHolderName()+"!"+"\nDeposited amount of Rs "+amount+" has been successfully credited to your account\nyour current balance is "+account.getBalance());
			 
		 }
		else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
	        }
	 }
	 
	 @PostMapping("/withdraw/{id}")
	 public ResponseEntity<String> withdraw(@PathVariable Long id, @RequestBody Map<String, Double> withdraw) {
		Optional<Account>accountOptional= accountRepository.findById(id);
		if(accountOptional.isPresent()) {
		Double withdrawAmount=	withdraw.getOrDefault("amount", 0.0);
		Account account=accountOptional.get();
		account.setBalance(account.getBalance()-withdrawAmount);
		accountRepository.save(account);
		return ResponseEntity.ok("Hi " +account.getAccountHolderName()+"!"+"\nWithdrawl amount of Rs "+withdrawAmount+" has been successfully  debited from Your account\nYour current balance Rs "+account.getBalance());
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
		}
	 }
	 
	 @DeleteMapping("/delete/{id}")
	 public String deleteUser(@PathVariable Long id) {
		
		        Optional<Account> accountOptional = accountRepository.findById(id);
		        if (accountOptional.isPresent()) {
		            accountRepository.deleteById(id);
		            return "Account with ID " + id + " has been deleted.";
		        } else {
		            return "Account with ID " + id + " not found.";
		        }
		    }
}

	

	
	
		

	


