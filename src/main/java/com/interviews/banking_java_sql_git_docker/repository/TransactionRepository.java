package com.interviews.banking_java_sql_git_docker.repository;

import com.interviews.banking_java_sql_git_docker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("select t from Transaction t join fetch t.accounts a " +
            "where a.id = :aId order by t.transactionDate desc")
    List<Transaction> findAllByAccountId(@Param("aId") Long aId);
}
