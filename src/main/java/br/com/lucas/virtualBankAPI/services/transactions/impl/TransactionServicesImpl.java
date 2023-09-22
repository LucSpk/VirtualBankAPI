package br.com.lucas.virtualBankAPI.services.transactions.impl;

import br.com.lucas.virtualBankAPI.domain.accounts.Account;
import br.com.lucas.virtualBankAPI.domain.accounts.AccountDTO;
import br.com.lucas.virtualBankAPI.domain.transactions.Transaction;
import br.com.lucas.virtualBankAPI.domain.transactions.TransactionDTO;
import br.com.lucas.virtualBankAPI.enums.exceptions.ErrorMessage;
import br.com.lucas.virtualBankAPI.enums.transactions.TransactionType;
import br.com.lucas.virtualBankAPI.repositories.transactions.TransactionRepository;
import br.com.lucas.virtualBankAPI.services.accounts.AccountServices;
import br.com.lucas.virtualBankAPI.services.exceptions.IllegalArgumentException;
import br.com.lucas.virtualBankAPI.services.exceptions.InsufficientBalanceException;
import br.com.lucas.virtualBankAPI.services.exceptions.ObjectNotFoundException;
import br.com.lucas.virtualBankAPI.services.transactions.TransactionServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServicesImpl implements TransactionServices {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountServices accountServices;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TransactionDTO findById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(ErrorMessage.TRANSACTION_NAO_ENCONTRADO.getMessage()));
        return modelMapper.map(transaction, TransactionDTO.class);
    }

    @Override
    public List<TransactionDTO> findAll() {
        List<Transaction> response = transactionRepository.findAll();
        return response.stream().map((trs) -> modelMapper.map(trs, TransactionDTO.class)).collect(Collectors.toList());
    }

    @Override
    public TransactionDTO create(Transaction transaction, Long sourceAccountId, Long destinationAccountId) {
        return null;
    }

    @Override
    public TransactionDTO update(Transaction transaction, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    public void transaction(Transaction transaction, Long sourceAccountId, Long destinationAccountId) {
        Account sourceAccount = this.findAccount(sourceAccountId);
        Account destinationAccount = this.findAccount(destinationAccountId);

        transactionManager(transaction.getTransactionType(), sourceAccount, destinationAccount, transaction.getAmount());
    }

    private void transactionManager(TransactionType type, Account sourceAccount, Account destinationAccount, double amount) {
        if(type == null)
            throw new IllegalArgumentException(ErrorMessage.TRANSACAO_NAO_ENCONTRADA.getMessage());

        if(type.getValue() == 0)
            transference(sourceAccount, destinationAccount, amount);

    }

    private void transference(Account sourceAccount, Account destinationAccount, double amount) {
        if (sourceAccount == null || destinationAccount == null)
            throw new IllegalArgumentException(ErrorMessage.CONTAS_DEVEM_SER_FORNECIDAS.getMessage());

        if (amount <= 0)
            throw new IllegalArgumentException(ErrorMessage.VALOR_MENOR_QUE_ZERO.getMessage());

        if (sourceAccount.getBalance() < amount)
            throw new InsufficientBalanceException(ErrorMessage.SALDO_INSUFICIENTE.getMessage());

        Transaction debitTransaction = new Transaction(TransactionType.TRANSFER, -amount, LocalDateTime.now());
        debitTransaction.setSourceAccount(sourceAccount);
        debitTransaction.setDestinationAccount(destinationAccount);

        Transaction creditTransaction = new Transaction(TransactionType.TRANSFER, amount, LocalDateTime.now());
        creditTransaction.setSourceAccount(sourceAccount);
        creditTransaction.setDestinationAccount(destinationAccount);

        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        destinationAccount.setBalance(destinationAccount.getBalance() + amount);

        saveTransaction(sourceAccount, debitTransaction);
        saveTransaction(destinationAccount, creditTransaction);
    }

    private void saveTransaction(Account account, Transaction transaction) {
        transactionRepository.save(transaction);
        accountServices.update(account, account.getId());
    }

    private Account findAccount(Long id) {
        return modelMapper.map(accountServices.findById(id), Account.class);
    }

}
