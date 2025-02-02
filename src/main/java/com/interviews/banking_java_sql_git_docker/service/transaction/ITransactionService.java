package com.interviews.banking_java_sql_git_docker.service.transaction;

import com.interviews.banking_java_sql_git_docker.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface ITransactionService {
    public Transaction createTransaction(Transaction.TransactionType transactionType, Long accountId, BigDecimal amount);
    public List<Transaction> getAccountTransactions(Long accountId);
}
