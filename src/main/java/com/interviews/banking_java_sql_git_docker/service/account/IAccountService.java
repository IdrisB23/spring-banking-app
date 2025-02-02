package com.interviews.banking_java_sql_git_docker.service.account;

import com.interviews.banking_java_sql_git_docker.model.Account;
import com.interviews.banking_java_sql_git_docker.request.CreateAccountRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface IAccountService {
    public Account createAccount(CreateAccountRequest request);
    public Account getAccount(Long accountId);
    public Page<Account> getAllAccounts(Pageable pageable);
}
