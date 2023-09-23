package br.com.lucas.virtualBankAPI.domain.accounts;

import br.com.lucas.virtualBankAPI.domain.users.UsuarioDTO;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class AccountDTO {

    private Long id;
    private String accNumber;
    private Double balance;
    private UsuarioDTO owner;

    public AccountDTO(Long id, String accNumber, Double balance) {
        this.id = id;
        this.accNumber = accNumber;
        this.balance = balance;
    }
}
