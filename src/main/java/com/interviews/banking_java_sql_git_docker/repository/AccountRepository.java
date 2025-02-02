package com.interviews.banking_java_sql_git_docker.repository;

import com.interviews.banking_java_sql_git_docker.model.Account;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findById(Long accountId);
    boolean existsByAccountHolderName(@NotNull String accountHolderName);
}
