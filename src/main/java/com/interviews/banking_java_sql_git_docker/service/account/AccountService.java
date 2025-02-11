package com.interviews.banking_java_sql_git_docker.service.account;

import com.interviews.banking_java_sql_git_docker.exceptions.ResourceNotFoundException;
import com.interviews.banking_java_sql_git_docker.model.Account;
import com.interviews.banking_java_sql_git_docker.repository.AccountRepository;
import com.interviews.banking_java_sql_git_docker.request.CreateAccountRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


@Validated
@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {
    private final AccountRepository accountRepository;
    private static final Logger logger = LogManager.getLogger(AccountService.class);

    @Override
    public Account createAccount(@Valid CreateAccountRequest request) {
        Account account = new Account();
        account.setAccountHolderName(request.getAccountHolderName());
        account.setBalance(request.getBalance());
        return accountRepository.save(account);
    }

    @Override
    public Account getAccount(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(
                () -> new ResourceNotFoundException("Account not found")
        );
    }

    @Override
    public Page<Account> getAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Override
    public Page<Account> searchAccounts(String accountHolderName, Pageable pageable) {
        return accountRepository.findAccountByAccountHolderNameContaining(accountHolderName, pageable);
    }
}
