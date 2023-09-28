package br.com.lucas.virtualBankAPI.services.accounts.impl;

import br.com.lucas.virtualBankAPI.domain.accounts.Account;
import br.com.lucas.virtualBankAPI.domain.accounts.AccountDTO;
import br.com.lucas.virtualBankAPI.domain.transactions.Transaction;
import br.com.lucas.virtualBankAPI.domain.transactions.TransactionDTO;
import br.com.lucas.virtualBankAPI.domain.transactions.TransactionResponseDTO;
import br.com.lucas.virtualBankAPI.domain.users.Usuario;
import br.com.lucas.virtualBankAPI.enums.exceptions.ErrorMessage;
import br.com.lucas.virtualBankAPI.repositories.accounts.AccountRepository;
import br.com.lucas.virtualBankAPI.repositories.users.UserRepository;
import br.com.lucas.virtualBankAPI.services.accounts.AccountServices;
import br.com.lucas.virtualBankAPI.services.exceptions.DataIntegrityViolationException;
import br.com.lucas.virtualBankAPI.services.exceptions.DivergentDataException;
import br.com.lucas.virtualBankAPI.services.exceptions.ObjectNotFoundException;
import br.com.lucas.virtualBankAPI.services.users.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountServicesImpl implements AccountServices {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserServices userServices;

    @Override
    public AccountDTO findById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(ErrorMessage.NUM_ACC_NAO_ENCONTRADO.getMessage()));
        return modelMapper.map(account, AccountDTO.class);
    }

    @Override
    public List<AccountDTO> findAll() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map((acc) -> modelMapper.map(acc, AccountDTO.class)).collect(Collectors.toList());
    }

    @Override
    public AccountDTO create(Account account, Integer userId) {
        this.accIsPresent(account);
        Usuario usuario = this.getUsuarioById(userId);
        account.setOwner(usuario);
        account.setBalance(0.0);
        Account accountCreated = accountRepository.save(account);
        return modelMapper.map(accountCreated, AccountDTO.class);
    }

    @Override
    public AccountDTO update(Account account, Long id) {
        if(!account.getId().equals(id))
            throw new DivergentDataException(ErrorMessage.DIVERGENCIA_NOS_DADOS.getMessage());

        this.accIsPresent(account);

        Account acc = accountRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(ErrorMessage.NUM_ACC_NAO_ENCONTRADO.getMessage()));

        acc.setBalance(account.getBalance());

        Account accountUpdated = accountRepository.save(acc);
        return modelMapper.map(accountUpdated, AccountDTO.class);
    }

    @Override
    public void delete(Long id) {
        this.findById(id);
        accountRepository.deleteById(id);
    }

    @Override
    public List<TransactionResponseDTO> getTransactions(Long id) {
        Account account = modelMapper.map(this.findById(id), Account.class);
        List<Transaction> incomingTransactions = account.getIncomingTransactions();
        List<Transaction> outgoingTransactions = account.getOutgoingTransactions();

        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(incomingTransactions);
        transactions.addAll(outgoingTransactions);

        transactions.sort((o1, o2) -> o1.getTimestamp().compareTo(o2.getTimestamp()));

        return transactions.stream().map((trs) -> modelMapper.map(trs, TransactionResponseDTO.class)).collect(Collectors.toList());
    }

    private void accIsPresent(Account account) {
        Optional<Account> response = accountRepository.findByAccNumber(account.getAccNumber());
        if(response.isPresent() && !response.get().getId().equals(account.getId()))
            throw new DataIntegrityViolationException(ErrorMessage.NUMERO_ACC_JA_CADASTRADO.getMessage());
    }

    private Usuario getUsuarioById(Integer id) {
        return modelMapper.map(userServices.findById(id), Usuario.class);
    }

}
