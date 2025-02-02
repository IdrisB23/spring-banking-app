package com.interviews.banking_java_sql_git_docker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "accounts_table")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String accountHolderName;
    @Min(value=0, message="Balance must be positive")
    private BigDecimal balance;
    @JsonIgnore
    @ManyToMany(mappedBy = "accounts")
    private List<Transaction> transactions = new ArrayList<>();

    public Account(Long id, String accountHolderName, BigDecimal balance) {
        this.id = id;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }
}
