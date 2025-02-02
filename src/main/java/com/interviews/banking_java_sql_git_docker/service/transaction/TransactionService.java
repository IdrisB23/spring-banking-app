package com.interviews.banking_java_sql_git_docker.service.transaction;

import com.interviews.banking_java_sql_git_docker.exceptions.ResourceNotFoundException;
import com.interviews.banking_java_sql_git_docker.model.Account;
import com.interviews.banking_java_sql_git_docker.model.Transaction;
import com.interviews.banking_java_sql_git_docker.repository.AccountRepository;
import com.interviews.banking_java_sql_git_docker.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@Service
public class TransactionService implements ITransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    private Transaction createTransaction(Account account, Transaction.TransactionType transactionType,
                                          BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionType);
        transaction.setAmount(amount);
        transaction.addAccount(account);
        transaction.setTransactionDate(Timestamp.valueOf(java.time.LocalDateTime.now()));
        return transaction;
    }

    @Override
    @Transactional
    public Transaction createTransaction(Transaction.TransactionType transactionType, Long accountId,
                                         BigDecimal amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new ResourceNotFoundException("Account not found")
        );
        Transaction transaction = createTransaction(account, transactionType, amount);
        Transaction savedTransaction = transactionRepository.save(transaction);
        account.setBalance(transactionType == Transaction.TransactionType.CREDIT ?
                account.getBalance().add(amount) : account.getBalance().subtract(amount));
        if (account.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        accountRepository.save(account);
        return savedTransaction;
    }

    @Override
    public List<Transaction> getAccountTransactions(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new ResourceNotFoundException("Account not found");
        }
        return transactionRepository.findAllByAccountId(accountId);
    }
}
