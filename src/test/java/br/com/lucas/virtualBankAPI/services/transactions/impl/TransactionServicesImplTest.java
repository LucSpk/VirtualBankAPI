package br.com.lucas.virtualBankAPI.services.transactions.impl;

import br.com.lucas.virtualBankAPI.domain.accounts.AccountDTO;
import br.com.lucas.virtualBankAPI.domain.transactions.Transaction;
import br.com.lucas.virtualBankAPI.domain.transactions.TransactionDTO;
import br.com.lucas.virtualBankAPI.enums.exceptions.ErrorMessage;
import br.com.lucas.virtualBankAPI.enums.transactions.TransactionType;
import br.com.lucas.virtualBankAPI.repositories.transactions.TransactionRepository;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransactionServicesImplTest {

    @InjectMocks
    private TransactionServicesImpl services;
    @Mock
    private TransactionRepository repository;
    @Mock
    private ModelMapper modelMapper;

    private Transaction transaction;
    private TransactionDTO transactionDTO;

    private static final Long ID = 1L;
    private static final TransactionType TYPE = TransactionType.DEPOSIT;
    private static final Double AMOUNT = 100.0;

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
    void transaction() {
    }

    private void configureModelMapper() {
        when(modelMapper.map(transaction, TransactionDTO.class)).thenReturn(transactionDTO);
    }

    private void initializeVariables() {
        this.transaction = new Transaction(ID, TYPE, AMOUNT, LocalDateTime.now(), null, null);
        this.transactionDTO = new TransactionDTO(ID, TYPE.toString(), AMOUNT, LocalDateTime.now(), null, null);
    }
}