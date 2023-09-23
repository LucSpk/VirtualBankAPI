package br.com.lucas.virtualBankAPI.domain.transactions;

import br.com.lucas.virtualBankAPI.domain.accounts.Account;
import br.com.lucas.virtualBankAPI.enums.transactions.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private Double amount;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "source_account_id")
    private Account sourceAccount;

    @ManyToOne
    @JoinColumn(name = "destination_account_id")
    private Account destinationAccount;

    public Transaction(TransactionType transactionType, Double amount, LocalDateTime timestamp) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.timestamp = timestamp;
    }
}
