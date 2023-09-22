package br.com.lucas.virtualBankAPI.domain.transactions;

import br.com.lucas.virtualBankAPI.enums.transactions.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Getter
public class TransactionRequest {
    private TransactionType type;
    private Long sourceAccountId;
    private Long destinationAccountId;
    private Double amount;
}
