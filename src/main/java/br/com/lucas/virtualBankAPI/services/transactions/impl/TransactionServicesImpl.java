package br.com.lucas.virtualBankAPI.services.transactions.impl;

import br.com.lucas.virtualBankAPI.domain.transactions.Transaction;
import br.com.lucas.virtualBankAPI.domain.transactions.TransactionDTO;
import br.com.lucas.virtualBankAPI.enums.exceptions.ErrorMessage;
import br.com.lucas.virtualBankAPI.repositories.transactions.TransactionRepository;
import br.com.lucas.virtualBankAPI.services.exceptions.ObjectNotFoundException;
import br.com.lucas.virtualBankAPI.services.transactions.TransactionServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServicesImpl implements TransactionServices {

    @Autowired
    private TransactionRepository transactionRepository;
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
