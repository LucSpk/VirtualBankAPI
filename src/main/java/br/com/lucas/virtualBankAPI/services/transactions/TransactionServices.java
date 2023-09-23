package br.com.lucas.virtualBankAPI.services.transactions;

import br.com.lucas.virtualBankAPI.domain.transactions.Transaction;
import br.com.lucas.virtualBankAPI.domain.transactions.TransactionDTO;
import br.com.lucas.virtualBankAPI.domain.transactions.TransactionRequest;

import java.util.List;

public interface TransactionServices {

    TransactionDTO findById(Long id);
    List<TransactionDTO> findAll();
    void transaction(TransactionRequest transaction);
}
