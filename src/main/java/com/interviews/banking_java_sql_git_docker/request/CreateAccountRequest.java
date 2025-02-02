package com.interviews.banking_java_sql_git_docker.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAccountRequest {
    @NotNull @Size(min = 1, max = 50)
    private String accountHolderName;
    @NotNull @PositiveOrZero
    private BigDecimal balance;
}
