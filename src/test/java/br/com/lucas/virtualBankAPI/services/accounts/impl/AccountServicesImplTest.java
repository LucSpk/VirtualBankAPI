package br.com.lucas.virtualBankAPI.services.accounts.impl;

import br.com.lucas.virtualBankAPI.domain.accounts.Account;
import br.com.lucas.virtualBankAPI.domain.accounts.AccountDTO;
import br.com.lucas.virtualBankAPI.enums.exceptions.ErrorMessage;
import br.com.lucas.virtualBankAPI.repositories.accounts.AccountRepository;
import br.com.lucas.virtualBankAPI.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class AccountServicesImplTest {

    @InjectMocks
    private AccountServicesImpl services;
    @Mock
    private AccountRepository repository;
    @Mock
    private ModelMapper modelMapper;

    private Account account;
    private AccountDTO accountDTO;

    public static final Long ACCOUNT_ID = 1L;
    public static final String ACCOUNT_NUMBER = "123456";
    public static final Double BALANCE = 1000.0;

    @BeforeEach
    void setupEach() {
        MockitoAnnotations.openMocks(this);
        this.initializeVariables();
        this.setModelMapper();
    }

    @Test
    public void whenFindByIdThenReturnAnAccountDTOInstance() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(account));

        AccountDTO response = services.findById(ACCOUNT_ID);

        assertNotNull(response);
        assertEquals(AccountDTO.class, response.getClass());
        assertEquals(ACCOUNT_ID, response.getId());
        assertEquals(ACCOUNT_NUMBER, response.getAccNumber());
        assertEquals(BALANCE, response.getBalance());
    }

    @Test
    public void whenFindByIdThenReturnAnObjectNotFoundException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        try {
            services.findById(ACCOUNT_ID);
            fail("Expected ObjectNotFoundException to be thrown");
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(ErrorMessage.OBJETO_NAO_ENCONTRADO.getMessage(), ex.getMessage());
        }
    }

    @Test
    public void findAll() {
    }

    @Test
    public void create() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    private void initializeVariables() {
        this.account = new Account(ACCOUNT_ID, ACCOUNT_NUMBER, BALANCE);
        this.accountDTO = new AccountDTO(ACCOUNT_ID, ACCOUNT_NUMBER, BALANCE);
    }

    private void setModelMapper() {
        when(modelMapper.map(account, AccountDTO.class)).thenReturn(accountDTO);
    }
}