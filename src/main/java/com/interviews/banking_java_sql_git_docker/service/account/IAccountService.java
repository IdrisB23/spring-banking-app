package com.interviews.banking_java_sql_git_docker.service.account;

import com.interviews.banking_java_sql_git_docker.model.Account;
import com.interviews.banking_java_sql_git_docker.request.CreateAccountRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAccountService {
    Account createAccount(@Valid CreateAccountRequest request);
    Account getAccount(Long accountId);
    Page<Account> getAllAccounts(Pageable pageable);
}
