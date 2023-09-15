package br.com.lucas.virtualBankAPI.domain.accounts;

import br.com.lucas.virtualBankAPI.domain.users.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class AccountDTO {

    private Long id;
    private String accNumber;
    private Double balance;
    private Usuario owner;

    public AccountDTO(Long id, String accNumber, Double balance) {
        this.id = id;
        this.accNumber = accNumber;
        this.balance = balance;
    }
}
