package br.com.lucas.virtualBankAPI.services.accounts;

import br.com.lucas.virtualBankAPI.domain.accounts.Account;
import br.com.lucas.virtualBankAPI.domain.accounts.AccountDTO;

import java.util.List;

public interface AccountServices {

    AccountDTO findById(Long id);
    List<AccountDTO> findAll();
    AccountDTO create(Account account, Integer userId);
    AccountDTO update(Account account, Long id);
    void delete(Long id);
}
