package br.com.lucas.virtualBankAPI.controllers.exceptions;

import br.com.lucas.virtualBankAPI.enums.exceptions.ErrorMessage;
import br.com.lucas.virtualBankAPI.services.exceptions.*;
import br.com.lucas.virtualBankAPI.services.exceptions.IllegalArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ControllerExceptionHandlerTest {

    @InjectMocks
    private ControllerExceptionHandler controllerExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenObjectNotFoundExceptionReturnAResponseEntity() {
        ResponseEntity<StandardError> response = controllerExceptionHandler
                .objectNotFound(
                        new ObjectNotFoundException(ErrorMessage.OBJETO_NAO_ENCONTRADO.getMessage()),
                        new MockHttpServletRequest()
                );

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(ErrorMessage.OBJETO_NAO_ENCONTRADO.getMessage(), response.getBody().getError());
        assertEquals(404, response.getBody().getStatus());
    }

    @Test
    void whenDataIntegrityViolationReturnAResponseEntity() {
        ResponseEntity<StandardError> response = controllerExceptionHandler
                .dataIntegrityViolation(
                        new DataIntegrityViolationException(ErrorMessage.EAMIL_JA_CADASTRADO.getMessage()),
                        new MockHttpServletRequest()
                );

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(ErrorMessage.EAMIL_JA_CADASTRADO.getMessage(), response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());
    }

    @Test
    void whenDivergentDataReturnAResponseEntity() {
        ResponseEntity<StandardError> response = controllerExceptionHandler
                .divergentData(
                        new DivergentDataException(ErrorMessage.DIVERGENCIA_NOS_DADOS.getMessage()),
                        new MockHttpServletRequest()
                );

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(ErrorMessage.DIVERGENCIA_NOS_DADOS.getMessage(), response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());
    }

    @Test
    void whenIllegalArgumentReturnAResponseEntity() {
        ResponseEntity<StandardError> response = controllerExceptionHandler
                .illegalArgument(
                        new IllegalArgumentException(ErrorMessage.TRANSACAO_NECESSARIA.getMessage()),
                        new MockHttpServletRequest()
                );

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(ErrorMessage.TRANSACAO_NECESSARIA.getMessage(), response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());
    }

    @Test
    void whenInsufficientBalanceReturnAResponseEntity() {
        ResponseEntity<StandardError> response = controllerExceptionHandler
                .insufficientBalance(
                        new InsufficientBalanceException(ErrorMessage.SALDO_INSUFICIENTE.getMessage()),
                        new MockHttpServletRequest()
                );

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(ErrorMessage.SALDO_INSUFICIENTE.getMessage(), response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());
    }
}