package br.com.lucas.virtualBankAPI.services.transactions;

import br.com.lucas.virtualBankAPI.domain.transactions.Transaction;
import br.com.lucas.virtualBankAPI.domain.transactions.TransactionDTO;

import java.util.List;

public interface TransactionServices {

    TransactionDTO findById(Long id);
    List<TransactionDTO> findAll();
    TransactionDTO create(Transaction transaction, Long sourceAccountId, Long destinationAccount);
    TransactionDTO update(Transaction transaction, Long id);
    void delete(Long id);
    void transaction(Transaction transaction, Long sourceAccountId, Long destinationAccountId);
}
