package br.com.lucas.virtualBankAPI.controllers.accounts;

import br.com.lucas.virtualBankAPI.domain.accounts.Account;
import br.com.lucas.virtualBankAPI.domain.accounts.AccountDTO;
import br.com.lucas.virtualBankAPI.domain.users.Usuario;
import br.com.lucas.virtualBankAPI.domain.users.UsuarioDTO;
import br.com.lucas.virtualBankAPI.services.accounts.AccountServices;
import br.com.lucas.virtualBankAPI.services.accounts.impl.AccountServicesImpl;
import br.com.lucas.virtualBankAPI.services.users.UserServices;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private AccountServicesImpl services;
    @Mock
    private UserServices userServices;

    private Account account;
    private AccountDTO accountDTO;
    private UsuarioDTO usuarioDTO;


    public static final Long ID = 1L;
    public static final String ACC_NUMBER = "123456";
    public static final Double BALANCE = 0.0;
    public static final Integer USER_ID = 1;
    public static final String USER_NAME = "userTest";
    public static final String USER_EMAIL = "test@email.com";
    public static final String USER_PASSWORD = "123456";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.initializeVariables();
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
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
    void whenFindAllThenReturnAnListNonEmpty() {
        when(services.findAll()).thenReturn(List.of(accountDTO));

        ResponseEntity<List<AccountDTO>> response = controller.findAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<AccountDTO> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertFalse(responseBody.isEmpty());
        assertEquals(1, responseBody.size());

        AccountDTO firstElement = responseBody.get(0);
        assertEquals(ID, firstElement.getId());
        assertEquals(ACC_NUMBER, firstElement.getAccNumber());
    }

    @Test
    void whenFindAllThenReturnAnListEmpty() {
        when(services.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<List<AccountDTO>> response = controller.findAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<AccountDTO> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.isEmpty());

        verify(services, times(1)).findAll();
    }

    @Test
    void whenCreateThenReturnSuccess() {
        when(services.create(any(), anyInt())).thenReturn(accountDTO);
        when(userServices.findById(anyInt())).thenReturn(usuarioDTO);

        ResponseEntity<AccountDTO> response = controller.create(accountDTO, USER_ID);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        URI expectedUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(ID).toUri();
        assertEquals(expectedUri, response.getHeaders().getLocation());

        verify(services, times(1)).create(any(), anyInt());
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(services.update(any(), anyLong())).thenReturn(accountDTO);

        ResponseEntity<AccountDTO> response = controller.update(accountDTO, ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(AccountDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(ACC_NUMBER, response.getBody().getAccNumber());
        assertEquals(BALANCE, response.getBody().getBalance());
    }

    @Test
    void whenDeleteThenReturnSuccess() {
        doNothing().when(services).delete(anyLong());

        ResponseEntity<?> response = controller.delete(ID);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(services, times(1)).delete(anyLong());
    }

    private void setModelMapper() {
        when(modelMapper.map(accountDTO, Account.class)).thenReturn(account);
        when(modelMapper.map(account, AccountDTO.class)).thenReturn(accountDTO);
    }


    private void initializeVariables() {
        this.account = new Account(ID, ACC_NUMBER, BALANCE);
        this.accountDTO = new AccountDTO(ID, ACC_NUMBER, BALANCE);
        this.usuarioDTO = new UsuarioDTO(USER_ID, USER_NAME, USER_EMAIL, USER_PASSWORD);
    }
}