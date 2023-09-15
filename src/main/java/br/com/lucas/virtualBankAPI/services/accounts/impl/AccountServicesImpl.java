package br.com.lucas.virtualBankAPI.services.accounts.impl;

import br.com.lucas.virtualBankAPI.domain.accounts.Account;
import br.com.lucas.virtualBankAPI.domain.accounts.AccountDTO;
import br.com.lucas.virtualBankAPI.enums.exceptions.ErrorMessage;
import br.com.lucas.virtualBankAPI.repositories.accounts.AccountRepository;
import br.com.lucas.virtualBankAPI.services.accounts.AccountServices;
import br.com.lucas.virtualBankAPI.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

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
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map((acc) -> modelMapper.map(acc, AccountDTO.class)).collect(Collectors.toList());
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
