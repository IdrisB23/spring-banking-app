package com.interviews.banking_java_sql_git_docker.controller;

import com.interviews.banking_java_sql_git_docker.exceptions.ResourceNotFoundException;
import com.interviews.banking_java_sql_git_docker.model.Account;
import com.interviews.banking_java_sql_git_docker.model.Transaction;
import com.interviews.banking_java_sql_git_docker.request.CreateAccountRequest;
import com.interviews.banking_java_sql_git_docker.response.ApiResponse;
import com.interviews.banking_java_sql_git_docker.service.account.IAccountService;
import com.interviews.banking_java_sql_git_docker.service.transaction.ITransactionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController  {
    private final IAccountService accountService;
    private final ITransactionService transactionService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createAccount(@RequestBody CreateAccountRequest request) {
        Account account = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("Account created", account));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<ApiResponse> getAccount(@PathVariable Long accountId) {
        Account account = accountService.getAccount(accountId);
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(new ApiResponse("Account retrieved", account));
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Account> accounts = accountService.getAllAccounts(pageable);
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(new ApiResponse("Accounts retrieved", accounts));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchAccounts(
            @RequestParam(defaultValue = "") String accountHolderName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Account> accounts = accountService.searchAccounts(accountHolderName, pageable);
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(new ApiResponse("Accounts retrieved", accounts));
    }

    @PostMapping("/{accountId}/transaction")
    public ResponseEntity<ApiResponse> createTransaction(@PathVariable Long accountId, @RequestParam String transactionType, @RequestParam BigDecimal amount) {
        try {
            Transaction createdTransaction = transactionService.createTransaction(Transaction.TransactionType.valueOf(transactionType), accountId, amount);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("Transaction created", createdTransaction));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<ApiResponse> getAccountTransactions(@PathVariable Long accountId) {
        try {
            List<Transaction> transactions = transactionService.getAccountTransactions(accountId);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(new ApiResponse("Transactions retrieved", transactions));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

}
