package br.com.lucas.virtualBankAPI.domain.transactions;

import br.com.lucas.virtualBankAPI.domain.accounts.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class TransactionDTO {

    private Long transactionId;
    private String transactionType;
    private Double amount;
    private LocalDateTime timestamp;
    private Account sourceAccount;
    private Account destinationAccount;
}
