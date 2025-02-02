package com.interviews.banking_java_sql_git_docker.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transactions_table")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class Transaction {
    public enum TransactionType {
        CREDIT,
        DEBIT
    }
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToMany
    @JoinTable(
            name = "account_transaction",
            joinColumns = @JoinColumn(name = "transaction_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id")
    )
    private List<Account> accounts = new ArrayList<>();
    private TransactionType transactionType;
    private BigDecimal amount;
    private Timestamp transactionDate;

    public Transaction(TransactionType transactionType, BigDecimal amount, Timestamp transactionDate) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }
}
