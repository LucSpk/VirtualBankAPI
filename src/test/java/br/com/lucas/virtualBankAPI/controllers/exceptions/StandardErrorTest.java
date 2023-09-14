package br.com.lucas.virtualBankAPI.controllers.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StandardErrorTest {

    public static final String ERROR_MESSAGE = "Error message";
    public static final int STATUS_CODE = 404;
    public static final String PATH = "/api/resource";
    public static final LocalDateTime NOW = LocalDateTime.now();
    private StandardError standardError;
    @BeforeEach
    void setUp() {
        this.initializeVariable();
    }

    @Test
    public void testGetError() {
        standardError.setError(ERROR_MESSAGE);
        assertEquals(ERROR_MESSAGE, standardError.getError());
    }

    @Test
    public void testGetStatus() {
        standardError.setStatus(STATUS_CODE);
        assertEquals(STATUS_CODE, standardError.getStatus());
    }

    @Test
    public void testGetPath() {
        standardError.setPath(PATH);
        assertEquals(PATH, standardError.getPath());
    }

    @Test
    public void testGetTimeStamp() {
        standardError.setTimeStamp(NOW);
        assertEquals(NOW, standardError.getTimeStamp());
    }

    private void initializeVariable() {
        standardError = new StandardError(ERROR_MESSAGE, STATUS_CODE, PATH, NOW);
    }
}