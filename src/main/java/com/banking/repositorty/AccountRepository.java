package com.banking.repositorty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.banking.model.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {
	
}
