package br.com.lucas.virtualBankAPI.domain.accounts;

import br.com.lucas.virtualBankAPI.domain.users.UsuarioDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountDTOTest {

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
        this.initializeVariables();
    }

    @Test
    public void noArgsConstructor() {
        AccountDTO accDTO = new AccountDTO();
        Assertions.assertNull(accDTO.getId());
        Assertions.assertNull(accDTO.getAccNumber());
        Assertions.assertNull(accDTO.getBalance());
        Assertions.assertNull(accDTO.getOwner());
    }

    @Test
    public void testAccountAttributes() {
        Assertions.assertEquals(ID, this.accountDTO.getId());
        Assertions.assertEquals(ACC_NUMBER, this.accountDTO.getAccNumber());
        Assertions.assertEquals(BALANCE, this.accountDTO.getBalance());
        Assertions.assertEquals(this.usuarioDTO, this.accountDTO.getOwner());
    }

    private void initializeVariables() {
        this.usuarioDTO = new UsuarioDTO(USER_ID, USER_NAME, USER_EMAIL, USER_PASSWORD);
        this.accountDTO = new AccountDTO(ID, ACC_NUMBER, BALANCE, this.usuarioDTO);
    }
}