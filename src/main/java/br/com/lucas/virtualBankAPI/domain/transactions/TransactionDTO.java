package br.com.lucas.virtualBankAPI.domain.transactions;

import br.com.lucas.virtualBankAPI.domain.accounts.AccountDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor @AllArgsConstructor @Getter
public class TransactionDTO {

    private Long id;
    private String transactionType;
    private Double amount;
    private LocalDateTime timestamp;
    private AccountDTO sourceAccount;
    private AccountDTO destinationAccount;
}
