package br.com.lucas.virtualBankAPI.domain.transactions;

import br.com.lucas.virtualBankAPI.domain.accounts.AccountInfoDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class TransactionResponseDTO {
    @JsonIgnore
    private Long id;
    private String transactionType;
    private Double amount;
    private LocalDateTime timestamp;
    private AccountInfoDTO sourceAccount;
    private AccountInfoDTO destinationAccount;
}
