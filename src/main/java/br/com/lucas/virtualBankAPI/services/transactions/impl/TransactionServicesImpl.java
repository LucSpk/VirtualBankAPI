package br.com.lucas.virtualBankAPI.services.transactions.impl;

import br.com.lucas.virtualBankAPI.domain.transactions.Transaction;
import br.com.lucas.virtualBankAPI.domain.transactions.TransactionDTO;
import br.com.lucas.virtualBankAPI.repositories.transactions.TransactionRepository;
import br.com.lucas.virtualBankAPI.services.transactions.TransactionServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServicesImpl implements TransactionServices {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TransactionDTO findById(Long id) {
        return null;
    }

    @Override
    public List<TransactionDTO> findAll() {
        return null;
    }

    @Override
    public TransactionDTO create(Transaction transaction, Long sourceAccountId, Long destinationAccount) {
        return null;
    }

    @Override
    public TransactionDTO update(Transaction transaction, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
