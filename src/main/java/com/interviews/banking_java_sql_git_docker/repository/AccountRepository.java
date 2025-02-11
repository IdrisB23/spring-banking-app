package com.interviews.banking_java_sql_git_docker.repository;

import com.interviews.banking_java_sql_git_docker.model.Account;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findById(Long accountId);
    boolean existsByAccountHolderName(@NotNull String accountHolderName);

    Page<Account> findAccountByAccountHolderNameContaining(String accountHolderName, Pageable pageable);
}
