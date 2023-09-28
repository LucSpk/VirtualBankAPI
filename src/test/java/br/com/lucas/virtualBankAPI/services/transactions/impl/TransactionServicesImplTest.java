package br.com.lucas.virtualBankAPI.services.transactions.impl;

import br.com.lucas.virtualBankAPI.domain.accounts.Account;
import br.com.lucas.virtualBankAPI.domain.accounts.AccountDTO;
import br.com.lucas.virtualBankAPI.domain.transactions.Transaction;
import br.com.lucas.virtualBankAPI.domain.transactions.TransactionDTO;
import br.com.lucas.virtualBankAPI.domain.transactions.TransactionRequest;
import br.com.lucas.virtualBankAPI.enums.exceptions.ErrorMessage;
import br.com.lucas.virtualBankAPI.enums.transactions.TransactionType;
import br.com.lucas.virtualBankAPI.repositories.transactions.TransactionRepository;
import br.com.lucas.virtualBankAPI.services.accounts.impl.AccountServicesImpl;
import br.com.lucas.virtualBankAPI.services.exceptions.IllegalArgumentException;
import br.com.lucas.virtualBankAPI.services.exceptions.InsufficientBalanceException;
import br.com.lucas.virtualBankAPI.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class TransactionServicesImplTest {

    @InjectMocks
    private TransactionServicesImpl services;
    @Mock
    private TransactionRepository repository;
    @Mock
    private AccountServicesImpl accountServices;
    @Mock
    private ModelMapper modelMapper;

    private Transaction transaction;
    private TransactionDTO transactionDTO;
    private TransactionRequest request;
    private Account account;
    private AccountDTO accountDTO;

    private static final Long ID = 1L;
    private static final TransactionType TYPE = TransactionType.DEPOSIT;
    private static final Double AMOUNT = 100.0;
    public static final Long ACC_ID = 1L;
    public static final String ACC_NUMBER = "123456";
    public static final Double BALANCE = 1000.0;
    public static final LocalDateTime TIME_STAMP = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.initializeVariables();
        this.configureModelMapper();
    }

    @Test
    public void whenFindByIdThenReturnAnAccountDTOInstance() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(transaction));

        TransactionDTO response = services.findById(ID);

        assertNotNull(response);
        assertEquals(TransactionDTO.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(AMOUNT, response.getAmount());
        assertEquals(TYPE.toString(), response.getTransactionType());
    }

    @Test
    public void whenFindByIdThenReturnAnObjectNotFoundException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        try {
            services.findById(ID);
            fail("Expected ObjectNotFoundException to be thrown");
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(ErrorMessage.TRANSACTION_NAO_ENCONTRADO.getMessage(), ex.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnNonEmptyList() {
        when(repository.findAll()).thenReturn(List.of(transaction));

        List<TransactionDTO> response = services.findAll();

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());

        assertEquals(TransactionDTO.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getId());
        assertEquals(AMOUNT, response.get(0).getAmount());
        assertEquals(TYPE.toString(), response.get(0).getTransactionType());
    }

    @Test
    public void whenFindAllThenReturnEmptyList() {
        when(repository.findAll()).thenReturn(new ArrayList<>());

        List<TransactionDTO> response = services.findAll();

        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    @Test
    void whenTransactionWithInvokeTransferenceMethod() {
        when(accountServices.findById(anyLong())).thenReturn(accountDTO);
        when(repository.save(any())).thenReturn(transaction);
        when(accountServices.update(any(), anyLong())).thenReturn(accountDTO);

        request.setType(TransactionType.TRANSFER);

        services.transaction(request);

        verify(repository, times(2)).save(any(Transaction.class));
        verify(accountServices, times(2)).update(any(Account.class), anyLong());
    }

    @Test
    void whenTransactionWithInvokePaymentMethod() {
        when(accountServices.findById(anyLong())).thenReturn(accountDTO);
        when(repository.save(any())).thenReturn(transaction);
        when(accountServices.update(any(), anyLong())).thenReturn(accountDTO);

        request.setType(TransactionType.PAYMENT);

        services.transaction(request);

        verify(repository, times(2)).save(any(Transaction.class));
        verify(accountServices, times(2)).update(any(Account.class), anyLong());
    }

    @Test
    void whenTransactionWithInvokeDepositMethod() {
        when(accountServices.findById(anyLong())).thenReturn(accountDTO);
        when(repository.save(any())).thenReturn(transaction);
        when(accountServices.update(any(), anyLong())).thenReturn(accountDTO);

        request.setType(TransactionType.DEPOSIT);

        services.transaction(request);

        verify(repository, times(1)).save(any(Transaction.class));
        verify(accountServices, times(1)).update(any(Account.class), anyLong());
    }

    @Test
    void whenTransactionWithInvokeWithdrawMethod() {
        when(accountServices.findById(anyLong())).thenReturn(accountDTO);
        when(repository.save(any())).thenReturn(transaction);
        when(accountServices.update(any(), anyLong())).thenReturn(accountDTO);

        request.setType(TransactionType.WITHDRAW);

        services.transaction(request);

        verify(repository, times(1)).save(any(Transaction.class));
        verify(accountServices, times(1)).update(any(Account.class), anyLong());
    }

    @Test
    void whenTransactionThrowIllegalArgumentExceptionTransactionRequire() {
        try {
            request.setType(null);
            services.transaction(request);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertEquals(ErrorMessage.TRANSACAO_NECESSARIA.getMessage(), ex.getMessage());
        }
    }

    @Test
    void whenTransferenceThrowIllegalArgumentExceptionAccountRequire() {
        try {
            request.setSourceAccountId(null);
            services.transaction(request);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertEquals(ErrorMessage.CONTAS_DEVEM_SER_FORNECIDAS.getMessage(), ex.getMessage());
        }
    }

    @Test
    void whenTransferenceThrowIllegalArgumentExceptionAmountMenorQueZero() {
        try {
            request.setAmount(-1.0);
            services.transaction(request);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertEquals(ErrorMessage.VALOR_MENOR_QUE_ZERO.getMessage(), ex.getMessage());
        }
    }

    @Test
    void whenTransferenceThrowInsufficientBalanceException() {
        when(accountServices.findById(anyLong())).thenReturn(accountDTO);

        try {
            account.setBalance(0.0);
            services.transaction(request);
            fail("Expected InsufficientBalanceException to be thrown");
        } catch (Exception ex) {
            assertEquals(InsufficientBalanceException.class, ex.getClass());
            assertEquals(ErrorMessage.SALDO_INSUFICIENTE.getMessage(), ex.getMessage());
        }
    }

    @Test
    void whenPaymentThrowIllegalArgumentExceptionAccountRequire() {
        try {
            request.setType(TransactionType.PAYMENT);
            request.setSourceAccountId(null);
            services.transaction(request);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertEquals(ErrorMessage.CONTAS_DEVEM_SER_FORNECIDAS.getMessage(), ex.getMessage());
        }
    }

    @Test
    void whenPaymentThrowIllegalArgumentExceptionAmountMenorQueZero() {
        try {
            request.setType(TransactionType.PAYMENT);
            request.setAmount(-1.0);
            services.transaction(request);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertEquals(ErrorMessage.VALOR_MENOR_QUE_ZERO.getMessage(), ex.getMessage());
        }
    }

    @Test
    void whenPaymentThrowInsufficientBalanceException() {
        when(accountServices.findById(anyLong())).thenReturn(accountDTO);

        try {
            account.setBalance(0.0);
            request.setType(TransactionType.PAYMENT);
            services.transaction(request);
            fail("Expected InsufficientBalanceException to be thrown");
        } catch (Exception ex) {
            assertEquals(InsufficientBalanceException.class, ex.getClass());
            assertEquals(ErrorMessage.SALDO_INSUFICIENTE.getMessage(), ex.getMessage());
        }
    }

    @Test
    void whenDepositThrowIllegalArgumentExceptionAccountRequire() {
        try {
            request.setType(TransactionType.DEPOSIT);
            request.setDestinationAccountId(null);
            services.transaction(request);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertEquals(ErrorMessage.ACC_DESTINO_NECESSARIA.getMessage(), ex.getMessage());
        }
    }

    @Test
    void whenDepositThrowIllegalArgumentExceptionAmountMenorQueZero() {
        try {
            request.setType(TransactionType.DEPOSIT);
            request.setAmount(-1.0);
            services.transaction(request);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertEquals(ErrorMessage.VALOR_MENOR_QUE_ZERO.getMessage(), ex.getMessage());
        }
    }

    @Test
    void whenWithdrawThrowIllegalArgumentExceptionAccountRequire() {
        try {
            request.setType(TransactionType.WITHDRAW);
            request.setSourceAccountId(null);
            services.transaction(request);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertEquals(ErrorMessage.ACC_ORIGEM_NECESSARIA.getMessage(), ex.getMessage());
        }
    }

    @Test
    void whenWithdrawThrowIllegalArgumentExceptionAmountMenorQueZero() {
        try {
            request.setType(TransactionType.WITHDRAW);
            request.setAmount(-1.0);
            services.transaction(request);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertEquals(ErrorMessage.VALOR_MENOR_QUE_ZERO.getMessage(), ex.getMessage());
        }
    }

    @Test
    void whenWithdrawThrowInsufficientBalanceException() {
        when(accountServices.findById(anyLong())).thenReturn(accountDTO);

        try {
            account.setBalance(0.0);
            request.setType(TransactionType.WITHDRAW);
            services.transaction(request);
            fail("Expected InsufficientBalanceException to be thrown");
        } catch (Exception ex) {
            assertEquals(InsufficientBalanceException.class, ex.getClass());
            assertEquals(ErrorMessage.SALDO_INSUFICIENTE.getMessage(), ex.getMessage());
        }
    }

    @Test
    public void testTransactionsFields() {
        assertEquals(ID, this.transaction.getId());
        assertEquals(TYPE, this.transaction.getTransactionType());
        assertEquals(AMOUNT, this.transaction.getAmount());
        assertEquals(TIME_STAMP, this.transaction.getTimestamp());
        assertEquals(this.account, this.transaction.getSourceAccount());
        assertEquals(this.account, this.transaction.getDestinationAccount());
    }
    private void configureModelMapper() {
        when(modelMapper.map(transaction, TransactionDTO.class)).thenReturn(transactionDTO);
        when(modelMapper.map(accountDTO, Account.class)).thenReturn(account);
    }

    private void initializeVariables() {
        this.transaction = new Transaction(ID, TYPE, AMOUNT, TIME_STAMP, null, null);
        this.transactionDTO = new TransactionDTO(ID, TYPE.toString(), AMOUNT, TIME_STAMP, null, null);
        this.request = new TransactionRequest(TransactionType.TRANSFER, ACC_ID, ACC_ID, AMOUNT);
        this.account = new Account(ACC_ID, ACC_NUMBER, BALANCE);
        this.accountDTO = new AccountDTO(ACC_ID, ACC_NUMBER, BALANCE);

        this.transaction.setDestinationAccount(this.account);
        this.transaction.setSourceAccount(this.account);
    }
}