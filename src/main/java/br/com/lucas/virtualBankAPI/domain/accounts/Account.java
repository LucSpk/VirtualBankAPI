package br.com.lucas.virtualBankAPI.domain.accounts;

import br.com.lucas.virtualBankAPI.domain.transactions.Transaction;
import br.com.lucas.virtualBankAPI.domain.users.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String accNumber;
    private Double balance;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuario owner;

    @OneToMany(mappedBy = "sourceAccount", fetch = FetchType.EAGER)
    private List<Transaction> outgoingTransactions = new ArrayList<>();
    @OneToMany(mappedBy = "destinationAccount", fetch = FetchType.EAGER)
    private List<Transaction> incomingTransactions = new ArrayList<>();

    public Account(Long id, String accNumber, Double balance) {
        this.id = id;
        this.accNumber = accNumber;
        this.balance = balance;
    }

    public Account(Long id, String accNumber, Double balance, Usuario owner) {
        this.id = id;
        this.accNumber = accNumber;
        this.balance = balance;
        this.owner = owner;
    }
}
