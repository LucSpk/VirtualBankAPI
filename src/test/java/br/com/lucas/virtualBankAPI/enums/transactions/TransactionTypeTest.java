package br.com.lucas.virtualBankAPI.enums.transactions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTypeTest {
    @Test
    public void testFromValueValid() {
        try {

            TransactionType transactionType = TransactionType.fromValue(TransactionType.DEPOSIT.getValue());
            assertEquals(TransactionType.DEPOSIT, transactionType);
        } catch (Exception ex) {
            fail("Ocorreu um erro!");
        }
    }

    @Test
    public void testFromValueInvalid() {
        int invalidValue = 99;
        assertThrows(IllegalAccessException.class, () -> TransactionType.fromValue(invalidValue));
    }
}