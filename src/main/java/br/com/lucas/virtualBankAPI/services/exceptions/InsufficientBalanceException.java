package br.com.lucas.virtualBankAPI.services.exceptions;

public class InsufficientBalanceException extends RuntimeException{

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
