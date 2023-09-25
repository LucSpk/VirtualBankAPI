package br.com.lucas.virtualBankAPI.services.transactions.impl;

import br.com.lucas.virtualBankAPI.domain.accounts.Account;
import br.com.lucas.virtualBankAPI.domain.accounts.AccountDTO;
import br.com.lucas.virtualBankAPI.domain.transactions.Transaction;
import br.com.lucas.virtualBankAPI.domain.transactions.TransactionDTO;
import br.com.lucas.virtualBankAPI.domain.transactions.TransactionRequest;
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

    public void transaction(TransactionRequest request) {
        if(request.getType() == null)
            throw new IllegalArgumentException(ErrorMessage.TRANSACAO_NECESSARIA.getMessage());

        if(request.getType().getValue() == 0) {
            this.transference(request.getSourceAccountId(), request.getDestinationAccountId(), request.getAmount());
            return;
        }
        if(request.getType().getValue() == 1) {
            this.payment(request.getSourceAccountId(), request.getDestinationAccountId(), request.getAmount());
            return;
        }
        if(request.getType().getValue() == 2) {
            this.deposit(request.getDestinationAccountId(), request.getAmount());
            return;
        }
        if(request.getType().getValue() == 3) {
            this.withdraw(request.getSourceAccountId(), request.getAmount());
            return;
        }

        throw new IllegalArgumentException(ErrorMessage.TRANSACAO_NAO_ENCONTRADA.getMessage());
    }

    private void transference(Long sourceAccountId, Long destinationAccountId, double amount) {
        if (sourceAccountId == null || destinationAccountId == null)
            throw new IllegalArgumentException(ErrorMessage.CONTAS_DEVEM_SER_FORNECIDAS.getMessage());

        if (amount <= 0)
            throw new IllegalArgumentException(ErrorMessage.VALOR_MENOR_QUE_ZERO.getMessage());

        Account sourceAccount = this.findAccount(sourceAccountId);
        Account destinationAccount = this.findAccount(destinationAccountId);

        if (sourceAccount.getBalance() < amount)
            throw new InsufficientBalanceException(ErrorMessage.SALDO_INSUFICIENTE.getMessage());

        this.createTransaction(TransactionType.TRANSFER, amount, sourceAccount, destinationAccount);
    }

    private void payment(Long sourceAccountId, Long destinationAccountId, double amount) {
        if (sourceAccountId == null || destinationAccountId == null)
            throw new IllegalArgumentException(ErrorMessage.CONTAS_DEVEM_SER_FORNECIDAS.getMessage());

        if (amount <= 0)
            throw new IllegalArgumentException(ErrorMessage.VALOR_MENOR_QUE_ZERO.getMessage());

        Account sourceAccount = this.findAccount(sourceAccountId);
        Account destinationAccount = this.findAccount(destinationAccountId);

        if (sourceAccount.getBalance() < amount)
            throw new InsufficientBalanceException(ErrorMessage.SALDO_INSUFICIENTE.getMessage());

        this.createTransaction(TransactionType.PAYMENT, amount, sourceAccount, destinationAccount);
    }

    private void deposit(Long destinationAccountId, double amount) {
        if(destinationAccountId == null)
            throw new IllegalArgumentException(ErrorMessage.ACC_DESTINO_NECESSARIA.getMessage());

        if (amount <= 0)
            throw new IllegalArgumentException(ErrorMessage.VALOR_MENOR_QUE_ZERO.getMessage());

        Account destinationAccount = this.findAccount(destinationAccountId);

        this.createTransactionCredit(amount, destinationAccount);
    }

    private void withdraw(Long sourceAccountId, double amount) {
        if(sourceAccountId == null)
            throw new IllegalArgumentException(ErrorMessage.ACC_ORIGEM_NECESSARIA.getMessage());

        if (amount <= 0)
            throw new IllegalArgumentException(ErrorMessage.VALOR_MENOR_QUE_ZERO.getMessage());

        Account sourceAccount = this.findAccount(sourceAccountId);

        if (sourceAccount.getBalance() < amount)
            throw new InsufficientBalanceException(ErrorMessage.SALDO_INSUFICIENTE.getMessage());

        this.createTransactionDebit(amount, sourceAccount);
    }

    private void createTransactionCredit(double amount, Account account) {
        Transaction deposityTransaction = new Transaction(TransactionType.DEPOSIT, -amount, LocalDateTime.now());
        deposityTransaction.setSourceAccount(null);
        deposityTransaction.setDestinationAccount(account);

        this.saveTransaction(account, deposityTransaction, amount);
    }

    private void createTransactionDebit(double amount, Account account) {
        Transaction withdrawTransaction = new Transaction(TransactionType.WITHDRAW, amount, LocalDateTime.now());
        withdrawTransaction.setSourceAccount(account);
        withdrawTransaction.setDestinationAccount(null);

        this.saveTransaction(account, withdrawTransaction, amount);
    }

    private void createTransaction(TransactionType type, double amount, Account sourceAccount, Account destinationAccount) {

        Transaction debitTransaction = new Transaction(type, -amount, LocalDateTime.now());
        debitTransaction.setSourceAccount(sourceAccount);
        debitTransaction.setDestinationAccount(destinationAccount);

        Transaction creditTransaction = new Transaction(type, amount, LocalDateTime.now());
        creditTransaction.setSourceAccount(sourceAccount);
        creditTransaction.setDestinationAccount(destinationAccount);

        this.saveTransaction(debitTransaction, creditTransaction);

    }

    private void saveTransaction(Transaction debitTransaction, Transaction creditTransaction) {
        Account sourceAccount = debitTransaction.getSourceAccount();
        sourceAccount.setBalance(sourceAccount.getBalance() - creditTransaction.getAmount());

        Account destinationAccount = debitTransaction.getDestinationAccount();
        destinationAccount.setBalance(destinationAccount.getBalance() + creditTransaction.getAmount());

        this.saveTransaction(sourceAccount, debitTransaction);
        this.saveTransaction(destinationAccount, creditTransaction);
    }

    private void saveTransaction(Account account, Transaction transaction, double amount) {
        account.setBalance(account.getBalance() + amount);
        this.saveTransaction(account, transaction);
    }

    private void saveTransaction(Account account, Transaction transaction) {
        transactionRepository.save(transaction);
        accountServices.update(account, account.getId());
    }

    private Account findAccount(Long id) {
        if(id == null)
            throw new IllegalArgumentException(ErrorMessage.NUMERO_ACC_INVALIDO.getMessage() + ": " + id);

        return modelMapper.map(accountServices.findById(id), Account.class);
    }

}
