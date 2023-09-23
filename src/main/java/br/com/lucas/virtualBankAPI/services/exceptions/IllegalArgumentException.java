package br.com.lucas.virtualBankAPI.services.exceptions;

public class IllegalArgumentException extends RuntimeException{

    public IllegalArgumentException(String message) {
        super(message);
    }
}
