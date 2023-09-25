package br.com.lucas.virtualBankAPI.domain.accounts;

import br.com.lucas.virtualBankAPI.domain.transactions.TransactionDTO;
import br.com.lucas.virtualBankAPI.domain.users.UsuarioDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class AccountDTO {

    private Long id;
    private String accNumber;
    private Double balance;
    private UsuarioDTO owner;
    @JsonIgnore
    private List<TransactionDTO> outgoingTransactions = new ArrayList<>();
    @JsonIgnore
    private List<TransactionDTO> incomingTransactions = new ArrayList<>();

    public AccountDTO(Long id, String accNumber, Double balance) {
        this.id = id;
        this.accNumber = accNumber;
        this.balance = balance;
    }

    public AccountDTO(Long id, String accNumber, Double balance, UsuarioDTO owner) {
        this.id = id;
        this.accNumber = accNumber;
        this.balance = balance;
        this.owner = owner;
    }
}
