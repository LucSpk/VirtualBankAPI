package br.com.lucas.virtualBankAPI.services.accounts.impl;

import br.com.lucas.virtualBankAPI.domain.accounts.Account;
import br.com.lucas.virtualBankAPI.domain.accounts.AccountDTO;
import br.com.lucas.virtualBankAPI.domain.users.Usuario;
import br.com.lucas.virtualBankAPI.domain.users.UsuarioDTO;
import br.com.lucas.virtualBankAPI.enums.exceptions.ErrorMessage;
import br.com.lucas.virtualBankAPI.repositories.accounts.AccountRepository;
import br.com.lucas.virtualBankAPI.repositories.users.UserRepository;
import br.com.lucas.virtualBankAPI.services.exceptions.DataIntegrityViolationException;
import br.com.lucas.virtualBankAPI.services.exceptions.ObjectNotFoundException;
import br.com.lucas.virtualBankAPI.services.users.UserServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class AccountServicesImplTest {

    @InjectMocks
    private AccountServicesImpl services;
    @Mock
    private AccountRepository repository;
    @Mock
    private UserServices userServices;
    @Mock
    private ModelMapper modelMapper;

    private Account account;
    private Account existingAccount;
    private AccountDTO accountDTO;
    private Usuario usuario;
    private UsuarioDTO usuarioDTO;

    public static final Long ID = 1L;
    public static final String ACC_NUMBER = "123456";
    public static final Double BALANCE = 1000.0;
    public static final Integer USER_ID = 1;
    public static final String USER_NAME = "userTest";
    public static final String USER_EMAIL = "test@email.com";
    public static final String USER_PASSWORD = "123456";

    @BeforeEach
    void setupEach() {
        MockitoAnnotations.openMocks(this);
        this.initializeVariables();
        this.setModelMapper();
    }

    @Test
    public void whenFindByIdThenReturnAnAccountDTOInstance() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(account));

        AccountDTO response = services.findById(ID);

        assertNotNull(response);
        assertEquals(AccountDTO.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(ACC_NUMBER, response.getAccNumber());
        assertEquals(BALANCE, response.getBalance());
    }

    @Test
    public void whenFindByIdThenReturnAnObjectNotFoundException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        try {
            services.findById(ID);
            fail("Expected ObjectNotFoundException to be thrown");
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(ErrorMessage.NUM_ACC_NAO_ENCONTRADO.getMessage(), ex.getMessage());
        }
    }

    @Test
    public void whenFindAllThenReturnNonEmptyList() {
        when(repository.findAll()).thenReturn(List.of(account));

        List<AccountDTO> response = services.findAll();

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());

        AccountDTO firstAccount = response.get(0);
        assertEquals(ID, firstAccount.getId());
        assertEquals(ACC_NUMBER, firstAccount.getAccNumber());
        assertEquals(BALANCE, firstAccount.getBalance());
    }

    @Test
    public void whenFindAllThenReturnEmptyList() {
        when(repository.findAll()).thenReturn(new ArrayList<>());

        List<AccountDTO> response = services.findAll();

        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    @Test
    public void whenCreateThenReturnAnAccountDTO() {
        when(repository.findByAccNumber(any())).thenReturn(Optional.empty());
        when(userServices.findById(Mockito.anyInt())).thenReturn(usuarioDTO);
        when(repository.save(any())).thenReturn(account);

        AccountDTO response = services.create(account, USER_ID);

        assertNotNull(response);
        assertEquals(ACC_NUMBER, response.getAccNumber());
        assertEquals(BALANCE, response.getBalance());

        verify(repository, times(1)).save(any(Account.class));
    }

    @Test
    public void whenCreateWithDuplicateAccNumberThenThrowDataIntegrityViolationException() {
        when(repository.findByAccNumber(ACC_NUMBER)).thenReturn(Optional.of(this.existingAccount));

        try {
            services.create(account, 1);
            fail("Expected DataIntegrityViolationException to be thrown");
        } catch (Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals(ErrorMessage.NUMERO_ACC_JA_CADASTRADO.getMessage(), ex.getMessage());
        }
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    private void initializeVariables() {
        this.account = new Account(ID, ACC_NUMBER, BALANCE);
        this.accountDTO = new AccountDTO(ID, ACC_NUMBER, BALANCE);
        this.existingAccount = new Account((ID + 1), ACC_NUMBER, BALANCE);
        this.usuario = new Usuario(USER_ID, USER_NAME, USER_EMAIL, USER_PASSWORD);
        this.usuarioDTO = new UsuarioDTO(USER_ID, USER_NAME, USER_EMAIL, USER_PASSWORD);
    }

    private void setModelMapper() {
        when(modelMapper.map(account, AccountDTO.class)).thenReturn(accountDTO);
        when(modelMapper.map(usuarioDTO, Usuario.class)).thenReturn(usuario);
    }
}