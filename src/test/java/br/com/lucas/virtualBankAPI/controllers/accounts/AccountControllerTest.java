package br.com.lucas.virtualBankAPI.controllers.accounts;

import br.com.lucas.virtualBankAPI.domain.accounts.Account;
import br.com.lucas.virtualBankAPI.domain.accounts.AccountDTO;
import br.com.lucas.virtualBankAPI.services.accounts.AccountServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class AccountControllerTest {

    @InjectMocks
    private AccountController controller;

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private AccountServices services;

    private Account account;
    private AccountDTO accountDTO;

    public static final Long ID = 1L;
    public static final String ACC_NUMBER = "123456";
    public static final Double BALANCE = 0.0;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.initializeVariables();
    }

    @Test
    void whenFindByIdThenReturnSucess() {
        when(services.findById(anyLong())).thenReturn(accountDTO);

        ResponseEntity<AccountDTO> response = controller.findById(ID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        AccountDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(AccountDTO.class, responseBody.getClass());

        assertEquals(ID, responseBody.getId());
        assertEquals(ACC_NUMBER, responseBody.getAccNumber());

        verify(services, times(1)).findById(ID);
    }

    @Test
    void findAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private void initializeVariables() {
        this.account = new Account(ID, ACC_NUMBER, BALANCE);
        this.accountDTO = new AccountDTO(ID, ACC_NUMBER, BALANCE);
    }
}