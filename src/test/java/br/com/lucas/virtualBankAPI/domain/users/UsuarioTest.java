package br.com.lucas.virtualBankAPI.domain.users;

import br.com.lucas.virtualBankAPI.domain.accounts.Account;
import br.com.lucas.virtualBankAPI.repositories.users.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    private final Usuario usuario = new Usuario();

    private static final Integer ID = 1;
    private static final String NAME = "nameTest";
    private static final String EMAIL = "email@teste.com";
    private static final String PASSWORD = "123456";
    public static final Long ACC_ID = 1L;
    public static final String ACC_NUMBER = "123456";
    public static final Double ACC_BALANCE = 0.0;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIdFields() {
        usuario.setId(ID);
        assertEquals(ID, usuario.getId());
    }

    @Test
    void testNameFields() {
        usuario.setName(NAME);
        assertEquals(NAME, usuario.getName());
    }

    @Test
    void testEmailFields() {
        usuario.setEmail(EMAIL);
        assertEquals(EMAIL, usuario.getEmail());
    }

    @Test
    void testPasswordFields() {
        usuario.setPassword(PASSWORD);
        assertEquals(PASSWORD, usuario.getPassword());
    }

    @Test
    void testAllArgumentsConstructor() {
        Usuario test = new Usuario(ID, NAME, EMAIL, PASSWORD);
        assertEquals(ID, test.getId());
        assertEquals(NAME, test.getName());
        assertEquals(EMAIL, test.getEmail());
        assertEquals(PASSWORD, test.getPassword());
    }

    @Test
    void testAccountList() {
        Account account = new Account(ACC_ID, ACC_NUMBER, ACC_BALANCE);
        this.usuario.setAccountList(List.of(account));
        assertEquals(1, this.usuario.getAccountList().size());
    }
}