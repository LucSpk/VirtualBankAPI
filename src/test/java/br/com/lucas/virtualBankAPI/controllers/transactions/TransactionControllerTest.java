package br.com.lucas.virtualBankAPI.controllers.transactions;

import br.com.lucas.virtualBankAPI.domain.transactions.TransactionDTO;
import br.com.lucas.virtualBankAPI.enums.transactions.TransactionType;
import br.com.lucas.virtualBankAPI.services.transactions.TransactionServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TransactionControllerTest {

    @InjectMocks
    private TransactionController controller;
    @Mock
    private TransactionServices services;
    private TransactionDTO transactionDTO;

    private static final Long ID = 1L;
    private static final TransactionType TYPE = TransactionType.DEPOSIT;
    private static final Double AMOUNT = 100.0;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.initializeVariables();
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        Mockito.when(services.findById(Mockito.anyLong())).thenReturn(transactionDTO);

        ResponseEntity<TransactionDTO> response = controller.findById(ID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        TransactionDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(TransactionDTO.class, responseBody.getClass());

        assertEquals(ID, responseBody.getId());
        assertEquals(AMOUNT, responseBody.getAmount());

        verify(services, times(1)).findById(ID);

    }

    @Test
    void whenFindAllThenReturnAnListNonEmpty() {
        Mockito.when(services.findAll()).thenReturn(List.of(transactionDTO));

        ResponseEntity<List<TransactionDTO>> response = controller.findAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<TransactionDTO> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(TransactionDTO.class, responseBody.get(0).getClass());

        assertEquals(ID, responseBody.get(0).getId());
        assertEquals(AMOUNT, responseBody.get(0).getAmount());

        verify(services, times(1)).findAll();
    }

    @Test
    void whenFindAllThenReturnAnListEmpty() {
        when(services.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<List<TransactionDTO>> response = controller.findAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<TransactionDTO> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.isEmpty());

        verify(services, times(1)).findAll();
    }

    @Test
    void transaction() {
    }

    private void initializeVariables() {
        this.transactionDTO = new TransactionDTO(ID, TYPE.toString(), AMOUNT, LocalDateTime.now(), null, null);
    }
}