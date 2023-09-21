package br.com.lucas.virtualBankAPI.domain.accounts;

import br.com.lucas.virtualBankAPI.domain.users.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private Account account;
    private Usuario usuario;

    public static final Long ID = 1L;
    public static final String ACC_NUMBER = "123456";
    public static final Double BALANCE = 0.0;
    public static final Integer USER_ID = 1;
    public static final String USER_NAME = "userTest";
    public static final String USER_EMAIL = "test@email.com";
    public static final String USER_PASSWORD = "123456";

    @BeforeEach
    void setUp() {
        this.initializeVariables();
    }

    @Test
    public void attributesTest() {
        Assertions.assertEquals(ID, this.account.getId());
        Assertions.assertEquals(ACC_NUMBER, this.account.getAccNumber());
        Assertions.assertEquals(BALANCE, this.account.getBalance());
        Assertions.assertEquals(this.usuario, this.account.getOwner());
    }

    private void initializeVariables() {
        this.usuario = new Usuario(USER_ID, USER_NAME, USER_EMAIL, USER_PASSWORD);
        this.account = new Account(ID, ACC_NUMBER, BALANCE, this.usuario);
    }
}