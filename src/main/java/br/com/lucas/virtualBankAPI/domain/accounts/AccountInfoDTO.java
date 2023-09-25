package br.com.lucas.virtualBankAPI.domain.accounts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class AccountInfoDTO {
    private String accNumber;
    private String ownerName;
}
