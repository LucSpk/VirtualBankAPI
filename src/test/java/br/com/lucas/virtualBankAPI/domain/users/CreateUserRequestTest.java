package br.com.lucas.virtualBankAPI.domain.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

class CreateUserRequestTest {

    public CreateUserRequest createUserRequest;

    private static final String NAME = "nameTest";
    private static final String EMAIL = "email@teste.com";
    private static final String PASSWORD = "123456";

    @BeforeEach
    void setUp() {
        createUserRequest = new CreateUserRequest(NAME, EMAIL, PASSWORD);
    }

    @Test
    public void testGetName() {
        assertEquals(NAME, createUserRequest.getName());
    }

    @Test
    public void testGetEmail() {
        assertEquals(EMAIL, createUserRequest.getEmail());
    }

    @Test
    public void testGetPassword() {
        assertEquals(PASSWORD, createUserRequest.getPassword());
    }
}