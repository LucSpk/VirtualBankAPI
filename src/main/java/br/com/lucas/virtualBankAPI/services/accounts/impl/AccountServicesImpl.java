package br.com.lucas.virtualBankAPI.services.accounts.impl;

import br.com.lucas.virtualBankAPI.domain.accounts.Account;
import br.com.lucas.virtualBankAPI.domain.accounts.AccountDTO;
import br.com.lucas.virtualBankAPI.enums.exceptions.ErrorMessage;
import br.com.lucas.virtualBankAPI.repositories.accounts.AccountRepository;
import br.com.lucas.virtualBankAPI.services.accounts.AccountServices;
import br.com.lucas.virtualBankAPI.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;

import java.util.List;

public class AccountServicesImpl implements AccountServices {

    private AccountRepository accountRepository;
    private ModelMapper modelMapper;

    @Override
    public AccountDTO findById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(ErrorMessage.OBJETO_NAO_ENCONTRADO.getMessage()));
        return modelMapper.map(account, AccountDTO.class);
    }

    @Override
    public List<AccountDTO> findAll() {
        return null;
    }

    @Override
    public AccountDTO create(Account usuario) {
        return null;
    }

    @Override
    public AccountDTO update(Account usuario, Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
